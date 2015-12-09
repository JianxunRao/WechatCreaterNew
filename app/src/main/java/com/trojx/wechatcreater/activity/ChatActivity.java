package com.trojx.wechatcreater.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trojx.wechatcreater.R;
import com.trojx.wechatcreater.Util.ScreenShot;
import com.trojx.wechatcreater.adapter.MsgAdapter;
import com.trojx.wechatcreater.domain.Msg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ImageButton btBack;
    private ImageButton ibPerson;
    private TextView tvName;
    private Button btSend;
    private EditText etInputText;
    private ListView lvMsg;
    private List<Msg> msgList=new ArrayList<Msg>();
    private MsgAdapter adapter;
    private int status=Msg.TYPE_SEND;
    private SensorManager sensorManager;
    private String senderName;
    private String receiverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chat_activity);
        initMsgs();
        btBack = (ImageButton) findViewById(R.id.bt_back);
        ibPerson = (ImageButton) findViewById(R.id.ib_person);
        tvName = (TextView) findViewById(R.id.tv_name);
        btSend = (Button) findViewById(R.id.bt_send);
        etInputText = (EditText) findViewById(R.id.et_input_text);
        lvMsg = (ListView) findViewById(R.id.lv_msg);
        adapter = new MsgAdapter(ChatActivity.this, R.layout.msg_item, msgList);
        Toast.makeText(this,"摇一摇手机截取屏幕~",Toast.LENGTH_LONG).show();
        Toast.makeText(this,"点击右上角转换身份~",Toast.LENGTH_LONG).show();
        Intent intent=getIntent();
         senderName=intent.getStringExtra("sendername");
         receiverName=intent.getStringExtra("receivername");
        tvName.setText(senderName);
        lvMsg.setAdapter(adapter);
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                Date d = new Date();
                Boolean showDate = true;
                String date = sdf.format(d);
                String content = etInputText.getText().toString();
                if (!"".equals(content)) {
                    Date lastDate = null;
                    if (msgList.size() > 0) {
                        Msg lastMsg = msgList.get(msgList.size() - 1);
                        try {
                            lastDate = sdf.parse(lastMsg.getDate());
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if ((d.getTime() - lastDate.getTime()) > 120000) {
                            showDate = true;
                        } else {
                            showDate = false;
                        }
                        Msg msg = new Msg(date, content, status, showDate);
                        msgList.add(msg);
                        adapter.notifyDataSetChanged();
                        lvMsg.setSelection(msgList.size());
                        etInputText.setText("");
                    } else {
                        Msg msg = new Msg(date, content, status, true);
                        msgList.add(msg);
                        adapter.notifyDataSetChanged();
                        lvMsg.setSelection(msgList.size());
                        etInputText.setText("");
                    }
                }
            }
        });
        ibPerson.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ObjectAnimator oa=ObjectAnimator.ofFloat(ibPerson, "rotationY", 0,180);
                oa.setDuration(500);
                oa.start();
                if(status==Msg.TYPE_SEND){
                    status=Msg.TYPE_RECEIVED;
                    Toast.makeText(ChatActivity.this, "编辑将要收到的消息", Toast.LENGTH_SHORT).show();
                }else if (status==Msg.TYPE_RECEIVED) {
                    status=Msg.TYPE_SEND;
                    Toast.makeText(ChatActivity.this, "编辑要发送的消息", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void initMsgs(){
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        String date=sdf.format(new Date());
        Msg msg1=new Msg(date, "现在开始自由编辑对话吧~", Msg.TYPE_RECEIVED,true);
        //msgList.add(msg1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sensorManager!=null){
            sensorManager.unregisterListener(listener);
        }
    }
    private SensorEventListener listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float xValue=Math.abs(event.values[0]);
            float yValue=Math.abs(event.values[1]);
            float zValue=Math.abs(event.values[2]);
            if (xValue>22||yValue>22|zValue>22){
//                ScreenShot.
                Bitmap bitmap= ScreenShot.takeScreenShot(ChatActivity.this);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
                Date date=new Date();
                String dateString=simpleDateFormat.format(date);
                String filePath="sdcard/"+senderName+"与"+receiverName+"的对话"+dateString+".png";
                ScreenShot.savePic(bitmap,filePath);
                Toast.makeText(ChatActivity.this,"已保存到"+filePath,Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
