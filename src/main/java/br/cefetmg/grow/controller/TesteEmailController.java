package br.cefetmg.grow.controller;

import br.cefetmg.grow.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teste-email")
@RequiredArgsConstructor
public class TesteEmailController {

    private final EmailService emailService;

    @GetMapping("/boas-vindas")
    public String testarBoasVindas(@RequestParam String para) {
        emailService.enviarBoasVindas(para, "Teste");
        return "Email de boas-vindas enviado para " + para;
    }

    @GetMapping("/alerta-planta")
    public String testarAlerta(@RequestParam String para) {
        emailService.enviarAlertaPlanta(para, "Teste", "Jiboia do Teste", 25);
        return "Alerta de planta enviado para " + para;
    }

    @GetMapping("/reengajamento")
    public String testarReengajamento(@RequestParam String para) {
        emailService.enviarReengajamento(para, "Teste", 5);
        return "Email de reengajamento enviado para " + para;
    }
}