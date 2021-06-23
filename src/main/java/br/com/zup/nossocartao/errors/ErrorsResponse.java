package br.com.zup.nossocartao.errors;

public class ErrorsResponse {
    private String field;
    private String error;

    public ErrorsResponse(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
}