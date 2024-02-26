package com.cutanddry.filesync;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "s3")
@Getter
@Setter
public class S3Properties {

    private String sourceBucket;
    private String sourceFolder;
    private String destinationBucket;
    private String destinationFolder;
    private String fileName;
}
