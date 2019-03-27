package cn.wakeupeidolon.domain;

import cn.wakeupeidolon.enums.ErrorCode;

import java.io.Serializable;

/**
 * web访问返回对象
 * @author Wang Yu
 */
public class Result<T> implements Serializable {
    private T data;
    
    private String errMsg;
    
    private Integer code;
    
    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.setCode(ErrorCode.SUCCESS.getCode());
        result.setErrMsg(ErrorCode.SUCCESS.getErrMsg());
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> error(T data){
        Result<T> result = new Result<>();
        result.setCode(ErrorCode.UNKNOWN_ERROR.getCode());
        result.setErrMsg(ErrorCode.UNKNOWN_ERROR.getErrMsg());
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> error(){
        Result<T> result = new Result<>();
        result.setCode(ErrorCode.UNKNOWN_ERROR.getCode());
        result.setErrMsg(ErrorCode.UNKNOWN_ERROR.getErrMsg());
        return result;
    }
    
    public static <T> Result<T> error(ErrorCode errorCode){
        Result<T> result = new Result<>();
        result.setCode(errorCode.getCode());
        result.setErrMsg(errorCode.getErrMsg());
        return result;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public String getErrMsg() {
        return errMsg;
    }
    
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
}
