package com.cutanddry.filesync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S3FileSyncTask {

    private final Logger logger = LoggerFactory.getLogger(S3FileSyncTask.class);
    private final S3FileSyncService fileSyncService;


    @Autowired
    public S3FileSyncTask(S3FileSyncService fileSyncService) {
        this.fileSyncService = fileSyncService;
        logger.info("S3FileSyncTask initialized");
    }

//    @PostConstruct
//    public void init() {
//        logger.info("Starting file synchronization");
//        try {
//            fileSyncService.syncFiles();
//            logger.info("File synchronization task started successfully");
//        } catch (Exception e) {
//            logger.error("Error during file synchronization task", e);
//        }
//    }
}
