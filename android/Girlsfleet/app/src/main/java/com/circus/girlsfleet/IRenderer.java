package com.circus.girlsfleet;

import android.graphics.Color;
import android.graphics.Rect;

public interface IRenderer {

    void drawRect(Rect r);
    void drawRect(int l,int t,int w,int h);
    void fillRect(Rect r);
    void fillRect(int l,int t,int w,int h);
    void fillRect(Vector center,int radius);
    void drawLine(int x1,int y1,int x2,int y2);
    void drawText(int l,int t,String txt);
    void setColor(int c);
}
