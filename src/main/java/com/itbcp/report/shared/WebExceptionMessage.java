package com.itbcp.report.shared;

public class WebExceptionMessage {
    private String title;
    private String message;
    private int status;

    public WebExceptionMessage(String title, String message, int status) {
        this.title = title;
        this.message = message;
        this.status = status;
    }

    public WebExceptionMessage() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
