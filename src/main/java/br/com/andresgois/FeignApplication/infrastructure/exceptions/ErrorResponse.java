package br.com.andresgois.FeignApplication.infrastructure.exceptions;

public class ErrorResponse {
    private Integer status;
    private String msg;

    public ErrorResponse() {
    }

    public ErrorResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
