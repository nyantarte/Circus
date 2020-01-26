package com.circus.girlsfleet.fleet;

import android.graphics.Rect;

import com.circus.girlsfleet.GameConfig;
import com.circus.girlsfleet.IGameState;
import com.circus.girlsfleet.IGameSystem;
import com.circus.girlsfleet.IRenderer;
/**
    @brief 戦闘結果画面の処理を行う
*/
public class SimpleBattleResult implements IGameState {
    private SimpleBattleState m_bstate;
    private int m_count;
    
    /**
        @param bs バナー枠を表示するのに必要
    */
    public SimpleBattleResult(SimpleBattleState bs){
        m_bstate=bs;
    }
    public void onDraw(IRenderer r){
        m_bstate.onDraw(r); //バナー枠を描画
        
        //各キャラクターのバナー枠内にダメージ状態を文字で表示
        for(int i=0;i < m_bstate.getNodes().length;++i){
            for(int j=0;j < m_bstate.getNodes()[i].length;++j){
                FleetCharactor c=m_bstate.getParties()[i].getMembers()[j];
                if(null!=c){
                    float v=c.getLife()/((float)c.getMaxLife());
                    Rect rect=m_bstate.getNodes()[i][j];
                    
                    //ダメージ状態は無傷、小破、中破、大破
                    
                    if(GameConfig.BIG_DAMAGE_VALUE>v){

                        r.drawText(rect.centerX(),rect.centerY(),"大破");
                    }else if(GameConfig.MIDDLE_DAMAGE_VALUE>v){
                        r.drawText(rect.centerX(),rect.centerY(),"中破");

                    }else if(GameConfig.LITTLE_DAMAGE_VALUE>v){
                        r.drawText(rect.centerX(),rect.centerY(),"小破");

                    }//無傷状態は表示しない
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
