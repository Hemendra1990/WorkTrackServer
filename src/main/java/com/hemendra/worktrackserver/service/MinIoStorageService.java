package com.hemendra.worktrackserver.service;

import com.google.common.base.Preconditions;
import com.hemendra.worktrackserver.dto.StorageRequestObject;
import com.hemendra.worktrackserver.exception.StorageException;
import io.micrometer.common.util.StringUtils;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@Slf4j
public class MinIoStorageService {
    @Autowired
    private MinioClient minioClient;

    public boolean checkFileExists(StorageRequestObject storageRequestObject) throws ServiceException {
        try {
            String bucketName = storageRequestObject.getBucketName();
            String s3BucketName = convertToS3BucketName(bucketName);
            if (!checkBucketExists(s3BucketName)) {
                return false;
            }
            String folderName = storageRequestObject.getFolderName();
            String fileName = storageRequestObject.getCustomFileName();
            String objectName = folderName + fileName;
            StatObjectArgs statObjectArgs = StatObjectArgs.builder()
                    .bucket(s3BucketName)
                    .object(objectName)
                    .build();
            StatObjectResponse statObjectResponse = minioClient.statObject(statObjectArgs);
            // If the object exists, return true
            return statObjectResponse != null;
        } catch (MinioException e) {
            if (e instanceof ErrorResponseException && ((ErrorResponseException) e).errorResponse().code().equals("NoSuchKey")) {
                return false;
            }
            throw new StorageException("MinioStorageService.checkFileExists");
        } catch (Exception ex) {
            throw new StorageException("MinioStorageService.checkFileExists");
        }
    }

    public boolean delete(StorageRequestObject storageRequestObject) throws ServiceException {
        try {
            String bucketName = storageRequestObject.getBucketName();
            String s3BucketName = convertToS3BucketName(bucketName);
            Preconditions.checkArgument(checkBucketExists(s3BucketName), "Bucket Does Not Exists");
            String folderName = storageRequestObject.getFolderName();
            String fileName = storageRequestObject.getCustomFileName();
            String objectName = folderName + fileName;
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(s3BucketName)
                    .object(objectName)
                    .build();
            minioClient.removeObject(removeObjectArgs);
            // If the object is successfully deleted, return true
            return true;
        } catch (Exception ex) {
            throw new StorageException("MinioStorageService.delete");
        }
    }

    public void uploadFile(StorageRequestObject storageRequestObject, MultipartFile dataFile, boolean deletePreviousFile) throws ServiceException {
        try {
            String bucketName = storageRequestObject.getBucketName().replaceAll("[^a-zA-Z0-9]", "");
            String s3BucketName = convertToS3BucketName(bucketName);
            if (!checkBucketExists(s3BucketName)) {
                createBucket(s3BucketName);
            } else {
                if (deletePreviousFile) {
                    deleteAllFilesInBucket(storageRequestObject);
                }
            }
            String folderName = storageRequestObject.getFolderName();
            String fileName = storageRequestObject.getCustomFileName();
            String objectName = folderName + fileName;
            this.minioClient.putObject(PutObjectArgs.builder().bucket(s3BucketName).object(objectName)
                    .stream(dataFile.getInputStream(), dataFile.getSize(), -1).build());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new StorageException("MinioStorageService.upload");
        }
    }

    public boolean checkBucketExists(String bucketName) {
        String s3BucketName = convertToS3BucketName(bucketName);
        try {
            BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(s3BucketName).build();
            return minioClient.bucketExists(bucketExistsArgs);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    public Resource download(String bucketName, String objectPath) throws ServiceException {
        String s3BucketName = convertToS3BucketName(bucketName);
        try {
            InputStream inputStream = this.minioClient.getObject(GetObjectArgs.builder()
                    .bucket(s3BucketName)
                    .object(objectPath)
                    .build());
            return new InputStreamResource(inputStream);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new StorageException("MinioStorageService.download");
        }
    }

    public Resource download(StorageRequestObject storageRequestObject) throws ServiceException {
        try {
            String bucketName = storageRequestObject.getBucketName().replaceAll("[^a-zA-Z0-9]", "");
            Preconditions.checkArgument(checkBucketExists(bucketName), "Bucket does not exist");

            String folderName = storageRequestObject.getFolderName();
            String fileName = storageRequestObject.getCustomFileName();
            String objectName = folderName + fileName;
            InputStream inputStream = this.minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
            return new InputStreamResource(inputStream);
        } catch (Exception ex) {
            throw new StorageException("MinioStorageService.download");
        }
    }

    public void createBucket(String bucketName) throws ServiceException {
        String s3BucketName = convertToS3BucketName(bucketName);
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(s3BucketName)
                    .build());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new StorageException("MinioStorageService.createBucket");
        }
    }

    public void deleteAllFilesInBucket(StorageRequestObject storageRequestObject) throws ServiceException {
        try {
            String bucketName = storageRequestObject.getBucketName().replaceAll("[^a-zA-Z0-9]", "");
            String folderName = storageRequestObject.getFolderName().replaceAll("^/|/$", "");
            String customFileName = storageRequestObject.getCustomFileName();
            /*if (!folderName.isEmpty() && !customFileName.isEmpty()) {
                folderName += "/" + customFileName;
            }*/
            // Ensure folderName ends with '/'
            if (StringUtils.isNotBlank(folderName) && !folderName.endsWith("/")) {
                folderName += "/";
            }
            Iterable<Result<Item>> objects = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .prefix(folderName)
                    .build());

            if (objects != null) {
                for (Result<Item> object : objects) {
                    String objectName = object.get().objectName();
                    minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
                }
            }
        } catch (Exception ex) {
            throw new StorageException("MinioStorageService.deleteExistsFilesInBucket");
        }
    }

    public String convertToS3BucketName(String input) {
        // Convert to lowercase
        String bucketName = input.toLowerCase();

        // Replace invalid characters with hyphens
        bucketName = bucketName.replaceAll("[^a-z0-9.-]", "-");

        // Ensure it starts with a letter or number
        if (!Character.isLetterOrDigit(bucketName.charAt(0))) {
            bucketName = "a" + bucketName;
        }

        // Ensure it ends with a letter or number
        if (!Character.isLetterOrDigit(bucketName.charAt(bucketName.length() - 1))) {
            bucketName = bucketName + "z";
        }

        // Replace consecutive periods with a single period
        while (bucketName.contains("..")) {
            bucketName = bucketName.replace("..", ".");
        }

        // Truncate to a maximum of 63 characters
        if (bucketName.length() > 63) {
            bucketName = bucketName.substring(0, 63);
        }

        // Ensure the bucket name is at least 3 characters long
        if (bucketName.length() < 3) {
            bucketName = bucketName + "xyz".substring(0, 3 - bucketName.length());
        }

        return bucketName;
    }
}
