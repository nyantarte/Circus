package com.personal.circus.Kancole

import android.content.res.AssetManager
import android.util.Log
import com.personal.circus.IGameSystem
import com.personal.circus.IOUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class KancoreData{
    private val m_charaCollectTbl=java.util.HashMap<String,Charactor>()
    private val m_playerCharaTbl=java.util.ArrayList<Charactor>()
    private var m_playerFleet=Fleet()


    private constructor(am:AssetManager?){
        assert(null!=am)

        for(f in am!!.list(KancoleConfig.CHARA_DIR)){
            val txt=IOUtils.loadPlainText(File(KancoleConfig.CHARA_DIR,f),am)
            val jsonObj=JSONObject(txt)
            val obj=Charactor.loadFromJSON(jsonObj)
            m_charaCollectTbl.put(obj.name,obj)
        }
        val c=m_charaCollectTbl.values.elementAt(0)!!.clone() as Charactor
        m_playerCharaTbl.add(c)
        m_playerFleet.members[0]= m_playerCharaTbl.elementAt(0)

    }
    companion object{
        @JvmStatic
        private var s_obj:KancoreData?=null
        @JvmStatic
        fun getInstance(am:AssetManager?):KancoreData?{
            if(null== s_obj){
                s_obj= KancoreData(am)
            }
            return s_obj
        }

    }
    fun getCharaTbl():HashMap<String,Charactor>{
        return m_charaCollectTbl;
    }

    fun getPlayerCharaTbl():ArrayList<Charactor>{

        return m_playerCharaTbl
    }
    fun getPlayerFleet():Fleet{
        return m_playerFleet
    }

    fun createRandomFleet(s:IGameSystem):Fleet{
        val r=Fleet()
        val ctbl= m_charaCollectTbl
        for(i in 0..r.members.size-1){
            val k=(s.getRand().nextFloat().toInt()*ctbl.size)
            r.members[i]= m_charaCollectTbl.values.elementAt(k)!!.clone() as Charactor
            r.members[i]!!.Level=1
            r.members[i]!!.setupParams()

        }
        return r
    }

    fun saveState(){
        val saveO=IOUtils.createFile(KancoleConfig.SAVE_FILE)
        val o=JSONObject()

        var a=JSONArray()
        for(m in m_playerCharaTbl){
            a.put(m.toJSON())
        }
        o.put("playerCharaTbl",a)
        o.put("playerFleet",this.m_playerFleet.toJSON())

        saveO.write(o.toString())
        saveO.close()

    }

    fun loadState(){
        val loadT=IOUtils.loadPlainText(File(KancoleConfig.SAVE_FILE),null)
        if(null!=loadT) {
            val o = JSONObject(loadT)
            var a=o.getJSONArray("playerCharaTbl")
            m_playerCharaTbl.clear()

            for(i in 0..a.length()-1){
                m_playerCharaTbl.add(Charactor.loadFromJSON(a.getJSONObject(i)))
            }


            m_playerFleet=Fleet(o.getJSONObject("playerFleet"))
            for(t in m_playerFleet.members){
                if(null!=t){
                    t.Life=t.MaxLife
                }
            }
            Log.i(this.javaClass.name,m_playerFleet.toString())

        }

    }

    fun getRandomChara(s:IGameSystem):Charactor{
        return m_charaCollectTbl.values.elementAt((m_charaCollectTbl.values.size* s.getRand().nextFloat()).toInt())
    }


}