package com.kingbird.apptest.wecaht;

/**
 * @ClassName: WeiXin
 * @Description: java类作用描述
 * @Author: Pan
 * @CreateDate: 2020/1/17 17:04
 */
public class WeiXin {

    //1:登录 2.分享 3:微信支付
    private int type;
    //微信返回的错误码
    private int errCode;
    //登录成功才会有的code
    private String code;

    public WeiXin() {
    }

    public WeiXin(int type, int errCode, String code) {
        this.type = type;
        this.errCode = errCode;
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
