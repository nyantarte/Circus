package com.personal.circus.Moneybook;

import org.json.JSONObject;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class MoneyBookTransaction implements Serializable {
    public long id;
    public long timeStamp;
    public String payMethod;
    public String usage;
    public int value;
    public String note;


    public MoneyBookTransaction(){}
    public MoneyBookTransaction(JSONObject o){
        try {
            id = o.getLong("id");
            timeStamp=o.getLong("timeStamp");
            payMethod=o.getString("payMethod");
            usage=o.getString("usage");
            value=o.getInt("value");
            note=o.getString("note");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public JSONObject toJSONObject(){
        JSONObject o=new JSONObject();
        try {
            o.put("id", id);
            o.put("timeStamp", timeStamp);
            o.put("payMethod",payMethod);
            o.put("usage",usage);
            o.put("value",value);
            o.put("note",note);
        }catch (Exception e){
            e.printStackTrace();
        }
        return o;
    }
    @Override
    public String toString(){

        return String.format("%s %s %s %d %s", SimpleDateFormat.getInstance().format(new  Date(this.timeStamp)), value<0?"支出":"収入",usage,Math.abs(value),note);
    }
}
