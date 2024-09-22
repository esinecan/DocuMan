package com.documentsies.DocuMan.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response model containing message, status, and timestamp")
public class ErrorResponse {

    @Schema(description = "Error message", example = "Resource not found")
    private String message;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Timestamp when the error occurred", example = "1638282982000")
    private long timestamp;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
