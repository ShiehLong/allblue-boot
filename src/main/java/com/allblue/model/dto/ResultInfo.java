package com.allblue.model.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端的HTTP调用的应答结果类.
 *
 * @title ResultInfo
 * @description 应答结果类，返回信息包括调用状态、可读的msg、结果数据.
 */
public class ResultInfo {

    private static Logger logger = LoggerFactory.getLogger(ResultInfo.class);

    // 应答结果状态码——成功
    private static final int RESULT_CODE_SUCCESS = 0;
    // 应答结果状态码——通用错误
    private static final int RESULT_CODE_COMMONERR = 9999;
    // NOT Exists
    private static final int RESULT_CODE_NOTEXIST = 1000;
    // business error
    private static final int RESULT_CODE_BUSINESSERR = 1100;

    private int status = RESULT_CODE_SUCCESS;
    private String message = "SUCCESS"; // 操作结果描述信息

    private Object data;// 操作返回数据绑定

    private ResultInfo() {
    }

    private ResultInfo(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private ResultInfo(int status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + status;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ResultInfo other = (ResultInfo) obj;
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        if (status != other.status) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        return true;
    }

    public static ResultInfo result(int status, String info) {
        ResultInfo res = new ResultInfo(status, info);
        return res;
    }

    public static ResultInfo result(int status, String info, Object data) {
        ResultInfo res = new ResultInfo(status, info, data);
        return res;
    }

    public static ResultInfo error() {
        ResultInfo res = new ResultInfo(RESULT_CODE_COMMONERR, "ERROR");
        return res;
    }

    public static ResultInfo error(String errorMessage) {
        logger.error(errorMessage);
        ResultInfo res = new ResultInfo(RESULT_CODE_COMMONERR, errorMessage);
        return res;
    }

    public static ResultInfo error(String errorMessage, Object data) {
        ResultInfo res = new ResultInfo(RESULT_CODE_COMMONERR, errorMessage, data);
        return res;
    }

    public static ResultInfo success() {
        ResultInfo res = new ResultInfo();
        return res;
    }

    public static ResultInfo success(String message) {
        logger.info(message);
        ResultInfo res = new ResultInfo(RESULT_CODE_SUCCESS, message);
        return res;
    }

    public static ResultInfo success(String message, Object data) {
        ResultInfo res = new ResultInfo(RESULT_CODE_SUCCESS, message, data);
        return res;
    }


}
