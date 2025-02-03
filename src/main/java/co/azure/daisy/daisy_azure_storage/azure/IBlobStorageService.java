package co.azure.daisy.daisy_azure_storage.azure;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IBlobStorageService {

    public String uploadBlob(MultipartFile blob) throws IOException;

}
