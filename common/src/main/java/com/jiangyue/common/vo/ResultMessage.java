package com.jiangyue.common.vo;

/**
 * @author liuyongkang
 * @date Create in 2022/6/22 15:03
 */
public class ResultMessage {

    private boolean success;

    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultMessage() {
    }

    public ResultMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultMessage{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
