package org.example.helpers;

public class APIResponse {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
    
    int code;
    String type;
    String message;

    //{"code":1,"type":"error","message":"Pet not found"}
}
