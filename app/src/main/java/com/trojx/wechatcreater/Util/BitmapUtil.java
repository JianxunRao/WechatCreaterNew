package com.trojx.wechatcreater.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2015/12/8.
 */
public   class BitmapUtil {
    public BitmapUtil(){

    }
    /**
     *
     * @param filePath 源图地址
     * @param heightLmt 限高，超出此范围则缩放
     * @param widthLmt  限宽
     * @return
     */
    public  Bitmap getScaledBitmap(String filePath,int widthLmt,int heightLmt ){
        BitmapFactory.Options opts=new BitmapFactory.Options();
        opts.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(filePath,opts);
        int imageWidth=opts.outWidth;
        int imageHeight=opts.outHeight;
        int scale=1;
        int scaleX=imageWidth/widthLmt;
        int scaleY=imageHeight/heightLmt;
        if(scaleX>scaleY&&scaleX>1){
            scale=scaleX;
        }else if(scaleX<scaleY&&scaleY>1){
            scale=scaleY;
        }
        opts.inSampleSize=scale;
        opts.inJustDecodeBounds=false;
        Bitmap bm=BitmapFactory.decodeFile(filePath,opts);
        return  bm;
    }
}
