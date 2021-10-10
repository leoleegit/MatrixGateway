package com.matrix.media;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.matrix.media.config.MediaServerConfig;
import com.matrix.service.entity.Media;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class MediaSwitch {
    @Autowired
    private MediaServerConfig mediaServerConfig;

    private MinioClient minioClient;
    private String bucket;
    private String downloadEndpoint;

    public MinioClient getMinioClient() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, ErrorResponseException, XmlParserException, ServerException, InvalidResponseException {
        if(minioClient==null){
            String accessKey = mediaServerConfig.getAccessKey();
            String secretKey = mediaServerConfig.getSecretKey();

            String endpoint = mediaServerConfig.getEndpoint();
            this.bucket     = mediaServerConfig.getBucket();
            this.downloadEndpoint = mediaServerConfig.getDownloadEndpoint();

            this.minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found)
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
        return minioClient;
    }

    private static final String dateFormat = "YYYY-MM";
    public String bucket(){
        String bucket =  DateUtil.format(new Date(System.currentTimeMillis()),dateFormat);
        boolean found = false;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found)
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            log.error("",e);
            return this.bucket;
        }
        return bucket;
    }

    public String verify(String type, String contentType, long size){
        return null;
    }

    public String upload(InputStream inputStream, String contentType, long size) throws IOException, NoSuchAlgorithmException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, XmlParserException, ServerException, InvalidResponseException {
        return upload(null,inputStream,contentType,size);
    }

    public String upload(String id, InputStream inputStream,String contentType, long size) throws IOException, NoSuchAlgorithmException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, XmlParserException, ServerException, InvalidResponseException {
        if(id==null)
            id = UUID.fastUUID().toString();
        MinioClient minioClient = getMinioClient();
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucket()).object(id).stream(inputStream, size, -1)
                        .contentType(contentType)
                        .build());
        return id;
    }

    public InputStream get(String bucket, String id) throws IOException, NoSuchAlgorithmException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, XmlParserException, ServerException, InvalidResponseException {
        MinioClient minioClient = getMinioClient();
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(id)
                        .build());
    }

    public String minioUrl(String id) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, ErrorResponseException, XmlParserException, ServerException, InvalidResponseException {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucket())
                        .object(id)
                        .expiry(7, TimeUnit.DAYS)
                        .build());
    }

    public String url(Media media){
        return StrUtil.format("{}/media/file/bucket/{}/{}",this.downloadEndpoint,media.getMediaId(),media.getName());
    }
}
