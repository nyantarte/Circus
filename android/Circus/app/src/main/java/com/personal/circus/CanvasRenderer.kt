package com.personal.circus;

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class CanvasRenderer:IRenderer {

    private var m_canvas:Canvas?=null;
    private var m_paint: Paint=Paint();
    constructor(){
        m_paint.textSize=Config.FONT_SIZE;
    }
    override fun drawRect(r: Rect){
        drawRect(r.left,r.top,r.width(),r.height());
    }
    override fun drawRect(x:Int,y:Int,w:Int,h:Int){
        m_paint.setStyle(Paint.Style.STROKE);
        m_canvas?.drawRect(x.toFloat(),y.toFloat(),(x+w).toFloat(),(y+h).toFloat(),m_paint);
    }
    override fun fillRect(r: Rect){
        fillRect(r.left,r.top,r.width(),r.height());
    }
    override fun fillRect(x:Int,y:Int,w:Int,h:Int){
        m_paint.setStyle(Paint.Style.FILL);
        m_canvas?.drawRect(x.toFloat(),y.toFloat(),(x+w).toFloat(),(y+h).toFloat(),m_paint);
    }
    override fun fillRect(v:Vector,r:Int){
        m_paint.setStyle(Paint.Style.FILL);
        m_canvas?.drawRect(v.getX()-r,v.getY()-r,v.getX()+r,v.getY()+r,m_paint);

    }

    override fun drawLine(x1:Int, y1:Int, x2:Int, y2:Int){
        m_canvas?.drawLine(x1.toFloat(),y1.toFloat(),x2.toFloat(),y2.toFloat(),m_paint);
    }
    override fun drawText(x:Int,y:Int,t:String){
        m_paint.setStyle(Paint.Style.FILL);
        m_canvas?.drawText(t,x.toFloat(),y.toFloat(),m_paint);
    }
    override fun calcTextRect(t:String): Rect{
        val w = m_paint.measureText(t)
        return Rect(0, 0, w.toInt(), m_paint.textSize.toInt())
    }
    override fun setColor(c:Int){
        m_paint.setColor(c);
    }

    fun bindCanvas(c:Canvas){
        m_canvas=c;
    }

}