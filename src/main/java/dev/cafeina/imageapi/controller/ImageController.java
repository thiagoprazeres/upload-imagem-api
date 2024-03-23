package dev.cafeina.imageapi.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import dev.cafeina.imageapi.dto.ImageRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@CrossOrigin("https://victorious-pond-08fa6e70f.5.azurestaticapps.net")
public class ImageController {
    // public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/image";
    @Value("${AZURE_STORAGE_CONNECTION_STRING}")
    private String AZURE_STORAGE_CONNECTION_STRING;
    @Value("${AZURE_STORAGE_CONTAINER_NAME}")
    private String AZURE_STORAGE_CONTAINER_NAME;
    @Value("${AZURE_STORAGE_ENDPOINT}")
    private String AZURE_STORAGE_ENDPOINT;

    @PostMapping
    public ResponseEntity<ImageRes> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        if (!image.getContentType().startsWith("image")) {
            return ResponseEntity.badRequest().body(null);
        }

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint("https://<storage-account-name>.blob.core.windows.net/")
                .connectionString(AZURE_STORAGE_CONNECTION_STRING)
                .buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(AZURE_STORAGE_CONTAINER_NAME);
        BlobClient blobClient = containerClient.getBlobClient(image.getOriginalFilename());
        blobClient.upload(image.getInputStream(), image.getSize(), true);
        // Files.copy(image.getInputStream(), Paths.get(UPLOAD_DIRECTORY, image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

        ImageRes imageRes = new ImageRes();
        imageRes.setContentType(image.getContentType());
        imageRes.setOriginalFilename(image.getOriginalFilename());
        imageRes.setSize(image.getSize()/1024);

        return ResponseEntity.ok(imageRes);
    }
}
