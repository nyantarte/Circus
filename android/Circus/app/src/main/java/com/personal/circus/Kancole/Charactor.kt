package com.personal.circus.Kancole

import android.graphics.Rect
import org.json.JSONObject
import java.util.*
import com.personal.circus.*;
import com.personal.circus.Vector
import java.io.Serializable

public class Charactor :Cloneable,Serializable{
    public class ParamRank {
        enum class RANK(val rankName: String,val sig:Int, val value: Int) {

            S("S", 0,16),
            A("A", 1,8),
            B("B", 2,4),
            C("C", 3,2),
            D("D", 4,1),
            E("E", 5,0)


        }

        private var m_lifeRank =ParamRank.RANK.E
        var lifeRank: RANK
            get() {
                return m_lifeRank
            }
            set(v: RANK) {
                m_lifeRank = v
            }

        private var m_atkRank = RANK.E
        var atkRank: RANK
            get() {
                return m_atkRank;
            }
            set(v: RANK) {
                m_atkRank = v;
            }

        private var m_torpedoRank = RANK.E
        var torpedoRank: RANK
            get() {
                return m_torpedoRank
            }
            set(v: RANK) {
                m_torpedoRank = v
            }

        private var m_avoidRank = RANK.E
        var avoidRank: RANK
            get() {
                return m_avoidRank
            }
            set(v: RANK) {
                m_avoidRank = v
            }
        private var m_airDefRank = RANK.E

        var airDefRank:RANK
            get() {
                return m_airDefRank
            }
            set(v: RANK) {
                m_airDefRank = v
            }

        private var m_airAtkRank = RANK.E
        var airAtkRank: RANK
            get() {
                return m_airAtkRank
            }
            set(v: RANK) {
                m_airAtkRank = v
            }
        private var m_rangeRank =RANK.E
        var rangeRank: RANK
            get(){
                return m_rangeRank
            }
            set(v: RANK) {
                m_rangeRank = v
            }



        fun toJSON():JSONObject{
            val o=JSONObject()
            o.put("airAtkRank",this.m_airAtkRank)
            o.put("lifeRank",this.m_lifeRank)
            o.put("atkRank",this.m_atkRank)
            o.put("torpedoRank",this.m_torpedoRank)
            o.put("avoidRank",this.m_avoidRank)
            o.put("airDefRank",this.m_airDefRank)
            o.put("rangeRank",this.m_rangeRank)

            return o
        }

    }
    private var m_bustupImageStr:String?=null
    var bustupImageStr:String?
        get(){
            return m_bustupImageStr
        }
        set(v:String?){
            m_bustupImageStr=v
        }

    private var m_level:Int=0
    var Level:Int
    get(){
        return m_level
    }
    set(v:Int){
        m_level=v
        setupParams()
    }
    private var m_paramRank=ParamRank()

    val paramRank:ParamRank
    get(){
        return m_paramRank
    }

    private var m_name:String="";
    public var name:String
    get(){
        return m_name
    }
    set(v:String){
        m_name=v;
    }

    private var m_id:Long=0
    var id:Long
    get(){
        return m_id
    }
    set(i:Long){
        m_id=i
    }
    constructor(){}
    override fun toString():String{
        return m_name
    }



    private var m_Life=0
    var Life:Int
    get(){
        return m_Life
    }
    set(v:Int){
        m_Life=v
    }
    private var m_maxLife=0
    var MaxLife:Int
    get(){
        return m_maxLife
    }
    set(v:Int){
        m_maxLife=v
    }

    private var m_Atk=0
    var Atk:Int
    get(){
        return m_Atk
    }
    set(v :Int){
        m_Atk=v
    }

    private var m_expNeed:Int=0
    val ExpNeed:Int
    get(){
        return m_expNeed
    }

    private var m_exp:Int=0
    var Exp:Int
    get(){
        return m_exp
    }
    set(v:Int){
        m_exp=v
    }
    private var m_pos= Rect()
    var pos: Rect
    get(){
        return m_pos
    }
    set(v:Rect){
        m_pos=v
    }

    companion object{
        @JvmStatic
        fun loadFromJSON(o:JSONObject):Charactor{
            var c=Charactor()
            c.name=o.getString("name")
            if(!o.has("level")) {
                c.paramRank.lifeRank = enumValueOf<ParamRank.RANK>(o.getString("life"))
                c.paramRank.atkRank = enumValueOf<ParamRank.RANK>(o.getString("atk"))
                c.paramRank.torpedoRank = enumValueOf<ParamRank.RANK>(o.getString("torpedo"))
                c.paramRank.avoidRank = enumValueOf<ParamRank.RANK>(o.getString("avoid"))
                c.paramRank.airDefRank = enumValueOf<ParamRank.RANK>(o.getString("air_def"))
                c.paramRank.airAtkRank = enumValueOf<ParamRank.RANK>(o.getString("air_atk"))
         //       c.paramRank.rangeRank = enumValueOf<ParamRank.RANK>(o.getString("range"))
                if(o.has("bustupImage")) {
                    c.m_bustupImageStr = o.getString("bustupImage")

                }
                c.setupParams()
            }else{
                c=KancoreData.getInstance(null)!!.getCharaTbl()[c.name]!!.clone() as Charactor
                c.Level=o.getInt("level")
                c.setupParams()
                c.Life=o.getInt("life")
                c.Exp=o.getInt("exp")
                if(o.has("id")){
                    c.id=o.getLong("id")
                }
            }
            return c;
        }


    }

    fun toJSON():JSONObject{
        val o=JSONObject()
        o.put("name",this.name)
        o.put("level",this.Level)
        o.put("life",this.Life)
        o.put("exp",this.Exp)
        o.put("id",this.m_id)
        return o
    }

    override public fun clone():Any{
        val c=Charactor()
        c.name=this.name
        c.m_paramRank=this.m_paramRank
        c.m_bustupImageStr=this.m_bustupImageStr
        c.m_id=Date().time
        c.setupParams()
        return c
    }

    fun setupParams(){

        m_expNeed=1
        this.m_maxLife = this.paramRank.lifeRank.value//+64

        this.m_Atk = this.paramRank.atkRank.value
        for(i in 1..m_level){
            m_maxLife+=Math.max(m_maxLife*0.1f,1.0f).toInt()
            m_Atk+=Math.max(m_Atk*0.1f,1.0f).toInt()
            m_expNeed+=Math.max(m_expNeed*0.1f,1.0f).toInt()
        }
        m_Life=m_maxLife
    }


}