package cn.pompip.browser.common.entity;

import java.io.Serializable;

public class Result implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8329204949391092860L;
    private int code;
    private String msg = "";
    private Object data;

    public static Result success(Object data) {
        return new Result(0, "success", data);
    }

    public static Result success() {
        return new Result(0, "success", "");
    }

    public Result() {

    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
