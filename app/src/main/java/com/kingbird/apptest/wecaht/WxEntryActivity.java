package com.kingbird.apptest.wecaht;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kingbird.apptest.utils.Constant;
import com.socks.library.KLog;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * @ClassName: WxEntryActivity
 * @Description: java类作用描述
 * @Author: Pan
 * @CreateDate: 2020/1/17 17:09
 */
public class WxEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI wxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wxApi = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APPID, true);
        wxApi.registerApp(Constant.WECHAT_APPID);
        wxApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        wxApi.handleIntent(getIntent(), this);
        KLog.e("ansen", "WxEntryActivity onNewIntent");
    }

    @Override
    public void onReq(BaseReq arg0) {
        KLog.e("ansen", "WxEntryActivity onReq:" + arg0);
    }

    @Override
    public void onResp(BaseResp resp) {
        //分享
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            KLog.e("ansen", "微信分享操作.....");
            WeiXin weiXin = new WeiXin(2, resp.errCode, "");
            EventBus.getDefault().post(weiXin);
            //登陆
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            KLog.e("ansen", "微信登录操作.....");
            SendAuth.Resp authResp = (SendAuth.Resp) resp;
            WeiXin weiXin = new WeiXin(1, resp.errCode, authResp.code);
            EventBus.getDefault().post(weiXin);
        }
        finish();
    }
}
