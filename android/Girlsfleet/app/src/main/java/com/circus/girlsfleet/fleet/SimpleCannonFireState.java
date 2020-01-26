package com.circus.girlsfleet.fleet;

import android.graphics.Color;

import com.circus.girlsfleet.Charactor;
import com.circus.girlsfleet.GameConfig;
import com.circus.girlsfleet.IGameState;
import com.circus.girlsfleet.IGameSystem;
import com.circus.girlsfleet.IRenderer;
import com.circus.girlsfleet.Vector;
//砲撃の処理を行う
public class SimpleCannonFireState implements IGameState {
    private SimpleBattleState m_battleState;
    private FleetCharactor m_atk,m_def;
    private Vector m_bPos,m_bTarget,m_bSpeed;
    private int m_count=0;
    /*
        @brief キャラクター毎に処理を行う
        @param bs 戦闘画面を表示するために必要
        @param atk 攻撃側のキャラクター
        @param def 被攻撃側のキャラクター
        @param pos 弾の初期位置
        @param tar 弾の終了位置
    */
    public SimpleCannonFireState(SimpleBattleState bs, FleetCharactor atk, FleetCharactor def, Vector pos, Vector tar){
        m_battleState=bs;
        m_atk=atk;
        m_def=def;
        m_bPos=pos;
        m_bTarget=tar;
        //弾の毎フレーム毎の速度を求める
        m_bSpeed=Vector.sub(m_bTarget,m_bPos);
        m_bSpeed=Vector.div(m_bSpeed, GameConfig.BULLET_MOVE_COUNT);

    }
    public void onDraw(IRenderer r){

        m_battleState.onDraw(r);    //背景の戦闘画面を表示する
        r.setColor(Color.WHITE);
        r.fillRect(m_bPos,GameConfig.BULLET_SIZE);  //砲弾を描画
    }
    public void onUpdate(IGameSystem s){

        if(GameConfig.BULLET_MOVE_COUNT> m_count){
            //砲弾の移動処理
            m_bPos=Vector.add(m_bPos,m_bSpeed);
            ++m_count;
        }else{
            //砲撃処理の終了
            assert GameConfig.BULLET_MOVE_COUNT==m_count;
            s.getStateStack().pop();    //自身の状態を終了させる
            m_def.setLife(m_def.getLife()-m_atk.getAtk());  //ダメージ分だけ被攻撃側からライフを減らす
                        //ダメージ数値表示処理を行う
            s.getStateStack().push(new SimpleBattleDamageShowState(m_battleState,m_bTarget,m_atk.getAtk()));
        }
    }
    public void onTouch(IGameSystem s, int x,int y){}
}
