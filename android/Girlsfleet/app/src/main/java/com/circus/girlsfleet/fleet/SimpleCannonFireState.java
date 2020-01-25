package com.circus.girlsfleet.fleet;

import android.graphics.Color;

import com.circus.girlsfleet.Charactor;
import com.circus.girlsfleet.GameConfig;
import com.circus.girlsfleet.IGameState;
import com.circus.girlsfleet.IGameSystem;
import com.circus.girlsfleet.IRenderer;
import com.circus.girlsfleet.Vector;

public class SimpleCannonFireState implements IGameState {
    private SimpleBattleState m_battleState;
    private FleetCharactor m_atk,m_def;
    private Vector m_bPos,m_bTarget,m_bSpeed;
    private int m_count=0;
    public SimpleCannonFireState(SimpleBattleState bs, FleetCharactor atk, FleetCharactor def, Vector pos, Vector tar){
        m_battleState=bs;
        m_atk=atk;
        m_def=def;
        m_bPos=pos;
        m_bTarget=tar;
        m_bSpeed=Vector.sub(m_bTarget,m_bPos);
        m_bSpeed=Vector.div(m_bSpeed, GameConfig.BULLET_MOVE_COUNT);

    }
    public void onDraw(IRenderer r){

        m_battleState.onDraw(r);
        r.setColor(Color.WHITE);
        r.fillRect(m_bPos,GameConfig.BULLET_SIZE);
    }
    public void onUpdate(IGameSystem s){

        if(GameConfig.BULLET_MOVE_COUNT> m_count){
            m_bPos=Vector.add(m_bPos,m_bSpeed);
            ++m_count;
        }else{
            assert GameConfig.BULLET_MOVE_COUNT==m_count;
            s.getStateStack().pop();
            m_def.setLife(m_def.getLife()-m_atk.getAtk());
            s.getStateStack().push(new SimpleBattleDamageShowState(m_battleState,m_bTarget,m_atk.getAtk()));
        }
    }
    public void onTouch(IGameSystem s, int x,int y){}
}
