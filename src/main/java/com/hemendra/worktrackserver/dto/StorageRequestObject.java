package com.hemendra.worktrackserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StorageRequestObject implements Serializable {

    private static final long serialVersionUID = 24082024022411L;

    @JsonProperty("bucketName")
    private String bucketName;
    @JsonProperty("folderName")
    private String folderName;
    @JsonProperty("customFileName")
    private String customFileName;
    @JsonProperty("contentType")
    private String contentType;
}
