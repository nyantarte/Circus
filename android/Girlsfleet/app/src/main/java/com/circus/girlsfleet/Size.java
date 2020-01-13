package com.circus.girlsfleet;

public class Size extends Pair<Integer,Integer>{

    public Size(int w,int h){
        super(w,h);
    }

    public int getWidth(){
        return first;
    }
    public int getHeight(){
        return second;
    }
}
