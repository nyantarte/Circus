package com.personal.circus.Kancole

import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import com.personal.circus.Vector
import com.personal.circus.IGameState
import com.personal.circus.IGameSystem
import com.personal.circus.IRenderer
import com.personal.circus.ScreenMode
import java.util.*

/**
 * @brief
 * 戦闘画面を管理する状態クラス
 * The class that management battle state.
 */
class BattleState :IGameState{

    private var m_player:Fleet?=null        /*!Player fleet that has passed from the constructor*/
    private var m_enemy:Fleet?=null         /*!Enemy fleet that has passed from the constructor*/
    private var m_scMode:ScreenMode?=null   /*!Screen mode that IGameSystem instance holds*/
    private val m_atkQueue= LinkedList<Charactor>() /*!Queue to negothiate the attack order*/
    private var m_attacker:Charactor?= null /*!Charactor is attacking*/
    private var m_defender:Charactor?=null  /*!Charactor that attacker is attacking*/
    private var m_bulletCount=0             /*!Bullet move count. When it is 0, the new attacker has set.*/
    private var m_bulletPos:Vector?=null    /*!Current bullet position.It moves from attacker to defender*/
    private var m_bulletSpeed:Vector?=null /*!Bullet move speed*/

    /**
     * @param p Player fleet object
     * @param e Enemy fleet object
     * @param s IGameSystem instance
     */
    constructor(p:Fleet,e:Fleet,s:IGameSystem){
        m_player=p
        m_enemy=e

        m_scMode=s.getScreenMode()

        setupPos()
        setupQueue()


    }
    override fun onDraw(r: IRenderer){
        r.setColor(Color.WHITE)

        for(i in 0..m_player!!.members.size-1){
            if(null!=m_player!!.members[i]){
                val c=m_player!!.members[i]
                r.setColor(Color.GREEN)
                val lPerc=c!!.Life/c!!.MaxLife.toFloat()
                r.fillRect(c!!.pos.left,c!!.pos.top,(c!!.pos.width()*lPerc).toInt(),c!!.pos.height())
                r.setColor(Color.WHITE)
                r.drawRect(c!!.pos)
                r.drawText(c!!.pos.left,c!!.pos.top+c!!.pos.height()/2,c!!.name)
            }
        }
        for(i in 0..m_enemy!!.members.size-1){
            if(null!=m_enemy!!.members[i]){
                val c=m_enemy!!.members[i]
                r.setColor(Color.RED)
                val lPerc=c!!.Life/c!!.MaxLife.toFloat()
                r.fillRect(c!!.pos.left,c!!.pos.top,(c!!.pos.width()*lPerc).toInt(),c!!.pos.height())
                r.setColor(Color.WHITE)
                r.drawRect(c!!.pos)
                r.drawText(c!!.pos.left,c!!.pos.top+c!!.pos.height()/2,c!!.name)
            }

        }

        if(null!=m_bulletPos){
            r.fillRect(m_bulletPos!!,32)
        }

    }
    override fun onUpdate(s: IGameSystem){
        if(0==m_bulletCount){
            if(null!=m_attacker && null!=m_defender){
//                Log.i("",m_defender!!.Life.toString())
                m_defender!!.Life=m_defender!!.Life-getDamageValue(m_attacker!!)
//                m_defender!!.Life=Math.max(m_defender!!.Life,0)
//                Log.i("",m_defender!!.Life.toString())
            }
            if(0==m_atkQueue.size){
                Log.i(this.javaClass.name,"Battle ended")
                s.getStateStack().pop()
                s.getStateStack().push(BattleResult(m_player!!,m_enemy!!,m_scMode!!))
                return
            }
            m_attacker=m_atkQueue.pop()
            m_defender=getTarget(m_attacker!!)
            if(null==m_defender)
                return
            m_bulletPos=Vector(m_attacker!!.pos)
            m_bulletSpeed=Vector.sub(Vector(),Vector(m_defender!!.pos),m_bulletPos!!)
            m_bulletSpeed=Vector.div(Vector(),m_bulletSpeed!!,30.0F)
            m_bulletCount=30


        }else{
            --m_bulletCount
            m_bulletPos=Vector.add(Vector(),m_bulletPos!!,m_bulletSpeed!!)
        }
    }
    override fun onTouch(s: IGameSystem, x: Int, y: Int){

    }


    private fun setupQueue(){
        m_atkQueue.clear()
        for(c in m_player!!.members){
            if(null!=c && 0 < c!!.Life){
                m_atkQueue.add(c)
            }
        }

        for(c in m_enemy!!.members){
            if(null!=c && 0 < c!!.Life){
                m_atkQueue.add(c)
            }
        }
    }

    private fun setupPos(){
        val nW=m_scMode!!.getWidth()/3
        val nH=m_scMode!!.getHeight()/KancoleConfig.FLEET_MEMBER_NUM
        for(i in 0..m_player!!.members.size-1){
            val c=m_player!!.members[i]
            val l=m_scMode!!.getRect().left
            val t=m_scMode!!.getRect().top+nH*i
            if(null!=c){
                c.pos= Rect(l,t,l+nW,t+nH)
            }
        }
        for(i in 0..m_enemy!!.members.size-1){
            val c=m_enemy!!.members[i]
            val l=m_scMode!!.getRect().right-nW
            val t=m_scMode!!.getRect().top+nH*i
            if(null!=c){
                c.pos= Rect(l,t,l+nW,t+nH)
            }
        }
    }

    private fun getDamageValue(c :Charactor):Int{
//        Log.i("",Math.sqrt(c.Atk.toDouble()).toInt().toString())
        val v= Math.max(Math.sqrt(c.Atk.toDouble()).toInt(),1)
       // Log.i("",v.toString())
        return v
    }

    private fun getTarget(attacker:Charactor):Charactor?{
        for(c in m_player!!.members){
            if(c==attacker){
                for(t in m_enemy!!.members){
                    if(null!=t && 0 < t!!.Life){
                        return t
                    }
                }
            }
        }
        for(c in m_player!!.members){
            if(null!=c && 0 < c!!.Life){
                return c
            }
        }
        return null
    }
}