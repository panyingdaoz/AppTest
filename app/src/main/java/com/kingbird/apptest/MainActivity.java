package com.kingbird.apptest;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.ansen.http.net.HTTPCaller;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.kingbird.apptest.utils.CodeUtils;
import com.kingbird.apptest.utils.Constant;
import com.kingbird.apptest.wecaht.WeiXin;
import com.socks.library.KLog;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author 86185
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView passWord, textCode;
    private EditText password;
    private LinearLayout linearLayout, linearLayoutCode;
    private View viewCode, viewPassw;
    private Bitmap bitmap;
    private String code;
    private IWXAPI wxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_login);

        EventBus.getDefault().register(this);
        wxApi = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APPID, true);
        wxApi.registerApp(Constant.WECHAT_APPID);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onClickCode(View view) {
        passWord = findViewById(R.id.PassWord);
        passWord.setTextColor(ContextCompat.getColor(this, R.color.textColor));
        textCode = findViewById(R.id.Code);
        textCode.setTextColor(ContextCompat.getColor(this, R.color.passWotd));
        viewCode = findViewById(R.id.CodeView);
        viewPassw = findViewById(R.id.PassWordView);
        viewPassw.setVisibility(View.GONE);
        viewCode.setVisibility(View.VISIBLE);
        password = findViewById(R.id.PassWordEd);
        password.setVisibility(View.GONE);
        linearLayout = findViewById(R.id.CodeLoginLayout);
        linearLayoutCode = findViewById(R.id.CodeLayout);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayoutCode.setVisibility(View.VISIBLE);
        final ImageView codeView = findViewById(R.id.imageViewCode);
        bitmap = CodeUtils.getInstance().createBitmap();
        code = CodeUtils.getInstance().getCode();
        codeView.setImageBitmap(bitmap);
        codeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = CodeUtils.getInstance().createBitmap();
                code = CodeUtils.getInstance().getCode();
                codeView.setImageBitmap(bitmap);
                KLog.e("点击验证码图片");
            }
        });
    }

    public void onClickPassWord(View view) {
        textCode.setTextColor(ContextCompat.getColor(this, R.color.textColor));
        passWord.setTextColor(ContextCompat.getColor(this, R.color.passWotd));
        viewPassw.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        viewCode.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        linearLayoutCode.setVisibility(View.GONE);
    }

    public void onClickWechat(View view) {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = String.valueOf(System.currentTimeMillis());
        wxApi.sendReq(req);
    }

    /**
     * 这里用到的了EventBus框架
     */
    @Subscribe
    public void onEventMainThread(WeiXin weiXin) {
        KLog.e("ansen", "收到eventbus请求 type:" + weiXin.getType());
        //1、登录 2、分享 3、微信支付
        if (weiXin.getType() == 1) {
            KLog.e("ansen", "微信登录成功.....");
//            getAccessToken(weiXin.getCode());
        } else if (weiXin.getType() == 2) {
            switch (weiXin.getErrCode()) {
                case BaseResp.ErrCode.ERR_OK:
                    KLog.e("ansen", "微信分享成功.....");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //分享取消
                    KLog.e("ansen", "微信分享取消.....");
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //分享被拒绝
                    KLog.e("ansen", "微信分享被拒绝.....");
                    break;
                default:
            }
        } else if (weiXin.getType() == 3) {
            if (weiXin.getErrCode() == BaseResp.ErrCode.ERR_OK) {
                //成功
                KLog.e("ansen", "微信支付成功.....");
            }
        }
    }
//
//    public void getAccessToken(String code){
//        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
//                "appid="+Constant.WECHAT_APPID+"&secret="+Constant.WECHAT_SECRET+
//                "&code="+code+"&grant_type=authorization_code";
//        HTTPCaller.getInstance().get(WeiXinToken.class, url, null, new RequestDataCallback<WeiXinToken>() {
//            @Override
//            public void dataCallback(WeiXinToken obj) {
//                if(obj.getErrcode()==0){//请求成功
//                    getWeiXinUserInfo(obj);
//                }else{//请求失败
//                    showToast(obj.getErrmsg());
//                }
//            }
//        });
//    }
//
//    public void getWeiXinUserInfo(WeiXinToken weiXinToken){
//        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+
//                weiXinToken.getAccess_token()+"&openid="+weiXinToken.getOpenid();
//        HTTPCaller.getInstance().get(WeiXinInfo.class, url, null, new RequestDataCallback<WeiXinInfo>() {
//            @Override
//            public void dataCallback(WeiXinInfo obj) {
//                tvNickname.setText("昵称:"+obj.getNickname());
//                tvAge.setText("年龄:"+obj.getAge());
//                KLog.e("ansen","头像地址:"+obj.getHeadimgurl());
//            }
//        });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
