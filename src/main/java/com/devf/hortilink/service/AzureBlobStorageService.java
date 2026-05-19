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
    
    /**
     * Exclui um arquivo da nuvem usando a sua URL absoluta
     *
     * @param fileUrl A URL completa do arquivo salva no banco de dados
     */
    public void excluirArquivo(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return;
        }

        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            // A URL salva é algo como: https://conta.blob.core.windows.net/hortilink/produto/1/uuid.jpg
            // Precisamos extrair apenas o caminho do blob (ex: "produto/1/uuid.jpg")
            String marcador = containerName + "/";
            if (fileUrl.contains(marcador)) {
                String blobName = fileUrl.substring(fileUrl.indexOf(marcador) + marcador.length());
                
                BlobClient blobClient = containerClient.getBlobClient(blobName);
                
                // deleteIfExists é mais seguro. Se o arquivo já não estiver lá, ele não quebra a API
                blobClient.deleteIfExists();
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao excluir o arquivo da Azure: " + fileUrl, e);
        }
    }
}
