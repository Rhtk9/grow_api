package br.cefetmg.grow.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path rootLocation = Paths.get("uploads/especies");

    public String salvarArquivo(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return null;
            }
            Files.createDirectories(rootLocation);
            String nomeOriginal = file.getOriginalFilename();
            String extensao = nomeOriginal != null && nomeOriginal.contains(".") 
                ? nomeOriginal.substring(nomeOriginal.lastIndexOf(".")) 
                : ".jpg";
            
            String nomeArquivo = UUID.randomUUID().toString() + extensao;
            Path destino = rootLocation.resolve(nomeArquivo);
            
            Files.copy(file.getInputStream(), destino);
            
            // Retorna o caminho relativo ou URL que será salvo no banco de dados na coluna 'imagem'
            return "/uploads/especies/" + nomeArquivo;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao armazenar o arquivo.", e);
        }
    }
}