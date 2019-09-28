package com.personal.circus;
import android.graphics.Rect

class ScreenMode{

    private var m_width:Int=0
    private var m_height:Int=0
    private var m_isVertical:Boolean=false

    private var m_screenW=0;
    private var m_screenH:Int=0;
    private var m_rect=Rect()

    companion object {
        @JvmStatic
        var UXGA_VERTICAL = ScreenMode(1200,1620,true);
        @JvmStatic
        var FHD_VERTICAL=ScreenMode(1080,1980,true);
        @JvmStatic
        var HD_PLUS_VERTICAL=ScreenMode(900,1600,true)
    }
    constructor(w:Int,h:Int,isVertical:Boolean){
        m_width=w;
        m_height=h;
        m_isVertical=isVertical
    }
    fun setScreenSize(sw:Int,sh:Int){
        m_screenW=sw;
        m_screenH=sh;

        println(String.format("%d,%d",m_width,m_screenW))

        val wO=m_screenW-m_width;
        m_rect.left=wO/2;
        val hO=m_screenH-m_height;
        m_rect.top=hO/2;

        m_rect.right=m_rect.left+m_width;
        m_rect.bottom=m_rect.top+m_height

    }

    fun getWidth():Int=m_width;
    fun getHeight():Int=m_height;
    fun isVertical():Boolean=m_isVertical;

    fun getRect():Rect=m_rect
    fun getScreenWidth()=m_screenW
    fun getScreenHeight()=m_screenH
}
/*enum class SCREEN_MODE(val m_width:Int,val m_height:Int,val m_isVertical:Boolean) {
    VGA_HORIZON(640,480,false),
    UXGA_VERTICAL(1200,1620),
    FHD_VERTICAL(1080,1980),
    FHD_PLUS_VERTICAL(1080,2265,true);

    fun getWidth():Int=m_width;
    fun getHeight():Int=m_height;
    fun isVertical():Boolean=m_isVertical;

}*/