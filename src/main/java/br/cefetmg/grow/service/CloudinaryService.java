package br.cefetmg.grow.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload da imagem para a nuvem: " + e.getMessage());
        }
    }

        /**
     * Upload pra arquivos de atividade (PDF, docx, imagem).
     */
    public String uploadArquivoAtividade(MultipartFile file) {
    try {
        String contentType = file.getContentType();
        boolean isImage = contentType != null && contentType.startsWith("image/");

        // 👇 Extrai o nome do arquivo original COM extensão
        String nomeOriginal = file.getOriginalFilename();
        String publicId = (nomeOriginal != null) ? nomeOriginal : "arquivo";

        Map<String, Object> options = new HashMap<>();
        options.put("resource_type", isImage ? "image" : "raw");
        options.put("type", "upload");
        options.put("access_mode", "public");
        options.put("public_id", publicId);
        options.put("folder", "grow-atividades");
        

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return uploadResult.get("secure_url").toString();
    } catch (IOException e) {
        throw new RuntimeException("Erro ao fazer upload do arquivo: " + e.getMessage());
    }
}
}