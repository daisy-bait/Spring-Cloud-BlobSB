package co.azure.daisy.daisy_azure_storage.controller;

import co.azure.daisy.daisy_azure_storage.azure.BlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/blob")
public class BlobController {

    @Autowired
    BlobStorageService blobStorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<String> uploadBlob(
            @RequestPart("blob")MultipartFile blob) throws IOException {

        String url;

        try {
            url = blobStorageService.uploadBlob(blob);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return ResponseEntity.ok(url);

    }

}
