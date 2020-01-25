package com.circus.girlsfleet.fleet;

import android.graphics.Color;
import android.graphics.Rect;

import com.circus.girlsfleet.GameConfig;
import com.circus.girlsfleet.IGameState;
import com.circus.girlsfleet.IGameSystem;
import com.circus.girlsfleet.IRenderer;
import com.circus.girlsfleet.Vector;

public class SimpleBattleDamageShowState implements IGameState {
    private SimpleBattleState m_bstate;
    private Vector m_targetPoint;
    private int m_value;
    private int m_count;
    public SimpleBattleDamageShowState(SimpleBattleState bs, Vector po, int v){
        m_bstate=bs;
        m_targetPoint=po;
        m_value=v;

    }
    public void onDraw(IRenderer r){
        m_bstate.onDraw(r);
        r.setColor(Color.RED);
        r.drawText((int)m_targetPoint.getX(),(int)m_targetPoint.getY(),Integer.toString(m_value));
    }
    public void onUpdate(IGameSystem s){
        if(GameConfig.MAP_MOVE_COUNT==m_count){
            s.getStateStack().pop();
            return;
        }
        ++m_count;

    }

    public void onTouch(IGameSystem s, int x,int y){}
}
