package com.trojx.wechatcreater.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.trojx.wechatcreater.R;

public class LoginActivity extends AppCompatActivity {
    EditText et_account;
    EditText et_password;
    Button bt_login;
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        AVUser currentUser=AVUser.getCurrentUser();
        et_account= (EditText) findViewById(R.id.et_account);
        et_password= (EditText) findViewById(R.id.et_password);
        bt_login= (Button) findViewById(R.id.bt_login);
        tv_register= (TextView) findViewById(R.id.tv_register);
        if(currentUser!=null){
            et_account.setText(currentUser.getUsername());
        }
    }
    public void login (View v){
        String account=et_account.getText().toString();
        String password=et_password.getText().toString();
        if (password.length()==0){
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
        }else {
            AVUser.logInInBackground(account, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (avUser != null) {
                        Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败，请检查网络设置！", Toast.LENGTH_SHORT).show();
                        et_password.setText("");
                    }
                }
            });
        }
    }
    public void goToRegister(View v){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                et_account.setText(data.getStringExtra("account"));
            }
        }

    }


}
