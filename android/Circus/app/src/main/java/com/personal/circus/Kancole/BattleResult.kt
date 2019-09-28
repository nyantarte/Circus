package com.personal.circus.Kancole

import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import com.personal.circus.*

class BattleResult: IGameState {
    private var m_player:Fleet?=null        /*!Player fleet that has passed from the constructor*/
    private var m_enemy:Fleet?=null         /*!Enemy fleet that has passed from the constructor*/
    private var m_scMode: ScreenMode?=null   /*!Screen mode that IGameSystem instance holds*/
    private var m_result:Float=0.0f
    private var m_count=0
    /**
     * @param p Player fleet object
     * @param e Enemy fleet object
     * @param s IGameSystem instance
     */
    constructor(p:Fleet,e:Fleet,sm:ScreenMode){
        m_player=p
        m_enemy=e

        m_scMode=sm

        setupPos()


        calcResult()
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

                var tmpS=String.format("%s %d/%d",c!!.name,c!!.Exp,c!!.ExpNeed)
                if(c!!.Exp>=c!!.ExpNeed){
                    tmpS=String.format("%s Level up!!",tmpS)
                }
                r.drawText(c!!.pos.left,c!!.pos.top+c!!.pos.height()/2,tmpS)
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
        var resStr="勝利"
        if(0.0f>m_result)
            resStr="敗北"
        r.drawText(m_scMode!!.getRect().left,(m_scMode!!.getRect().top+ Config.FONT_SIZE).toInt(),String.format("%.1f %s",m_result,resStr))
    }
    override fun onUpdate(s: IGameSystem){
        if(KancoleConfig.RESULT_WAIT_COUNT==m_count){
            for(c in m_player!!.members){
                if(null!=c ){
                    while(c.Exp>=c.ExpNeed) {
                        c.Exp = c.Exp - c.ExpNeed
                        c.Level = c.Level + 1
        //                c.setupParams()
                    }
                }
            }
            KancoreData.getInstance(null)!!.saveState()
            s.getStateStack().pop()
            if(0.0f>m_result){
                s.getStateStack().pop()
            }
            s.getStateStack().push(RootState(s))
            return
        }
        ++m_count
    }
    override fun onTouch(s: IGameSystem, x: Int, y: Int){

    }
    private fun setupPos(){
        val nW=m_scMode!!.getWidth()/3
        val nH=m_scMode!!.getHeight()/(KancoleConfig.FLEET_MEMBER_NUM+1)
        for(i in 0..m_player!!.members.size-1){
            val c=m_player!!.members[i]
            val l=m_scMode!!.getRect().left
            val t=m_scMode!!.getRect().top+nH*(i+1)
            if(null!=c){
                c.pos= Rect(l,t,l+nW,t+nH)
            }
        }
        for(i in 0..m_enemy!!.members.size-1){
            val c=m_enemy!!.members[i]
            val l=m_scMode!!.getRect().right-nW
            val t=m_scMode!!.getRect().top+nH*(i+1)
            if(null!=c){
                c.pos= Rect(l,t,l+nW,t+nH)
            }
        }
    }

    private fun calcResult(){
        if(0 >= m_player!!.members[0]!!.Life){
            m_result=-1.0f
        }else {
            var cP = 0
            var mP = 0
            m_result = cP / mP.toFloat()
            for (c in m_player!!.members) {
                if (null != c) {
                    cP += c.Life
                    mP += c.MaxLife
                }
            }

            cP = 0
            mP = 0

            for (c in m_enemy!!.members) {
                if (null != c) {
                    cP += c.Life
                    mP += c.MaxLife

                }
            }

            m_result -= (cP / mP.toFloat())
        }
        var eLevelTotal = 0
        for(c in m_enemy!!.members) {
            if (null != c) {
                eLevelTotal += c.Level
            }
        }
        var exp=eLevelTotal
        if(m_result<=0.0f) {
            exp = 1
        }
        for(c in m_player!!.members){
            if(null!=c){
                c.Exp=c.Exp+exp
                Log.i(javaClass.name,String.format("%s current exp is %d",c.name,c.Exp))
            }
        }


    }
}