package com.itbcp.report.shared;

public class WebExceptionResponse {
    private final Boolean success;
    private WebExceptionMessage error;

    public WebExceptionResponse(String title, String message, int status) {
        this.error = new WebExceptionMessage();
        this.error.setTitle(title);
        this.error.setMessage(message);
        this.error.setStatus(status);
        this.success = false;
    }

    public WebExceptionResponse() {
        this.success = false;
    }

    public Boolean isSuccess() {
        return success;
    }

    public WebExceptionMessage getError() {
        return error;
    }

    public void setTitle(String title) {
        if (this.error == null)
            this.error = new WebExceptionMessage();

        this.error.setTitle(title);
    }

    public void setMessage(String message) {
        if (this.error == null)
            this.error = new WebExceptionMessage();

        this.error.setMessage(message);
    }

    public void setStatus(int status) {
        if (this.error == null)
            this.error = new WebExceptionMessage();

        this.error.setStatus(status);
    }
}