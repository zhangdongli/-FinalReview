package com.zdl.finalreview.common.exception;

import com.zdl.finalreview.common.enums.ReturnCode;

/**
 * Created by zhangdongli on 16/11/28.
 * 异常类
 */
public class FRException extends Exception {

    private ReturnCode returnCode;

    private String errorMsg;

    public ReturnCode getReturnCode() {
        return returnCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setReturnCode(ReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    public FRException(ReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    public FRException(ReturnCode returnCode,String errorMsg) {
        this.returnCode = returnCode;
        this.errorMsg = errorMsg;
    }
}
