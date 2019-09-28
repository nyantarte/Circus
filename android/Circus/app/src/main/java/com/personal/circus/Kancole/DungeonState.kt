package com.personal.circus.Kancole;
import android.graphics.Color
import android.graphics.Rect
import com.personal.circus.Vector
import com.personal.circus.IGameState
import com.personal.circus.IGameSystem
import com.personal.circus.IRenderer

class DungeonState : IGameState {
    private class Room{
        private var m_rect= Rect()
        val rect:Rect
            get() {
                return m_rect
            }


        constructor(r:Rect){
            m_rect=r
        }
    }
    private val m_rooms=Array<Room?>(3,{null})
    private var m_nRooms=0
    private var m_pos= Vector.zero.clone() as Vector
    private var m_dir=Vector.zero.clone() as Vector
    private var m_nCurRoom=0
    private var m_nMoveCount=0
    constructor(s: IGameSystem){

        createFloor(s)

    }
    override fun onDraw(r: IRenderer){
        r.setColor(Color.WHITE)
        for(room in m_rooms){
            if(null!=room){
                r.drawRect(room.rect)
            }
        }

        for(i in 0..1){
            if(null!=m_rooms[i] && null!=m_rooms[i+1]){
                val r1=m_rooms[i]!!
                val r2=m_rooms[i+1]!!
                r.drawLine(r1.rect.centerX(),r1.rect.centerY(),r2.rect.centerX(),r2.rect.centerY())
            }
        }
        r.setColor(Color.GREEN)
        r.fillRect(m_pos.getX().toInt(),m_pos.getY().toInt(),KancoleConfig.MIN_ROOM_SIZE,KancoleConfig.MIN_ROOM_SIZE)
    }
    override fun onUpdate(s: IGameSystem){

        if(KancoleConfig.PLAYER_MOVE_COUNT==m_nMoveCount && m_nCurRoom==m_rooms.size){

        }
        if(0 < m_nMoveCount){
            m_pos=Vector.add(Vector(),m_pos,m_dir)
            --m_nMoveCount
        }else if(0==m_nMoveCount && m_nCurRoom< m_rooms.size-1){
            m_pos=Vector(m_rooms[m_nCurRoom++]!!.rect)
            val tmp=Vector.sub(Vector(),Vector(m_rooms[m_nCurRoom]!!.rect),m_pos)
            Vector.div(m_dir,tmp,KancoleConfig.PLAYER_MOVE_COUNT.toFloat())
            m_nMoveCount=KancoleConfig.PLAYER_MOVE_COUNT
            s.getStateStack().push(BattleState(KancoreData.getInstance(null)!!.getPlayerFleet(),KancoreData.getInstance(null)!!.createRandomFleet(s),s))
        }
    }
    override fun onTouch(s: IGameSystem, x: Int, y: Int){}

    private  fun createRoom(s:IGameSystem,r:Rect){
        if(s.getRand().nextBoolean()){
            m_rooms[m_nRooms++]=Room(r)
        }else{
            if(s.getRand().nextBoolean()){
                val nH=r.height()/2
                val newRect1=Rect(r.left,r.top,r.right,r.top+nH)
                val newRect2=Rect(r.left,r.top+nH,r.right,r.bottom)
                if(s.getRand().nextBoolean()) {
                    createRoom(s, newRect1)
                }else{
                    createRoom(s,newRect2)
                }
            }else{
                val nW=r.width()/2
                val newRect1=Rect(r.left,r.top,r.left+nW,r.bottom)
                val newRect2=Rect(r.left+nW,r.top,r.right,r.bottom)
                if(s.getRand().nextBoolean()) {
                    createRoom(s, newRect1)
                }else{
                    createRoom(s,newRect2)
                }

            }
        }
    }
    private fun createFloor(s:IGameSystem){
        val rH=                s.getScreenMode().getHeight()/3
        for(i in 0..2) {
            createRoom(s, Rect(s.getScreenMode().getRect().left, s.getScreenMode().getRect().top+rH*i, s.getScreenMode().getWidth(),rH*(i+1)))
        }

    }

}