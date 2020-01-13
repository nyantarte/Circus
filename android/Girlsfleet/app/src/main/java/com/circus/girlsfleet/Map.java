package com.circus.girlsfleet;


import com.circus.girlsfleet.Size;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Map {
    private String m_name;
    public String getName(){
        return m_name;
    }
    public void setName(String n){
        m_name=n;
    }
    private Size m_size;
    public Size getSize(){
        return m_size;
    }
    public void setSize(Size s){
        m_size=s;
    }
    private int m_bossLevel,m_mobLevel;
    public int getBossLevel(){
        return m_bossLevel;
    }
    public void setBossLevel(int i){
        m_bossLevel=i;
    }
    public int getMobLevel(){
        return m_mobLevel;
    }
    public void setMobLevel(int i){
        m_mobLevel=i;
    }
    private String m_next;
    public String getNext(){
        return m_next;
    }
    public void setNext(String n){
        m_next=n;
    }
    private String m_boss1,m_boss2;
    public String getBoss1(){
        return m_boss1;
    }
    public void setBoss1(String s){
        m_boss1=s;
    }
    public String getBoss2(){
        return m_boss2;
    }
    public void setBoss2(String s){
        m_boss2=s;
    }
    private int m_getBattleNum;
    public int getBattleNum(){
        return m_getBattleNum;

    }
    public void setBattleNum(int i){
        m_getBattleNum=i;
    }

    private ArrayList<String> m_dropCharaList=new ArrayList<>();
    public List<String> getDropCharaList(){
        return m_dropCharaList;
    }
}
