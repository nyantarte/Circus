package com.circus.girlsfleet.fleet;

import android.graphics.Rect;

import com.circus.girlsfleet.GameConfig;
import com.circus.girlsfleet.IGameState;
import com.circus.girlsfleet.IGameSystem;
import com.circus.girlsfleet.IRenderer;

public class SimpleBattleResult implements IGameState {
    private SimpleBattleState m_bstate;
    private int m_count;
    public SimpleBattleResult(SimpleBattleState bs){
        m_bstate=bs;
    }
    public void onDraw(IRenderer r){
        m_bstate.onDraw(r);
        for(int i=0;i < m_bstate.getNodes().length;++i){
            for(int j=0;j < m_bstate.getNodes()[i].length;++j){
                FleetCharactor c=m_bstate.getParties()[i].getMembers()[j];
                if(null!=c){
                    float v=c.getLife()/((float)c.getMaxLife());
                    Rect rect=m_bstate.getNodes()[i][j];
                    if(GameConfig.BIG_DAMAGE_VALUE>v){

                        r.drawText(rect.centerX(),rect.centerY(),"大破");
                    }else if(GameConfig.MIDDLE_DAMAGE_VALUE>v){
                        r.drawText(rect.centerX(),rect.centerY(),"中破");

                    }else if(GameConfig.LITTLE_DAMAGE_VALUE>v){
                        r.drawText(rect.centerX(),rect.centerY(),"小破");

                    }
                }
            }
        }
    }
    public void onUpdate(IGameSystem s){
        if(GameConfig.MAP_MOVE_COUNT==m_count){
            s.getStateStack().pop();

            return;
        }
        ++m_count;
    }
    public void onTouch(IGameSystem s, int x,int y){

    }
}
