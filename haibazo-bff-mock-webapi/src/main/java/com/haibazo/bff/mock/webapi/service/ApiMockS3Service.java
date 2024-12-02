package com.haibazo.bff.mock.webapi.service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

/**
 * Before running this Java V2 code example, set up your development
 * environment, including your credentials.
 * <p>
 * For more information, see the following documentation topic:
 * <p>
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

@Service
public class ApiMockS3Service {

    /**
     * Logger instance for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ApiMockSettingService.class);

    private S3Client s3Client;

    @Value("${haibazo.bff.mock.storage}")
    private String mockStorage;

    @Value("${haibazo.bff.mock.s3.region}")
    private String region;

    @Value("${haibazo.bff.aws.iam.access-key}")
    private String accessKey;

    @Value("${haibazo.bff.aws.iam.secret-key}")
    private String secretKey;

    @Value("${haibazo.bff.mock.s3.bucket-name}")
    private String bucketName;

    @Value("${haibazo.bff.mock.s3.folder}")
    private String mockBaseFolder;

    @PostConstruct
    public void initiate() {
        if (mockStorage.equals("s3")) {
            createS3Client();
        }
    }

    /**
     * Retrieves the bytes of an object stored in an Amazon S3 bucket and saves them
     * to a local file.
     *
     * @param keyName The key (or name) of the S3 object.
     * @param path    The local file path where the object's bytes will be saved.
     * @throws IOException If an I/O error occurs while writing the bytes to the
     *                     local file.
     * @throws S3Exception If an error occurs while retrieving the object from the
     *                     S3 bucket.
     */
    public void getObjectBytes(String keyName, String path) {
        logger.info("S3_START_DOWNLOAD_OBJECT: {}", getMockFilePath(keyName));

        try {
            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(getMockFilePath(keyName))
                    .bucket(bucketName)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObject(objectRequest,
                    ResponseTransformer.toBytes());
            byte[] data = objectBytes.asByteArray();

            // Write the data to a local file.
            File myFile = new File(path);
            OutputStream os = new FileOutputStream(myFile);
            os.write(data);
            logger.info("S3_WRITE_OBJECT_TO_FILE: {}", path);
            os.close();

        } catch (IOException ex) {
            logger.error("S3_WRITE_OBJECT_TO_FILE_FAILED: {}", ex.getMessage());
            System.exit(1);
        } catch (S3Exception e) {
            logger.error("S3_DOWNLOAD_OBJECT_FAILED: {}", e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Resolves the absolute file path for a mock file.
     * Combines the base folder path with the relative path and normalizes it.
     * 
     * @param path The relative path to resolve
     * @return The absolute file path as a string
     */
    public String getMockFilePath(String path) {
        Path mockBasePath = Path.of(mockBaseFolder);
        Path mockFilePath = mockBasePath.resolve(path).normalize();

        return mockFilePath.toString();
    }

    private S3Client createS3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        S3ClientBuilder builder = S3Client.builder().region(Region.of(region)).credentialsProvider(credentialsProvider);

        s3Client = builder.build();

        return s3Client;
    }

}