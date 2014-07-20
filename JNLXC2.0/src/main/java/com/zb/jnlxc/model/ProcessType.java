package com.zb.jnlxc.model;

/**
 * Created by apple on 2014/7/20.
 */
public enum ProcessType {
    oxidation((byte)0,"yh"),pentu((byte)1,"pt"),shixiao((byte)2,"sx");
    private byte num;
    private String shortName;
    private ProcessType(byte num, String shortName){
        this.num = num;
        this.shortName = shortName;
    }
    public Byte getStoreByte(){
        return num;
    }
    public String getShortName(){
        return shortName;
    }

    public static ProcessType getByNum(byte num){
        switch (num){
            case 0:return oxidation;
            case 1:return pentu;
            case 2:return shixiao;
            default:return shixiao;
        }
    }
}