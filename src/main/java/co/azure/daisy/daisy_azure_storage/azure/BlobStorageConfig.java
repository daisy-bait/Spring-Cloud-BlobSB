package co.azure.daisy.daisy_azure_storage.azure;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h3>Configuración para Azure Blob Storage.</h3>
 * <p>
 * Esta clase se encarga de configurar el cliente de Azure Blob Storage utilizando
 * las credenciales proporcionadas en el archivo `application.properties`.
 * </p>
 *
 * <p>
 * <strong>Anotaciones:</strong>
 * <ul>
 *   <li>{@code @Configuration}: Indica que esta clase es una clase de configuración de Spring.</li>
 *   <li>{@code @Value}: Inyecta los valores de las propiedades definidas en `application.properties`.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Propiedades:</strong>
 * <ul>
 *   <li>{@code accountName}: Nombre de la cuenta de almacenamiento de Azure.</li>
 *   <li>{@code accountKey}: Clave de acceso de la cuenta de almacenamiento.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Método {@code blobServiceClient()}:</strong>
 * <ul>
 *   <li>Construye la cadena de conexión ({@code connectionString}) usando el nombre de la cuenta y la clave de acceso.</li>
 *   <li>Crea una instancia de {@code BlobServiceClient}, que es el cliente principal para interactuar con Azure Blob Storage.</li>
 *   <li>Retorna el cliente configurado.</li>
 * </ul>
 * </p>
 *<br>
 * <p>
 * <h4><strong>Comparación con AWS S3:</strong></h4>
 * <table border="1">
 *   <tr>
 *     <th>AWS S3 ({@code S3Config.java})</th>
 *     <th>Azure Blob Storage ({@code AzureBlobStorageConfig.java})</th>
 *   </tr>
 *   <tr>
 *     <td>{@code AwsBasicCredentials.create(accessKey, secretKey)}</td>
 *     <td>{@code String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;...", accountName, accountKey)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code S3Client.builder()}</td>
 *     <td>{@code BlobServiceClientBuilder()}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code StaticCredentialsProvider.create(credentials)}</td>
 *     <td>No es necesario, la autenticación se hace mediante la cadena de conexión.</td>
 *   </tr>
 *   <tr>
 *     <td>{@code Region.US_EAST_2}</td>
 *     <td>No es necesario especificar la región, ya que se incluye en la cadena de conexión.</td>
 *   </tr>
 * </table>
 * </p>
 */
@Configuration
public class BlobStorageConfig {

    @Value("${spring.cloud.azure.storage.blob.account-name}")
    private String accountName;

    @Value("${spring.cloud.azure.storage.blob.account-key}")
    private String accountKey;

    /**
     *Método encargado de crear la cadena de conexión para poder conectárse con el bucket,
     * encargándose de autenticar la conexión y definir la región de la misma.
     *
     * @return Instancia de {@code BlobServiceClient} para poder comunicarse y realizar
     * peticiones con el container de Azure Blob Storage; este es un cliente ya configurado
     */
    @Bean
    public BlobServiceClient blobServiceClient() {

        String conString = String.format(
                "DefaultEndpointsProtocol=https;" +
                        "AccountName=%s;" +
                        "AccountKey=%s;" +
                        "EndpointSuffix=core.windows.net",
                accountName, accountKey
        );

        return new BlobServiceClientBuilder()
                .connectionString(conString)
                .buildClient();
    }

}
