package com.example.habitforgeapi.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String mensaje;
    private int status;
    private LocalDateTime timestamp;
    private String errorDetails;

    public ErrorResponse(String mensaje, int status, String errorDetails) {
        this.mensaje = mensaje;
        this.status = status;
        this.errorDetails = errorDetails;
        this.timestamp = LocalDateTime.now();
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}
