package co.azure.daisy.daisy_azure_storage.azure;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class BlobStorageService implements IBlobStorageService {

    private final BlobServiceClient blobServiceClient;

    BlobContainerClient containerClient;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Autowired
    public BlobStorageService(final BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;

    }

    @PostConstruct
    public void init() {
        this.containerClient = blobServiceClient.getBlobContainerClient(containerName);
    }

    @Override
    public String uploadBlob(MultipartFile blob) throws IOException {
        String keyName = UUID.randomUUID() + "_" + blob.getOriginalFilename();

        BlobClient blobClient = containerClient.getBlobClient(keyName);

        blobClient.upload(blob.getInputStream(), blob.getSize(), true);

        return blobClient.getBlobUrl();

    }

}
