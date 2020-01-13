package com.personal.circus.Kancole

import android.graphics.Color
import com.personal.circus.IGameState
import com.personal.circus.IGameSystem
import com.personal.circus.IRenderer
import com.personal.circus.ScreenMode

class RootState: IGameState {

    private var m_count=0
    private var m_target:Charactor?=null
    private var m_scMode:ScreenMode?=null
    constructor(sys:IGameSystem){
        m_scMode=sys.getScreenMode()
        m_target=KancoreData.getInstance(null)!!.getRandomChara(sys,0)

    }
    override fun onDraw(r: IRenderer){
        r.setColor(Color.WHITE)
        r.drawText(m_scMode!!.getRect().left,m_scMode!!.getRect().top,m_target!!.name)
    }
    override fun onUpdate(s: IGameSystem){
        if(KancoleConfig.RESULT_WAIT_COUNT==m_count){
            KancoreData.getInstance(null)!!.getPlayerCharaTbl().add(m_target!!)
            s.getStateStack().pop()
            return
        }
        ++m_count
    }
    override fun onTouch(s: IGameSystem, x: Int, y: Int){}

}