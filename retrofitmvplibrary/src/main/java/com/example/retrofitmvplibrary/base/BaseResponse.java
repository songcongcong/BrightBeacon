package com.example.retrofitmvplibrary.base;

/**
 * @author Muyangmin
 * @since 1.0.0
 */
public class BaseResponse<DataType> {

    public static final int RESULT_CODE_SUCCESS = 200;
    public static final int RESULT_CODE_TOKEN_EXPIRED = 401;

    /**
     * 通用返回值属性
     */
    private int code;
    /**
     * 通用返回信息。
     */
    private String message;
    /**
     * 具体的内容。
     */
    private DataType data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataType getData() {
        return data;
    }

    public void setData(DataType data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}