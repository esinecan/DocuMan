package com.documentsies.DocuMan.exception;

public class ErrorResponse {

    private String message;
    private int status;
    private long timestamp;

    // Constructor
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis(); // Capture current time
    }

    // Getters and Setters
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
