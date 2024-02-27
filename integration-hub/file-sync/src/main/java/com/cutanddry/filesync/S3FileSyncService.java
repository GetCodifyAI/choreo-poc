package com.cutanddry.filesync;

import com.cutanddry.common.api.model.CustomerData;
import com.cutanddry.common.api.service.ApiService;
import com.cutanddry.common.properties.AwsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3FileSyncService implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(S3FileSyncService.class);

    private final AwsProperties awsProperties;
    private final S3Properties s3Properties;
    private final ApiService apiService;
    private final ApiAuthProperties apiAuthProperties;
    private final CsvMapper csvMapper;
    private final S3Client s3Client;

    @Autowired
    public S3FileSyncService(
            AwsProperties awsProperties,
            S3Properties s3Properties,
            ApiService apiService,
            ApiAuthProperties apiAuthProperties,
            CsvMapper csvMapper // Inject CsvMapper
    ) {
        this.awsProperties = awsProperties;
        this.s3Properties = s3Properties;
        this.apiService = apiService;
        this.apiAuthProperties = apiAuthProperties;
        this.csvMapper = csvMapper;

        try {
            AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                    awsProperties.getAccessKeyId(),
                    awsProperties.getSecretKey()
            );

            Region awsRegion = Region.of(awsProperties.getRegion());

            this.s3Client = S3Client.builder()
                    .region(awsRegion)
                    .credentialsProvider(() -> awsCredentials)
                    .build();

            logger.info("S3 client initialized: {}", (s3Client != null));
        } catch (Exception e) {
            logger.error("Error during S3 client initialization", e);
            throw new RuntimeException("Failed to initialize S3 client", e);
        }
    }

    public void syncFilesAndPushToApi() {
        List<String> fileNames = Arrays.asList(s3Properties.getFileName());

        for (String fileName : fileNames) {
            try {
                syncFiles(fileName);
            } catch (Exception e) {
                logger.error("Error processing CSV file: {}", fileName, e);
            }
        }
    }

    private void syncFiles(String filename) {
        logger.info("Starting file synchronization");

        List<String> fileNames = Arrays.asList(filename);
        logger.info("Number of files to sync: {}", fileNames.size());

        String sourceBucket = s3Properties.getSourceBucket();
        String sourceFolder = s3Properties.getSourceFolder();
        String destinationBucket = s3Properties.getDestinationBucket();
        String destinationFolder = s3Properties.getDestinationFolder();

        try {
            for (String fileName : fileNames) {
                String sourceKey = sourceFolder + fileName;
                String destinationKey = destinationFolder + fileName;
                copyObject(sourceBucket, sourceKey, destinationBucket, destinationKey);
            }
            logger.info("Objects copied successfully");
        } catch (Exception e) {
            logger.error("Error during file synchronization", e);
        }
    }

    private String readCsvContentFromS3(String sourceBucket, String sourceKey) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(sourceBucket)
                    .key(sourceKey)
                    .build();

            // Obtain the S3Object directly and read its content
            ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);

            try (InputStream contentStream = s3Object) {
                return new BufferedReader(new InputStreamReader(contentStream))
                        .lines()
                        .collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (Exception e) {
            logger.error("Error during reading CSV content from {} in bucket {}: {}",
                    sourceKey, sourceBucket, e.getMessage(), e);
            return ""; // Handle the exception according to your requirements
        }
    }


    private void processCsvContent(String csvContent) {
        // Process CSV content using CsvMapper
        List<CustomerData> customerDataList = csvMapper.mapCsvToCustomerDataFromS3(new ByteArrayInputStream(csvContent.getBytes()));
        pushDataToApi(customerDataList);
    }

    private void pushDataToApi(List<CustomerData> customerDataList) {
        // Obtain the access token outside the loop since it's a one-time operation
        String accessToken = null;
        try {
            accessToken = apiService.authenticateAndGetToken(
                    apiAuthProperties.getAuthEmail(),
                    apiAuthProperties.getAuthPassword(),
                    apiAuthProperties.getAuthTimezone(),
                    apiAuthProperties.getAuthDeviceId()
            );
        } catch (Exception e) {
            logger.error("Error obtaining access token", e);
            return;
        }

        for (CustomerData customerData : customerDataList) {
            try {
                apiService.createCustomer(customerData, accessToken);
            } catch (Exception e) {
                logger.error("Error pushing data to API", e);
            }
        }
    }

    private void copyObject(String sourceBucket, String sourceKey, String destinationBucket, String destinationKey) {
        try {
            CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder().sourceBucket(sourceBucket).sourceKey(sourceKey).destinationBucket(destinationBucket).destinationKey(destinationKey).build();

            s3Client.copyObject(copyObjectRequest);

            logger.info("Object copied from {} in bucket {} to {} in bucket {} successfully", sourceKey, sourceBucket, destinationKey, destinationBucket);
        } catch (Exception e) {
            logger.error("Error during object copy from {} in bucket {} to {} in bucket {}: {}", sourceKey, sourceBucket, destinationKey, destinationBucket, e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        if (s3Client != null) {
            s3Client.close();
            logger.info("S3 client closed");
        }
    }
}
