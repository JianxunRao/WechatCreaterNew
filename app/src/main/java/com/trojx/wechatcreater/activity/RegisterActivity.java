package com.trojx.wechatcreater.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.trojx.wechatcreater.R;

public class RegisterActivity extends AppCompatActivity {
    EditText et_account;
    EditText et_password;
    EditText et_password_comfirm;
    Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_account= (EditText) findViewById(R.id.et_rg_account);
        et_password= (EditText) findViewById(R.id.et_rg_pass);
        et_password_comfirm= (EditText) findViewById(R.id.et_rg_pass_comfirm);
        bt_register= (Button) findViewById(R.id.bt_register);

    }
    public void register(View v){
        if(et_account.getText().length()<6||et_password.getText().length()<6||!(et_password.getText().toString().equals(et_password_comfirm.getText().toString()))){
             AlertDialog.Builder builder=new AlertDialog.Builder(this);//直接new、get不了，用AlertDialog自带的Builder类来创建对话框
//            builder.setIcon(android.R.drawable.ic_menu_agenda);
            builder.setTitle("注意");
            builder.setMessage("  请正确填写注册信息！");
            builder.setCancelable(false);
            builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            });
            builder.show();
        }
         final String account=et_account.getText().toString();
        String password=et_password.getText().toString();
        AVUser user=new AVUser();
        user.setUsername(account);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    Intent intent=new Intent();
                    intent.putExtra("account",account);
                    setResult(RESULT_OK, intent);
                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"注册失败，请检查网络设置！",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
