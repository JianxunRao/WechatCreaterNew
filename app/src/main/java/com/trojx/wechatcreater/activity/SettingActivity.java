package com.trojx.wechatcreater.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.trojx.wechatcreater.R;
import com.trojx.wechatcreater.Util.BitmapUtil;
import com.trojx.wechatcreater.Util.Uri2Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private CircleImageView civ_sender;
    private CircleImageView civ_receiver;
    private EditText et_sender_name;
    private EditText et_receiver_name;
    private Button bt_set_sender;
    private Button bt_set_receiver;
    private Button bt_start;
    private  Bitmap bitmapThumbSender;
    private  Bitmap bitmapThumbReceiver;
    private static final int REQUEST_SENDER_IMAGE=1;
    private static final int REQUEST_RECEIVER_IMAGE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        civ_sender= (CircleImageView) findViewById(R.id.civ_sender_logo);
        civ_receiver= (CircleImageView) findViewById(R.id.civ_receiver_logo);
        et_sender_name= (EditText) findViewById(R.id.et_sender_name);
        et_receiver_name= (EditText) findViewById(R.id.et_receiverr_name);
        bt_set_sender= (Button) findViewById(R.id.bt_set_sender);
        bt_set_receiver= (Button) findViewById(R.id.bt_set_receiver);

    }
    public void setSender(View v){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_SENDER_IMAGE);
    }
    public void setReceiver(View v){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_RECEIVER_IMAGE);
    }
    public  void startChat(View v){
        if(et_sender_name.getText().toString().isEmpty()){
            final AlertDialog.Builder builder=new AlertDialog.Builder(this);//直接new、get不了，用AlertDialog自带的Builder类来创建对话框
//            builder.setIcon(android.R.drawable.ic_menu_agenda);
            builder.setTitle("注意");
            builder.setMessage("  发送者姓名不能为空噢！");
            builder.setCancelable(false);
            builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            });
            builder.show();
        }else {

            Intent intent=new Intent(this,ChatActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BitmapUtil bitmapUtil=new BitmapUtil();
        if (requestCode==REQUEST_SENDER_IMAGE){
            if (resultCode==RESULT_OK){
//                List<String> path=data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                Bitmap bitmap=bitmapUtil.getScaledBitmap(Uri2Path.getRealFilePath(this, data.getData()),200,200);
                 bitmapThumbSender=ThumbnailUtils.extractThumbnail(bitmap, 200,200);
                civ_sender.setImageBitmap(bitmapThumbSender);
                Bitmap bitmapThumbSenderSmall=ThumbnailUtils.extractThumbnail(bitmap, 100, 100);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(new File("sdcard/bitmapThumbSenderSmall.png"));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                bitmapThumbSenderSmall.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
        }
        if (requestCode==REQUEST_RECEIVER_IMAGE){
            if(resultCode==RESULT_OK){
//                List<String> path=data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                Bitmap bitmap= bitmapUtil.getScaledBitmap(Uri2Path.getRealFilePath(this, data.getData()),200,200);
                 bitmapThumbReceiver=ThumbnailUtils.extractThumbnail(bitmap, 200, 200);
                civ_receiver.setImageBitmap(bitmapThumbReceiver);
                Bitmap bitmapThumbReceiverSmall=ThumbnailUtils.extractThumbnail(bitmap, 100, 100);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(new File("sdcard/bitmapThumbReceiverSmall.png"));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                bitmapThumbReceiverSmall.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
        }
    }




}
