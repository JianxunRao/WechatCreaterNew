package com.trojx.wechatcreater.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.trojx.wechatcreater.R;

public class SplashActivity extends AppCompatActivity {

   public Handler handler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
           startActivity(intent);
           super.handleMessage(msg);
           finish();
       }
   };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AVOSCloud.initialize(this, "qGeVG0HXPT5e7HchnMsKdFte-gzGzoHsz", "dX0qIpEpN3x82G5T4TJMkven");
        AVInstallation.getCurrentInstallation().saveInBackground();
        PushService.setDefaultPushCallback(this, LoginActivity.class);//默认打开的Activity
        handler.sendEmptyMessageDelayed(1, 1000);
    }
}
