package com.kingbird.apptest;

import android.graphics.Bitmap;
import android.os.Bundle;

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
import com.socks.library.KLog;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 86185
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Bitmap bitmap;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_login);
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
        TextView passWord = findViewById(R.id.PassWord);
        passWord.setTextColor(ContextCompat.getColor(this, R.color.textColor));
        TextView textCode = findViewById(R.id.Code);
        textCode.setTextColor(ContextCompat.getColor(this, R.color.passWotd));
        View viewCode = findViewById(R.id.CodeView);
        View viewPassw = findViewById(R.id.PassWordView);
        viewPassw.setVisibility(View.GONE);
        viewCode.setVisibility(View.VISIBLE);
        EditText password = findViewById(R.id.PassWordEd);
        password.setVisibility(View.GONE);
        LinearLayout linearLayout = findViewById(R.id.CodeLoginLayout);
        linearLayout.setVisibility(View.VISIBLE);
        LinearLayout linearLayoutCode = findViewById(R.id.CodeLayout);
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
}
