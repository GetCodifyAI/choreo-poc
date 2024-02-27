package com.cutanddry.filesync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.cutanddry.common.api.service", "com.cutanddry.common.api.properties", "com.cutanddry.common.configuration",
    "com.cutanddry.common.connector", "com.cutanddry.common.properties", "com.cutanddry.common", "com.cutanddry.filesync"
})
public class S3FileSyncApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(S3FileSyncApplication.class);

    private final S3FileSyncService fileSyncService;
    private final S3Properties s3Properties;
    @Autowired
    private ConfigurableApplicationContext context;

    public S3FileSyncApplication(S3FileSyncService fileSyncService, S3Properties s3Properties) {
        this.fileSyncService = fileSyncService;
        this.s3Properties = s3Properties;
    }

    public static void main(String[] args) {
        SpringApplication.run(S3FileSyncApplication.class, args);
        logger.info("Application initialized");
    }

    @Override
    public void run(String... args) {
        fileSyncService.syncFilesAndPushToApi();
        System.exit(SpringApplication.exit(context));
    }
}
