package com.personal.circus.Weather;
import java.io.*;
public class LivedoorAreaInfo implements Serializable{
    public String name=null;
    public String id;
    public String source;
    public Boolean check=true;
    public LivedoorAreaInfo(String n,String i,String src){
        name=n;
        id=i;
        source=src;
    }
    @Override
    public String toString(){
        return name;
    }
}
