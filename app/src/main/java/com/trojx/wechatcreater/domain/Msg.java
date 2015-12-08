package com.trojx.wechatcreater.domain;

/**
 * Created by Administrator on 2015/12/7.
 */
public class Msg {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SEND=1;
    private String date;
    private String content;
    private int type;
    private boolean showDate;
    public Msg(String date,String content,int type,boolean showDate) {
        // TODO Auto-generated constructor stub
        this.date=date;
        this.content=content;
        this.type=type;
        this.showDate=showDate;
    }
    public String getContent() {
        return content;
    }
    public int getType() {
        return type;
    }
    public String getDate() {
        return date;
    }
    public boolean getShowDate(){
        return showDate;
    }
}
