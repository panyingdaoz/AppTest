package com.kingbird.apptest.wecaht;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.socks.library.KLog;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ClassName: WxEntryActivity
 * @Description: java类作用描述
 * @Author: Pan
 * @CreateDate: 2020/1/17 17:09
 */
public class WxEntryActivity2 extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI wxApi;
    private ProgressDialog mProgressDialog;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        KLog.e("启动 WxEntryActivity2");

        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        wxApi = WXAPIFactory.createWXAPI(this, "wx45ccf8958a0a24c7", false);
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
        //登录回调
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) resp).code;
                //获取accesstoken
                getAccessToken(code);
                KLog.e("fantasychongwxlogin", code.toString() + "");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝授权
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    private void getAccessToken(String code) {
        createProgressDialog();
        //获取授权
        StringBuilder loginUrl = new StringBuilder();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append("wx45ccf8958a0a24c7")
                .append("&secret=")
                .append("e9c071f3326663856bc6cf02c2d6b657")
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        KLog.e("urlurl", loginUrl.toString());

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(loginUrl.toString())
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                KLog.e("fan12", "onFailure: ");
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo = response.body().string();
                KLog.e("fan12", "onResponse: " + responseInfo);
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getUserInfo(access, openId);
            }
        });
    }

    private void createProgressDialog() {
        mContext = this;
        mProgressDialog = new ProgressDialog(mContext);
        //转盘
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage("登录中，请稍后");
        mProgressDialog.show();
    }

    private void getUserInfo(String access, String openid) {
        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openid;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(getUserInfoUrl)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                KLog.e("fan12", "onFailure: ");
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo = response.body().string();
                KLog.e("fan123", "onResponse: " + responseInfo);
                SharedPreferences.Editor editor = getSharedPreferences("userInfo", MODE_PRIVATE).edit();
                editor.putString("responseInfo", responseInfo);
                editor.apply();
                finish();
                mProgressDialog.dismiss();
            }
        });
    }
}
