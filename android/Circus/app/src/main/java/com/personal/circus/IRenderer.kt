package com.personal.circus;

import android.graphics.Rect

interface IRenderer{

    fun drawRect(r: Rect);
    fun drawRect(x:Int,y:Int,w:Int,h:Int);
    fun fillRect(r:Rect);
    fun fillRect(x:Int,y:Int,w:Int,h:Int);
    fun fillRect(v:Vector,r:Int)
    fun drawLine(x1:Int, y1:Int, x2:Int, y2:Int);
    fun drawText(x:Int,y:Int,t:String);
    fun calcTextRect(t:String):Rect;
    fun setColor(c:Int);
}