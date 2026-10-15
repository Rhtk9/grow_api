package br.cefetmg.grow.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.cefetmg.grow.dto.PlantaUsuarioRequestDTO;
import br.cefetmg.grow.dto.PlantaUsuarioResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.EspeciePlanta;
import br.cefetmg.grow.model.FasePlanta;
import br.cefetmg.grow.model.PlantaUsuario;
import br.cefetmg.grow.model.Registro;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.EspeciePlantaRepository;
import br.cefetmg.grow.repository.FasePlantaRepository;
import br.cefetmg.grow.repository.PlantaUsuarioRepository;
import br.cefetmg.grow.repository.RegistroRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlantaUsuarioService {

    private final PlantaUsuarioRepository plantaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspeciePlantaRepository especieRepository;
    private final FasePlantaRepository faseRepository;
    private final RegistroRepository registroRepository;

    @Transactional
    public List<PlantaUsuarioResponseDTO> listarTodos() {
        List<PlantaUsuario> plantas = plantaRepository.findAll();
        plantas.forEach(this::recalcularEstado);
        return plantas.stream()
                .map(PlantaUsuarioResponseDTO::new)
                .toList();
    }

    @Transactional
    public List<PlantaUsuarioResponseDTO> listarPorUsuario(Long usuarioId) {
        List<PlantaUsuario> plantas = plantaRepository.findByUsuarioId(usuarioId);
        plantas.forEach(this::recalcularEstado);
        return plantas.stream()
                .map(PlantaUsuarioResponseDTO::new)
                .toList();
    }

    @Transactional
    public PlantaUsuarioResponseDTO buscarPorId(Long id) {
        PlantaUsuario planta = plantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + id));
        recalcularEstado(planta);
        return new PlantaUsuarioResponseDTO(planta);
    }

    @Transactional
    public PlantaUsuarioResponseDTO criar(PlantaUsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        EspeciePlanta especie = especieRepository.findById(dto.getEspecieId())
                .orElseThrow(() -> new ResourceNotFoundException("Espécie não encontrada. Id: " + dto.getEspecieId()));

        FasePlanta fase = faseRepository.findById(dto.getFaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Fase não encontrada. Id: " + dto.getFaseId()));

        PlantaUsuario planta = new PlantaUsuario();
        planta.setApelido(dto.getApelido());
        planta.setUsuario(usuario);
        planta.setEspeciePlanta(especie);
        planta.setFase(fase);

        planta.setDataCriacao(LocalDate.now());
        planta.setUltimaAlteracao(LocalDate.now());
        planta.setUltimaAtualizacao(LocalDateTime.now());

        planta.setXpAtual(0);
        planta.setNivel(1);
        planta.setFelicidade(100);
        planta.setSaude(100);

        // Padrões iniciais corretos
        planta.setAguaAtual(60);
        planta.setNutrientesAtual(70);
        planta.setLuzAtual(30);
        planta.setEstresse(0);
        planta.setPosicaoAtual("MEIA_SOMBRA");

        PlantaUsuario salvo = plantaRepository.save(planta);
        return new PlantaUsuarioResponseDTO(salvo);
    }

    @Transactional
    public PlantaUsuarioResponseDTO atualizar(Long id, PlantaUsuarioRequestDTO dto) {
        PlantaUsuario planta = plantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + id));

        if (dto.getApelido() != null) {
            planta.setApelido(dto.getApelido());
        }
        if (dto.getFaseId() != null) {
            FasePlanta fase = faseRepository.findById(dto.getFaseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fase não encontrada. Id: " + dto.getFaseId()));
            planta.setFase(fase);
        }

        planta.setUltimaAlteracao(LocalDate.now());
        planta.setUltimaAtualizacao(LocalDateTime.now());

        return new PlantaUsuarioResponseDTO(plantaRepository.save(planta));
    }

    @Transactional
    public PlantaUsuarioResponseDTO atualizarStatus(
            Long id, 
            Integer felicidade, 
            Integer saude, 
            Integer aguaAtual, 
            Integer nutrientesAtual, 
            Integer luzAtual, 
            Integer estresse) {
        
        PlantaUsuario planta = plantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + id));

        recalcularEstado(planta);

        if (felicidade != null) planta.setFelicidade(Math.max(0, Math.min(100, felicidade)));
        if (saude != null) planta.setSaude(Math.max(0, Math.min(100, saude)));
        if (aguaAtual != null) planta.setAguaAtual(Math.max(0, Math.min(100, aguaAtual)));
        if (nutrientesAtual != null) planta.setNutrientesAtual(Math.max(0, Math.min(100, nutrientesAtual)));
        if (luzAtual != null) planta.setLuzAtual(Math.max(0, Math.min(100, luzAtual)));
        if (estresse != null) planta.setEstresse(Math.max(0, Math.min(100, estresse)));

        planta.setUltimaAlteracao(LocalDate.now());
        planta.setUltimaAtualizacao(LocalDateTime.now());

        return new PlantaUsuarioResponseDTO(plantaRepository.save(planta));
    }

    @Transactional
    public PlantaUsuarioResponseDTO alterarPosicao(Long id, String novaPosicao) {
        return mover(id, novaPosicao);
    }

    @Transactional
    public PlantaUsuarioResponseDTO regar(Long id) {
        PlantaUsuario planta = plantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + id));

        recalcularEstado(planta);

        LocalDateTime agora = LocalDateTime.now();
        if (planta.getUltimaRega() != null && planta.getUltimaRega().plusHours(6).isAfter(agora)) {
            throw new IllegalStateException("Cooldown de rega ainda ativo. Aguarde para regar novamente.");
        }

        int aguaAnterior = planta.getAguaAtual();
        int novaAgua = Math.min(100, aguaAnterior + 45);
        
        planta.setAguaAtual(novaAgua);
        planta.setUltimaRega(agora);
        planta.setUltimaAlteracao(LocalDate.now());
        planta.setUltimaAtualizacao(agora);

        criarRegistro(planta, "REGAR", aguaAnterior, novaAgua, 10);

        return new PlantaUsuarioResponseDTO(plantaRepository.save(planta));
    }

    @Transactional
    public PlantaUsuarioResponseDTO adubar(Long id) {
        PlantaUsuario planta = plantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + id));

        recalcularEstado(planta);

        LocalDateTime agora = LocalDateTime.now();
        int adubacoesMes = (planta.getFase() != null && planta.getFase().getNecessidadeFase() != null)
                ? planta.getFase().getNecessidadeFase().getAdubacoesPorMes() : 4;
        
        long horasCooldown = Math.max(24, 720 / Math.max(1, adubacoesMes));

        if (planta.getUltimaAdubacao() != null && planta.getUltimaAdubacao().plusHours(horasCooldown).isAfter(agora)) {
            throw new IllegalStateException("Cooldown de adubação ainda ativo.");
        }

        int nutrientesAnterior = planta.getNutrientesAtual();
        int novosNutrientes = Math.min(100, nutrientesAnterior + 60);

        planta.setNutrientesAtual(novosNutrientes);
        planta.setUltimaAdubacao(agora);
        planta.setUltimaAlteracao(LocalDate.now());
        planta.setUltimaAtualizacao(agora);

        criarRegistro(planta, "ADUBAR", nutrientesAnterior, novosNutrientes, 25);

        return new PlantaUsuarioResponseDTO(plantaRepository.save(planta));
    }

    @Transactional
    public PlantaUsuarioResponseDTO mover(Long id, String novaPosicao) {
        PlantaUsuario planta = plantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + id));

        recalcularEstado(planta);

        planta.setPosicaoAtual(novaPosicao);
        planta.setUltimaAlteracao(LocalDate.now());
        planta.setUltimaAtualizacao(LocalDateTime.now());

        criarRegistro(planta, "MOVER", 0, 0, 5);

        return new PlantaUsuarioResponseDTO(plantaRepository.save(planta));
    }

    @Transactional
    public void excluir(Long id) {
        if (!plantaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + id);
        }
        plantaRepository.deleteById(id);
    }

    private void recalcularEstado(PlantaUsuario planta) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime ultima = planta.getUltimaAtualizacao() != null ? planta.getUltimaAtualizacao() : planta.getDataCriacao().atStartOfDay();
        
        long horasPassadas = ChronoUnit.HOURS.between(ultima, agora);
        if (horasPassadas <= 0) return;

        int perdaAgua = (int) (horasPassadas * 2); 
        int perdaNutrientes = (int) (horasPassadas * 1);

        planta.setAguaAtual(Math.max(0, planta.getAguaAtual() - perdaAgua));
        planta.setNutrientesAtual(Math.max(0, planta.getNutrientesAtual() - perdaNutrientes));
        
        planta.setUltimaAtualizacao(agora);
        plantaRepository.save(planta);
    }

    private void criarRegistro(PlantaUsuario planta, String tipoAcao, int valorAnterior, int valorNovo, int xp) {
        Registro registro = new Registro();
        registro.setPlantaUsuario(planta);
        registro.setDataRegistro(LocalDateTime.now()); // Corrigido para setDataRegistro
        registro.setTipoAcao(tipoAcao);
        registro.setValorAnterior(valorAnterior);
        registro.setValorNovo(valorNovo);
        registro.setXpRecebido(xp);
        registroRepository.save(registro);
        
        planta.setXpAtual((planta.getXpAtual() != null ? planta.getXpAtual() : 0) + xp);
    }
}