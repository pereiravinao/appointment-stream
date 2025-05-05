package com.app.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.services.interfaces.StorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${minio.storage.bucket}")
    private String bucketName;

    @Value("${minio.storage.endpoint}")
    private String endpoint;

    @Override
    public void delete(String fileName) {
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(fileName).build());
        log.info("Arquivo deletado com sucesso: {}", fileName);
    }

    @Override
    public String getSignedUrl(String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .getObjectRequest(getObjectRequest)
                    .build();

            URL url = s3Presigner.presignGetObject(presignRequest).url();
            return url.toString();
        } catch (Exception e) {
            log.error("Erro ao gerar URL assinada para download", e);
            throw new RuntimeException("Erro ao gerar URL assinada", e);
        }
    }

    @Override
    public String generatePresignedUploadUrl(String objectName) {
        try {
            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(60)) // 60 minutos
                    .putObjectRequest(req -> req
                            .bucket(bucketName)
                            .key(objectName)
                            .build())
                    .build();

            URL url = s3Presigner.presignPutObject(presignRequest).url();
            log.info("URL de upload pré-assinada gerada: {}", url);
            return url.toString();
        } catch (Exception e) {
            log.error("Erro ao gerar presigned URL para upload", e);
            throw new RuntimeException("Erro ao gerar presigned URL", e);
        }
    }

    @Override
    public String getPublicUrl(String objectName) {
        return endpoint + "/" + bucketName + "/" + objectName;
    }

    @Override
    public String concatAudioChunks(String sessionId) {
        try {
            // 1. Listar todos os objetos no diretório da sessão
            String prefix = "session/" + sessionId + "/chunk/";
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .build();
            
            // 2. Obter e ordenar os chunks
            List<S3Object> audioChunks = new ArrayList<>(s3Client.listObjectsV2(listRequest).contents());
            
            if (audioChunks.isEmpty()) {
                log.warn("Nenhum chunk de áudio encontrado para a sessão: {}", sessionId);
                return null;
            }
            
            log.info("Encontrados {} chunks de áudio para concatenar", audioChunks.size());
            
            // 3. Criar diretório temporário para armazenar os arquivos baixados
            Path tempDir = Files.createTempDirectory("audio-concat-");
            
            // 4. Lista de arquivos temporários para a concatenação
            List<File> chunkFiles = new ArrayList<>();
            
            // 5. Baixar cada chunk para arquivos temporários
            for (S3Object s3Object : audioChunks) {
                File tempFile = downloadObject(s3Object.key(), tempDir);
                chunkFiles.add(tempFile);
            }
            
            // 6. Concatenar os arquivos usando FFmpeg
            String outputFilePath = tempDir.resolve("output.webm").toString();
            concatenateWithFFmpeg(chunkFiles, outputFilePath);
            
            // 7. Fazer upload do arquivo concatenado
            File outputFile = new File(outputFilePath);
            String targetKey = "session/" + sessionId + "/full-audio.webm";
            uploadFile(outputFile, targetKey);
            
            // 8. Limpar arquivos temporários
            for (File file : chunkFiles) {
                file.delete();
            }
            outputFile.delete();
            Files.delete(tempDir);
            
            log.info("Concatenação concluída com sucesso. Arquivo final: {}", targetKey);
            return targetKey;
            
        } catch (Exception e) {
            log.error("Erro ao concatenar chunks de áudio", e);
            throw new RuntimeException("Falha ao concatenar chunks de áudio: " + e.getMessage(), e);
        }
    }
    
    /**
     * Faz download de um objeto do bucket para um arquivo temporário
     */
    private File downloadObject(String key, Path tempDir) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        
        // Extrair o nome do arquivo da chave
        String fileName = key.substring(key.lastIndexOf('/') + 1);
        Path filePath = tempDir.resolve(fileName);
        File file = filePath.toFile();
        
        try (ResponseInputStream<GetObjectResponse> s3ObjectInputStream = s3Client.getObject(getObjectRequest);
             FileOutputStream outputStream = new FileOutputStream(file)) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = s3ObjectInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        
        return file;
    }
    
    /**
     * Faz upload de um arquivo para o bucket
     */
    private void uploadFile(File file, String key) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("audio/webm")
                .build();
        
        s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
    }
    
    /**
     * Concatena arquivos de áudio usando FFmpeg
     */
    private void concatenateWithFFmpeg(List<File> inputFiles, String outputFilePath) throws IOException, InterruptedException {
        // Criar arquivo de lista para o FFmpeg
        File listFile = new File(outputFilePath + ".txt");
        try (FileOutputStream fos = new FileOutputStream(listFile)) {
            for (File file : inputFiles) {
                String line = "file '" + file.getAbsolutePath().replace("\\", "\\\\") + "'\n";
                fos.write(line.getBytes());
            }
        }
        
        // Executar comando FFmpeg
        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg", 
                "-f", "concat", 
                "-safe", "0", 
                "-i", listFile.getAbsolutePath(),
                "-c", "copy",
                outputFilePath
        );
        
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        
        // Remover arquivo de lista
        listFile.delete();
        
        if (exitCode != 0) {
            // Capturar erro do processo
            try (InputStream errorStream = process.getErrorStream();
                 ByteArrayOutputStream result = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = errorStream.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
                String errorOutput = result.toString("UTF-8");
                log.error("Erro ao executar FFmpeg: {}", errorOutput);
            }
            throw new IOException("Falha ao concatenar arquivos de áudio com FFmpeg. Código de saída: " + exitCode);
        }
    }
}