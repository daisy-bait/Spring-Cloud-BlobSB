package co.azure.daisy.daisy_azure_storage.controller;

import co.azure.daisy.daisy_azure_storage.azure.IBlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/blob")
public class BlobController {

    @Autowired
    IBlobStorageService blobStorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadBlob(
            @RequestPart("blob")MultipartFile blob) throws IOException {

        String url;

        try {
            url = blobStorageService.uploadBlob(blob);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return ResponseEntity.ok(url);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBlob(@RequestPart("name") String blobName) {
        return ResponseEntity.ok(blobStorageService.deleteBlob(blobName));
    }

    @GetMapping("/verify-existence")
    public ResponseEntity<String> verifyBlobExistence(@RequestPart("name") String blobName) {
        return ResponseEntity.ok(blobStorageService.doesBlobExist(blobName) ? "blob exists" : "blob not exists");
    }

}
