package com.liuritian.aigou.util;
//工具类
public class AjaxResult {
    private boolean success = true;//是否成功 默认成功
    private String msg = "操作成功";//给个提醒
    private Object object;//返回后台封装的数据

    public static AjaxResult me() {

        return new AjaxResult();
    }
    @Override
    public String toString() {
        return "AjaxResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", object=" + object +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public AjaxResult setObject(Object object) {
        this.object = object;
        return this;
    }
}
