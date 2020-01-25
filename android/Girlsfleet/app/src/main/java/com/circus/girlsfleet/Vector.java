package com.circus.girlsfleet;

import android.graphics.Rect;

public class Vector {
    public float[] xyz=new float[3];

    public Vector(float x,float y){
        setX(x);
        setY(y);
        setZ(0.0f);
    }
    public Vector(float x,float y,float z){
        setX(x);
        setY(y);
        setZ(z);
    }

    public Vector(Rect r){
        setX(r.centerX());
        setY(r.centerY());
        setZ(0.0f);
    }
    public float getX(){
        return xyz[0];
    }
    public void setX(float f){
        xyz[0]=f;
    }
    public float getY(){
        return xyz[1];
    }
    public void setY(float f){
        xyz[1]=f;
    }
    public float getZ(){
        return xyz[2];
    }
    public void setZ(float f){
        xyz[2]=f;
    }

    public String toString(){
        return String.format("Vector{x:%f,y:%f,z:%f}",getX(),getY(),getZ());
    }
    public static Vector add(Vector v1,Vector v2){
        return new Vector(v1.getX()+v2.getX(),v1.getY()+v2.getY(),v1.getZ()+v2.getZ());
    }
    public static Vector sub(Vector v1,Vector v2){
        return new Vector(v1.getX()-v2.getX(),v1.getY()-v2.getY(),v1.getZ()-v2.getZ());
    }
    public static Vector div(Vector v,float s){
        return new Vector(v.getX()/s,v.getY()/s,v.getZ()/s);
    }
}
