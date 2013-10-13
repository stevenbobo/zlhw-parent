package com.zb.pcare.common;

/**
 * Created with IntelliJ IDEA.
 * User: 我是头熊没钱
 * Date: 13-10-4
 * Time: 下午10:03
 * To change this template use File | Settings | File Templates.
 */
public enum ReplyType {
    DATE("date"),YESNO("yesno"),TEXT("text"),NUMBER("number");
    public String type;
    ReplyType(String type){
         this.type = type;
    }
}
