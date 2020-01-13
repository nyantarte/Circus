package com.circus.girlsfleet;

import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;

public class MapLoader {
    public static final Map load(String f, AssetManager a){
        try{
            JSONObject o;
            o = new JSONObject(IOUtils.loadPlainText(new File(GameConfig.MAP_DIR,f),a));
            Map m=new Map();
            m.setName(o.optString("name"));
            int w=o.optInt("width");
            int h=o.optInt("height");
            m.setSize(new Size(w,h));
            m.setBossLevel(o.optInt("bossLevel"));
            m.setMobLevel(o.optInt("mobLevel"));
            m.setNext(o.optString("next"));
            m.setBoss1(o.optString("boss1"));
            m.setBoss2(o.optString("boss2"));
            m.setBattleNum(o.optInt("battleNum"));
            JSONArray ar=o.getJSONArray("dropCharaList");
            for(int i=0;i < ar.length();++i){
                m.getDropCharaList().add(ar.getString(i));
            }
            return m;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static final void save(Map m){
        try{
            JSONObject o=new JSONObject();
            o.put("name",m.getName());
            o.put("width",m.getSize().getWidth());
            o.put("height",m.getSize().getHeight());
            o.put("bossLevel",m.getBossLevel());
            o.put("mobLevel",m.getMobLevel());
            o.put("next",m.getNext());
            o.put("battleNum",m.getBattleNum());
            o.put("boss1",m.getBoss1());
            o.put("boss2",m.getBoss2());
            JSONArray a=new JSONArray();
            for(String n:m.getDropCharaList()){
                a.put(n);
            }
            o.put("dropCharaList",a);
            BufferedWriter bw =IOUtils.createFile(IOUtils.createPath(GameConfig.MAP_DIR,m.getName()+".json"));
            bw.write(o.toString());
            bw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
