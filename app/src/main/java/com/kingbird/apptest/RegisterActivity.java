package com.kingbird.apptest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

/**
 * @ClassName: RegisterActivity
 * @Description: 注册界面
 * @Author: Pan
 * @CreateDate: 2020/1/17 17:05
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onProtocol(View view) {
        SpannableString spannableString = new SpannableString("注册即表示您已阅读，并同意《用户注册协议》");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
        spannableString.setSpan(foregroundColorSpan, 12, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        view.setText(spannableString);
        TextView textView = findViewById(R.id.textProtocol);
        textView.setText(spannableString);
    }
}
