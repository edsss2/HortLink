package com.devf.hortilink.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

@Service
public class AzureBlobStorageService {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    /**
     * Salva o arquivo no Nuvem
     *
     * @param file O arquivo enviado
     * @param entityType O tipo de entidade (ex: "produto", "usuario")
     * @param entityId O ID da entidade (ex: 123)
     * @return O caminho relativo do arquivo (ex: "produto/123/uuid.jpg")
     * @throws IOException
     */
    public String salvarArquivo(MultipartFile file, String entityType, Long entityId) throws IOException {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        
        // 1. Gerar o nome único com a extensão
        String originalFilename = file.getOriginalFilename();
        String extensao = (originalFilename != null && originalFilename.contains(".")) 
                ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String nomeUnico = UUID.randomUUID().toString() + extensao;

        // 2. Criar o "Caminho Virtual" (o nome do blob)
        // Ex: "produto/123/uuid.jpg"
        String blobPath = String.format("%s/%s/%s", entityType, entityId, nomeUnico);

        BlobClient blobClient = containerClient.getBlobClient(blobPath);

        // 3. Upload direto do InputStream
        try (InputStream is = file.getInputStream()) {
            blobClient.upload(is, file.getSize(), true);
        }

        // 4. Retorna a URL final que será salva no banco SQL da HortLink
        return blobClient.getBlobUrl();
    }
}
