package com.trojx.wechatcreater.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trojx.wechatcreater.R;
import com.trojx.wechatcreater.domain.Msg;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2015/12/7.
 */
public class MsgAdapter extends ArrayAdapter<Msg> {

   private  Bitmap bitmapThumbSenderSmall;
   private  Bitmap bitmapThumbReceiverSmall;
    private int resourceId;
    public MsgAdapter(Context context, int resource, List<Msg> objects) {
        super(context, resource, objects);
        resourceId=resource;
        try{
            File file=new File("sdcard/bitmapThumbSenderSmall.png");
            if (file.exists()){
                bitmapThumbSenderSmall= BitmapFactory.decodeFile(file.getPath());
            }
        }catch (Exception e){
        }
        try{
            File file=new File("sdcard/bitmapThumbReceiverSmall.png");
            if (file.exists()){
                bitmapThumbReceiverSmall= BitmapFactory.decodeFile(file.getPath());
            }

        }catch (Exception e){

        }


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Msg msg=(Msg) getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder=new ViewHolder();
            viewHolder.tv_sendtime=(TextView) view.findViewById(R.id.tv_sendtime);
            viewHolder.rl_left=(LinearLayout) view.findViewById(R.id.rl_left);
            viewHolder.rl_right=(RelativeLayout) view.findViewById(R.id.rl_right);
            viewHolder.iv_left=(ImageView) view.findViewById(R.id.iv_left);
            viewHolder.iv_right=(ImageView) view.findViewById(R.id.iv_right);
            viewHolder.tv_msg_left=(TextView) view.findViewById(R.id.tv_msg_left);
            viewHolder.tv_msg_right=(TextView) view.findViewById(R.id.tv_msg_right);
            viewHolder.iv_left= (ImageView) view.findViewById(R.id.iv_left);
            viewHolder.iv_right= (ImageView) view.findViewById(R.id.iv_right);
            if(bitmapThumbSenderSmall!=null){
            viewHolder.iv_left.setImageBitmap(bitmapThumbSenderSmall);
            }
            if(bitmapThumbReceiverSmall!=null){
            viewHolder.iv_right.setImageBitmap(bitmapThumbReceiverSmall);
            }
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }
        if(msg.getType()==Msg.TYPE_RECEIVED){
            viewHolder.tv_sendtime.setVisibility(View.GONE);
            if (msg.getShowDate()) {
                viewHolder.tv_sendtime.setText(msg.getDate());
                viewHolder.tv_sendtime.setVisibility(View.VISIBLE);
            }
            viewHolder.rl_left.setVisibility(View.VISIBLE);
            viewHolder.rl_right.setVisibility(View.GONE);
            viewHolder.rl_left.setPadding(0, 0, 200, 0);
            viewHolder.rl_right.setPadding(0, 0, 0, 0);
            viewHolder.tv_msg_left.setText(msg.getContent());

        }else if (msg.getType()==Msg.TYPE_SEND) {
            viewHolder.tv_sendtime.setVisibility(View.GONE);
            if (msg.getShowDate()) {
                viewHolder.tv_sendtime.setText(msg.getDate());
                viewHolder.tv_sendtime.setVisibility(View.VISIBLE);
            }
            viewHolder.rl_right.setPadding(200, 0, 0, 0);
            viewHolder.rl_left.setPadding(0, 0, 0, 0);
            viewHolder.rl_left.setVisibility(View.GONE);
            viewHolder.rl_right.setVisibility(View.VISIBLE);
            viewHolder.tv_msg_right.setText(msg.getContent());
        }
        return view;
    }
    class ViewHolder{
        TextView tv_sendtime;
        LinearLayout rl_left;
        RelativeLayout rl_right;
        ImageView iv_left;
        ImageView iv_right;
        TextView tv_msg_left;
        TextView tv_msg_right;
    }
}
