package com.circus.girlsfleet.fleet;

import android.graphics.Color;
import android.graphics.Rect;

import com.circus.girlsfleet.Charactor;
import com.circus.girlsfleet.GameConfig;
import com.circus.girlsfleet.IGameState;
import com.circus.girlsfleet.IGameSystem;
import com.circus.girlsfleet.IRenderer;
import com.circus.girlsfleet.MainActivity;
import com.circus.girlsfleet.Party;
import com.circus.girlsfleet.ScreenMode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class SimpleBattleState implements IGameState {
    private Rect[][] m_nodes=new Rect[2][GameConfig.FLEET_MEMBER_MAX];
    private FleetParty[] m_party=new FleetParty[2];
    private ArrayList<FleetCharactor> m_atkQueue = new ArrayList<>();
    public SimpleBattleState(IGameSystem s, FleetParty e){
        ScreenMode sm=s.getScreenMode();
        int top=sm.getRect().top;
        int left=sm.getRect().left;
        int width=sm.getRect().width()/3;
        int height=sm.getRect().height()/GameConfig.FLEET_MEMBER_MAX;
        for(int i=0;i < m_nodes[0].length;++i){
            int nt=top+height*i;
            m_nodes[0][i]=new Rect(left,nt,left+width,nt+height);

        }
        left=sm.getRect().left+width*2;

        for(int i=0;i < m_nodes[1].length;++i){
            int nt=top+height*i;
            m_nodes[1][i]=new Rect(left,nt,left+width,nt+height);

        }
        m_party[0]=(FleetParty) MainActivity.mainParty;
        m_party[1]=e;

        for(int i=0;i < m_party.length;++i){
            FleetCharactor[] m=m_party[i].getMembers();
            for(int j=0;j < m.length;++j){
                if(null!=m[j]){
                    m_atkQueue.add(m[j]);
                }
            }
        }
        /*
        m_atkQueue.sort(new Comparator<Charactor>() {
            @Override
            public int compare(Charactor charactor, Charactor t1) {

            }
        });
*/

    }
    public void onDraw(IRenderer r){
        r.setColor(Color.WHITE);
        for(Rect[] ar:m_nodes){
            for(Rect rc:ar){
                r.drawRect(rc);

            }
        }
    }
    public void onUpdate(IGameSystem s){

    }
    public void onTouch(IGameSystem s, int x,int y){}
}
