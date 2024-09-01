package com.hemendra.worktrackserver.service;


import com.hemendra.worktrackserver.dto.StorageRequestObject;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class MinIOUtil {
    private final MinIoStorageService minioStorageService;
    private final MinioClient minioClient;

    public String prepareMinIOObject(MultipartFile file, String objectId, String filePath, String bucketName) throws Exception {
        return prepareMinIOObject(file, objectId, false, filePath, bucketName);
    }

    public String prepareMinIOObject(MultipartFile file, String objectId, boolean deletePrevFile, String filePath, String bucketName) throws Exception {
        try {
            if (Objects.nonNull(file)) {
                StorageRequestObject storageRequestObject = new StorageRequestObject();
                storageRequestObject.setBucketName(bucketName);
                storageRequestObject.setFolderName(getFormattedFolderName(filePath, objectId));
                storageRequestObject.setCustomFileName(file.getOriginalFilename());
                
                minioStorageService.uploadFile(storageRequestObject, file, deletePrevFile);
//                return generatePreSignedUrl(storageRequestObject);
                return getFormattedFolderName(filePath, objectId) + file.getOriginalFilename();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("Failed to upload file");
        }
        return "";
    }

    public String prepareMinIOObject(MultipartFile file, String filePath, boolean deletePrevFile, String bucketName) throws Exception {
        try {
            if (Objects.nonNull(file)) {
                StorageRequestObject storageRequestObject = new StorageRequestObject();
                storageRequestObject.setBucketName(bucketName);
                storageRequestObject.setFolderName(filePath);
                storageRequestObject.setCustomFileName(file.getOriginalFilename());

                minioStorageService.uploadFile(storageRequestObject, file, deletePrevFile);
//                return generatePreSignedUrl(storageRequestObject);
                return filePath + file.getOriginalFilename();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("Failed to upload file");
        }
        return "";
    }

    public String generatePreSignedUrl(StorageRequestObject storageRequestObj) {
        try {
            String objectName = storageRequestObj.getFolderName() + "/" + storageRequestObj.getCustomFileName();

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(storageRequestObj.getBucketName().replaceAll("[^a-zA-Z0-9]", ""))
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return "";
        }
    }

    static String getFormattedFolderName(String folderExpression, String value) {
        return folderExpression.formatted(value);
    }

}
