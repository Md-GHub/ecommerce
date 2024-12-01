package com.mdghub.project.exceptions;

public class ResourceNotFound extends RuntimeException {
    private String fileName;
    private String field;
    private String resourceName;
    private Long fieldId;

    public ResourceNotFound() {}


    public ResourceNotFound(String resourceName,String fieldName,Long fieldId) {
        super(String.format("%s not found in %s: %d", resourceName,fieldName,fieldId));
        this.fieldId = fieldId;
        this.resourceName = resourceName;
    }


}
