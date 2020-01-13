package com.circus.girlsfleet;

import android.graphics.Rect;

public enum ScreenMode {
    UXGA_VERTICAL (1200,1620,true),
    FHD_VERTICAL(1080,1980,true),
    HD_PLUS_VERTICAL(900,1600,true);
    private boolean m_isScreenMode;
    private Rect m_rect;

    private ScreenMode(int w,int h,boolean isVertical){
        m_isScreenMode=isVertical;
        m_rect=new Rect(0,0,w,h);
    }
    public void setLeftTop(int l,int t){
        m_rect=new Rect(l,t,l+m_rect.width(),t+m_rect.height());
    }

    public Rect getRect(){
        return m_rect;
    }
}
