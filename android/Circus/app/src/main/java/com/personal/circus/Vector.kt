package com.personal.circus

import android.graphics.Rect

class Vector :Cloneable{

    private var m_xyz=FloatArray(3)

    var xyz:FloatArray
    get(){
        return m_xyz
    }
    set(f:FloatArray){
        m_xyz=f.clone()
    }

    companion object {
        @JvmStatic
        val zero = Vector()

        fun add(r:Vector,v1:Vector,v2:Vector):Vector{
            for(i in 0..r.m_xyz.size-1){
                r.m_xyz[i]=v1.m_xyz[i]+v2.m_xyz[i]
            }
            return r
        }
        fun sub(r:Vector,v1:Vector,v2:Vector):Vector{
            for(i in 0..r.m_xyz.size-1){
                r.m_xyz[i]=v1.m_xyz[i]-v2.m_xyz[i]
            }
            return r
        }
        fun div(r:Vector,v1:Vector,s:Float):Vector{
            for(i in 0..r.m_xyz.size-1){
                r.m_xyz[i]=v1.m_xyz[i]/s
            }
            return r

        }
    }
    constructor(){
        xyz= floatArrayOf(0.0f,0.0f,0.0f)
    }
    constructor(f:FloatArray){
        xyz=f.clone()
    }
    constructor(r: Rect){
        xyz[0]=r.centerX().toFloat()
        xyz[1]=r.centerY().toFloat()
        xyz[2]=0.0f
    }

    fun getX():Float{
        return xyz[0]
    }
    fun setX(f:Float){
        xyz[0]=f
    }
    fun getY():Float{
        return xyz[1]
    }
    fun setY(f:Float){
        xyz[1]=f
    }

    fun getZ():Float{
        return xyz[2]
    }
    fun setZ(i:Float){
        xyz[2]=i
    }

    override public fun clone(): Any {
        return Vector(m_xyz)
    }
}