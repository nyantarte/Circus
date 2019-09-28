package com.personal.circus.Kancole

import org.json.JSONArray
import org.json.JSONObject

class Fleet {


    var members = Array<Charactor?>(KancoleConfig.FLEET_MEMBER_NUM, { null })

    constructor() {}
    constructor(o: JSONObject) {
        val a = o.getJSONArray("members")
        for (i in 0..members.size - 1) {

            val t = a.getInt(i)

            if (-1 != t) {
                members[i] = KancoreData.getInstance(null)!!.getPlayerCharaTbl()[t]
            }
        }
    }

    fun toJSON():JSONObject{
        val o=JSONObject()
        val a=JSONArray()
        val tbl=KancoreData.getInstance(null)!!.getPlayerCharaTbl()
        for(i in 0..members.size-1){
            var m=-1
            if(null!=members[i]){
                for(j in 0..tbl.size-1){
                    if(members[i]==tbl[j]){
                        m=j
                        break
                    }
                }

            }
            a.put(i,m)
        }
        o.put("members",a)
        return o
    }


    override fun toString():String{
        var r=""
        for(i in 0..members.size-1){
            val t=members[i]
            if(null==t){
                r+="null,"
            }else{
                r+=t.name+","
            }
        }

        return r
    }


}