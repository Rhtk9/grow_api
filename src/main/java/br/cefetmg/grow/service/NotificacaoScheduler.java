package br.cefetmg.grow.service;

import br.cefetmg.grow.model.PlantaUsuario;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.PlantaUsuarioRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoScheduler {

    private final UsuarioRepository usuarioRepository;
    private final PlantaUsuarioRepository plantaRepository;
    private final EmailService emailService;

    /**
     * Roda todo dia às 9h da manhã (horário de Brasília).
     * Cron: segundo minuto hora dia-do-mês mês dia-da-semana
     */
   @Scheduled(cron = "0 0 9 * * *", zone = "America/Sao_Paulo")
  // @Scheduled(cron = "0 * * * * *", zone = "America/Sao_Paulo")
    public void verificarNotificacoesDiarias() {
        System.out.println(" Rodando verificação diária de notificações...");

        verificarPlantasComSaudeBaixa();
        verificarUsuariosInativos();

        System.out.println(" Verificação concluída.");
    }

    private void verificarPlantasComSaudeBaixa() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        for (Usuario usuario : usuarios) {
            if (usuario.getEmail() == null) continue;
            if (!Boolean.TRUE.equals(usuario.getNotificacoes())) continue;

            List<PlantaUsuario> plantas = plantaRepository.findByUsuarioId(usuario.getId());

            for (PlantaUsuario planta : plantas) {
                if (planta.getSaude() != null && planta.getSaude() < 40) {
                    emailService.enviarAlertaPlanta(
                        usuario.getEmail(),
                        usuario.getNome(),
                        planta.getApelido(),
                        planta.getSaude()
                    );
                }
            }
        }
    }

    private void verificarUsuariosInativos() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        for (Usuario usuario : usuarios) {
            if (usuario.getEmail() == null) continue;
            if (!Boolean.TRUE.equals(usuario.getNotificacoes())) continue;

            LocalDateTime ultimoLogin = usuario.getUltimoLogin();
            if (ultimoLogin == null) {
                ultimoLogin = usuario.getDataCadastro();
            }
            if (ultimoLogin == null) continue;

            long diasSemEntrar = ChronoUnit.DAYS.between(ultimoLogin, LocalDateTime.now());

            if (diasSemEntrar >= 3 && diasSemEntrar <= 30) {
                emailService.enviarReengajamento(
                    usuario.getEmail(),
                    usuario.getNome(),
                    (int) diasSemEntrar
                );
            }
        }
    }
}