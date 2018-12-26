package cn.javon.core.result;

import cn.javon.core.common.Constant;

public class AjaxResult {

    private int code;

    private String message;

    private Object data;

    public AjaxResult() {
    }

    public AjaxResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static AjaxResult success() {
        return success(Constant.SUCCESS_MESSAGE);
    }

    public static AjaxResult success(String message) {
        return result(0, message, null);
    }

    public static AjaxResult success(String message, String data) {
        return result(0, message, data);
    }

    public static AjaxResult error() {
        return error(-1, Constant.ERROR_MESSAGE);
    }

    public static AjaxResult error(String message) {
        return error(-1, message);
    }

    public static AjaxResult error(int code, String message) {
        return result(code, message, null);
    }

    private static AjaxResult result(int code, String message, Object data) {
        return new AjaxResult(code, message, data);
    }

}
