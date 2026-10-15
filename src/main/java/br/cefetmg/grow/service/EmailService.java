package br.cefetmg.grow.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    /**
     * Envia email HTML.
     */
    public void enviarHtml(String para, String assunto, String html) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            helper.setFrom(remetente, "BioGrow");
            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(html, true);

            mailSender.send(mensagem);
            System.out.println("✅ Email enviado para: " + para);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println("❌ Erro ao enviar email para " + para + ": " + e.getMessage());
        }
    }



    public void enviarAlertaPlanta(String para, String nomeUsuario, String apelido, int saude) {
        String assunto = "🌱 " + apelido + " precisa de cuidados!";
        String html = montarTemplate(
            "Sua planta precisa de água!",
            "Oi " + nomeUsuario + "!",
            "A planta <strong>" + apelido + "</strong> está com a saúde em <strong>" + saude + "%</strong>. " +
            "Se você não cuidar dela logo, ela pode murchar. 💧",
            "Cuidar agora",
            "https://biogrow.app/minhas-plantas"
        );
        enviarHtml(para, assunto, html);
    }

    public void enviarReengajamento(String para, String nomeUsuario, int diasSemEntrar) {
        String assunto = "🌱 Sentimos sua falta no Grow!";
        String html = montarTemplate(
            "Volte a cultivar seu conhecimento",
            "Oi " + nomeUsuario + "!",
            "Faz <strong>" + diasSemEntrar + " dias</strong> que você não entra no BioGrow. " +
            "Suas plantas sentem sua falta! Que tal dar uma olhada? 🌿",
            "Abrir BioGrow",
            "https://biogrow.app/home"
        );
        enviarHtml(para, assunto, html);
    }

    public void enviarBoasVindas(String para, String nomeUsuario) {
        String assunto = "🌱 Bem-vindo ao Grow!";
        String html = montarTemplate(
            "Sua jornada começa agora!",
            "Olá, " + nomeUsuario + "!",
            "Estamos muito felizes em ter você com a gente. " +
            "Adote sua primeira planta, entre em turmas e comece a ganhar XP. Boa sorte! 🍀",
            "Começar agora",
            "https://biogrow.app/home"
        );
        enviarHtml(para, assunto, html);
    }



    private String montarTemplate(String titulo, String saudacao, String corpo, String ctaTexto, String ctaUrl) {
        return "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<meta charset='UTF-8'>" +
            "<style>" +
            "body { font-family: Arial, sans-serif; background: #F8FDF9; margin: 0; padding: 0; }" +
            ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
            ".header { background: linear-gradient(135deg, #1B4332, #2D6A4F); padding: 30px; border-radius: 16px 16px 0 0; text-align: center; }" +
            ".logo { color: #B7E4C7; font-size: 24px; font-weight: 800; letter-spacing: 2px; }" +
            ".content { background: #FFFFFF; padding: 40px 30px; border-radius: 0 0 16px 16px; }" +
            ".content h1 { color: #1B4332; font-size: 24px; margin: 0 0 20px; }" +
            ".content p { color: #4a6355; font-size: 16px; line-height: 1.6; margin: 0 0 20px; }" +
            ".btn { display: inline-block; background: linear-gradient(135deg, #2D6A4F, #40916C); color: #FFFFFF; padding: 14px 32px; border-radius: 12px; text-decoration: none; font-weight: 700; font-size: 15px; }" +
            ".footer { text-align: center; color: #95D5B2; font-size: 12px; margin-top: 30px; }" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<div class='container'>" +
            "<div class='header'>" +
            "<div class='logo'>🌱 GROW</div>" +
            "</div>" +
            "<div class='content'>" +
            "<h1>" + titulo + "</h1>" +
            "<p>" + saudacao + "</p>" +
            "<p>" + corpo + "</p>" +
            "<div style='text-align: center; margin: 30px 0;'>" +
            "<a href='" + ctaUrl + "' class='btn'>" + ctaTexto + "</a>" +
            "</div>" +
            "</div>" +
            "<div class='footer'>" +
            "BioGrow © 2026 · Você recebeu este email porque tem uma conta no BioGrow." +
            "</div>" +
            "</div>" +
            "</body>" +
            "</html>";
    }
}