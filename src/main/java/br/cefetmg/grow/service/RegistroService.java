package br.cefetmg.grow.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.cefetmg.grow.dto.RegistroRequestDTO;
import br.cefetmg.grow.dto.RegistroResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.EvolucaoFase;
import br.cefetmg.grow.model.FasePlanta;
import br.cefetmg.grow.model.PlantaUsuario;
import br.cefetmg.grow.model.Registro;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.FasePlantaRepository;
import br.cefetmg.grow.repository.PlantaUsuarioRepository;
import br.cefetmg.grow.repository.RegistroRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistroService {

    private final RegistroRepository registroRepository;
    private final PlantaUsuarioRepository plantaUsuarioRepository;
    private final FasePlantaRepository fasePlantaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<RegistroResponseDTO> listarTodos() {
        return registroRepository.findAll().stream()
                .map(RegistroResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RegistroResponseDTO> listarPorPlanta(Long plantaUsuarioId) {
        if (!plantaUsuarioRepository.existsById(plantaUsuarioId)) {
            throw new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + plantaUsuarioId);
        }
        return registroRepository.findByPlantaUsuarioId(plantaUsuarioId).stream()
                .map(RegistroResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RegistroResponseDTO> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        LocalDateTime inicioDataHora = inicio.atStartOfDay();
        LocalDateTime fimDataHora = fim.atTime(LocalTime.MAX);
        return registroRepository.findByDataRegistroBetween(inicioDataHora, fimDataHora).stream()
                .map(RegistroResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RegistroResponseDTO buscarPorId(Long id) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado. Id: " + id));
        return new RegistroResponseDTO(registro);
    }

    @Transactional
    public RegistroResponseDTO criar(RegistroRequestDTO dto) {
        // 1. Buscar a Planta do Usuário
        PlantaUsuario plantaUsuario = plantaUsuarioRepository.findById(dto.getPlantaUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Planta do usuário não encontrada. Id: " + dto.getPlantaUsuarioId()));

        FasePlanta faseAtual = plantaUsuario.getFase();
        EvolucaoFase evolucao = (faseAtual != null) ? faseAtual.getEvolucaoFase() : null;
        Usuario usuario = plantaUsuario.getUsuario();

        int qualidade = (dto.getQualidadeCuidado() != null) ? dto.getQualidadeCuidado() : 5;

        // 2. Atualizar Saúde e Felicidade da Planta
        if (qualidade >= 7) {
            plantaUsuario.setSaude(Math.min(100, plantaUsuario.getSaude() + 5));
            plantaUsuario.setFelicidade(Math.min(100, plantaUsuario.getFelicidade() + 10));
        } else if (qualidade <= 3) {
            plantaUsuario.setSaude(Math.max(0, plantaUsuario.getSaude() - 15));
            plantaUsuario.setFelicidade(Math.max(0, plantaUsuario.getFelicidade() - 10));
        }
        plantaUsuario.setUltimaAlteracao(LocalDate.now());

        // 3. Se a planta zerar a saúde, remove do banco
        if (plantaUsuario.getSaude() <= 0) {
            plantaUsuarioRepository.delete(plantaUsuario);
            throw new ResourceNotFoundException("A planta não resistiu e morreu.");
        }

        // 4. Calcular XP Ganho pela Planta
        int xpGanhoFase = (faseAtual != null && faseAtual.getXpGanho() != null) ? faseAtual.getXpGanho() : 10;
        double fatorQualidade = qualidade / 10.0;
        int xpBaseCalculado = (int) Math.round(xpGanhoFase * fatorQualidade);

        int bonus = (evolucao != null && evolucao.getBonus() != null) ? evolucao.getBonus() : 0;
        int penalidade = (evolucao != null && evolucao.getPenalidade() != null) ? evolucao.getPenalidade() : 0;

        if (qualidade >= 8) {
            xpBaseCalculado += bonus;
        } else if (qualidade <= 3) {
            xpBaseCalculado -= penalidade;
        }

        int xpGanhoPlanta = Math.max(0, xpBaseCalculado);

        // 5. Atualizar XP da Planta e Checar Evolução de Fase
        int xpAtualPlanta = (plantaUsuario.getXpAtual() != null) ? plantaUsuario.getXpAtual() : 0;
        int novoXpPlanta = xpAtualPlanta + xpGanhoPlanta;

        if (faseAtual != null && faseAtual.getXpNecessario() != null && novoXpPlanta >= faseAtual.getXpNecessario()) {
            var proximaFase = fasePlantaRepository.findByEspeciePlantaAndOrdem(
                    faseAtual.getEspeciePlanta(),
                    faseAtual.getOrdem() + 1);

            if (proximaFase.isPresent()) {
                novoXpPlanta -= faseAtual.getXpNecessario();
                plantaUsuario.setFase(proximaFase.get());
            } else {
                novoXpPlanta = faseAtual.getXpNecessario();
            }
        }
        plantaUsuario.setXpAtual(novoXpPlanta);
        plantaUsuarioRepository.save(plantaUsuario);

        // 6. Atualizar XP e Nível do Usuário
        if (usuario != null) {
            int xpGanhoUsuario = (int) Math.round(xpGanhoPlanta * 0.5);
            int xpAtualUsuario = (usuario.getXpTotal() != null) ? usuario.getXpTotal() : 0;
            int novoXpTotalUsuario = xpAtualUsuario + xpGanhoUsuario;

            usuario.setXpTotal(novoXpTotalUsuario);
            usuario.setNivelUsuario((novoXpTotalUsuario / 100) + 1);
            usuarioRepository.save(usuario);
        }

        // 7. Mapear e Salvar o Registro
        Registro registro = modelMapper.map(dto, Registro.class);
        registro.setPlantaUsuario(plantaUsuario);
        registro.setDataRegistro(LocalDateTime.now());
        registro.setXpRecebido(xpGanhoPlanta);

        Registro salvo = registroRepository.save(registro);
        return new RegistroResponseDTO(salvo);
    }

    @Transactional
    public RegistroResponseDTO atualizar(Long id, RegistroRequestDTO dto) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado. Id: " + id));

        PlantaUsuario plantaUsuario = plantaUsuarioRepository.findById(dto.getPlantaUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Planta do usuário não encontrada. Id: " + dto.getPlantaUsuarioId()));

        modelMapper.map(dto, registro);
        registro.setId(id);
        registro.setPlantaUsuario(plantaUsuario);

        Registro atualizado = registroRepository.save(registro);
        return new RegistroResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!registroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registro não encontrado. Id: " + id);
        }
        registroRepository.deleteById(id);
    }
}