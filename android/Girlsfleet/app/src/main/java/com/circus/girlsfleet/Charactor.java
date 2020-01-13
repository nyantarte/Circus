package com.circus.girlsfleet;

import java.security.PrivilegedAction;

public class Charactor {
    public enum RAREITY{
        SSR,
        SR,
        R,
        C,
    }
    private RAREITY m_rareity;
    public RAREITY getRareity(){
        return m_rareity;
    }
    public void setRareity(String s){
        m_rareity=RAREITY.valueOf(s);
    }
    public void setRareity(RAREITY r){
        m_rareity=r;
    }
    public enum TYPE{
        BATTLESHIP,
        AIR_CARRIER,
        CRUISER,
        DESTROYER,
        SUBMARINE
    }
    private TYPE m_type;
    public TYPE getType(){
        return m_type;
    }
    public void setType(String t){
        m_type=TYPE.valueOf(t);
    }
    public void setType(TYPE t){
        m_type=t;
    }
    public enum PARAMETER{
        S,
        A,
        B,
        C,
        D,
        E
    }
    private PARAMETER m_life;
    public PARAMETER getLife(){
        return m_life;
    }
    public void setLife(PARAMETER p){
        m_life=p;
    }
    public void setLife(String p){
        m_life=PARAMETER.valueOf(p);
    }
    private PARAMETER m_atk;
    public PARAMETER getAtk(){
        return m_atk;
    }
    public void setAtk(String a){
        m_atk=PARAMETER.valueOf(a);
    }
    public void setAtk(PARAMETER p){
        m_atk=p;
    }
    private  PARAMETER m_torpedo;
    public PARAMETER getTorpedo(){
        return m_torpedo;
    }
    public void setTorpedo(String t){
        m_torpedo=PARAMETER.valueOf(t);
    }
    public void setTorpedo(PARAMETER p){
        m_torpedo=p;
    }
    private PARAMETER m_airAtk;
    public PARAMETER getAirAtk(){
        return m_airAtk;

    }
    public void setAirAtk(String s){
        m_airAtk=PARAMETER.valueOf(s);
    }
    public void setAirAtk(PARAMETER p){
        m_airAtk=p;
    }
    private PARAMETER m_airDef;
    public PARAMETER getAirDef(){
        return m_airDef;
    }
    public void setAirDef(String s){
        m_airDef=PARAMETER.valueOf(s);
    }
    public void setAirDef(PARAMETER p){
        m_airDef=p;
    }

    private PARAMETER m_avoid;
    public PARAMETER getAvoid(){
        return m_avoid;
    }
    public void setAvoid(String s){
        m_avoid=PARAMETER.valueOf(s);
    }
    public void setAvoid(PARAMETER p){
        m_avoid=p;
    }
    private String m_name;
    public String getName(){
        return m_name;
    }
    public void setName(String n){
        m_name=n;
    }

    private int m_level;
    public int getLevel(){
        return m_level;
    }
    public void setLevel(int i){
        m_level=i;
    }
    @Override
    public String toString(){
        return getName();
    }
}
