package com.documentsies.DocuMan.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exception thrown when a resource is not found")
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
