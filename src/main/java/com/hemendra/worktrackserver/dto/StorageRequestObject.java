package com.hemendra.worktrackserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StorageRequestObject {
    @JsonProperty("bucketName")
    private String bucketName;
    @JsonProperty("folderName")
    private String folderName;
    @JsonProperty("customFileName")
    private String customFileName;
    @JsonProperty("contentType")
    private String contentType;
}
