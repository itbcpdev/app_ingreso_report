package com.itbcp.report.shared;

public class WebResponse<T> {
    private final T result;
    private final boolean success;

    public WebResponse(T result) {
        this.result = result;
        this.success = true;
    }

    public T getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }
}
