package dev.cafeina.imageapi.controller;

import dev.cafeina.imageapi.dto.ImageRes;
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
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/image";

    @GetMapping
    public String homepage() {
        return "olá";
    }

    @PostMapping
    public ResponseEntity<ImageRes> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        if (!image.getContentType().startsWith("image")) {
            return ResponseEntity.badRequest().body(null);
        }

        Files.copy(image.getInputStream(), Paths.get(UPLOAD_DIRECTORY, image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

        ImageRes imageRes = new ImageRes();
        imageRes.setContentType(image.getContentType());
        imageRes.setOriginalFilename(image.getOriginalFilename());
        imageRes.setSize(image.getSize()/1024);

        return ResponseEntity.ok(imageRes);
    }
}
