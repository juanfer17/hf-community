package com.hfcommunity.hf_community_hub.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/uploads")
public class FileController {

    private final Path fileStorageLocation;

    public FileController(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                String contentType = determineContentType(fileName);
                
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    private String determineContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        } else {
            return "application/octet-stream";
        }
    }
}