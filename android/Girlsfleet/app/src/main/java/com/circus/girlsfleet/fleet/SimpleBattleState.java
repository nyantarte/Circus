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
import com.circus.girlsfleet.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
/**
    @brief 戦闘状態を処理する
*/
public class SimpleBattleState implements IGameState {
    private Rect[][] m_nodes=new Rect[2][GameConfig.FLEET_MEMBER_MAX];
    private FleetParty[] m_party=new FleetParty[2];
    private ArrayList<FleetCharactor> m_atkQueue = new ArrayList<>();

    public Rect[][] getNodes(){
        return m_nodes;
    }
    public FleetParty[] getParties(){
        return m_party;
    }
    /**
        @param s 進行を管理しているゲームシステムオブジェクト
        @param e プレイヤーと対決する敵艦隊 
    */
    public SimpleBattleState(IGameSystem s, FleetParty e){
        ScreenMode sm=s.getScreenMode();
        
        //画面上に表示するキャラクターのバナー枠を求める
        //縦6行、横2列作る
        int top=sm.getRect().top;
        int left=sm.getRect().left;
        int width=sm.getRect().width()/3;
        int height=sm.getRect().height()/GameConfig.FLEET_MEMBER_MAX;
        
        //プレイヤー側
        for(int i=0;i < m_nodes[0].length;++i){
            int nt=top+height*i;
            m_nodes[0][i]=new Rect(left,nt,left+width,nt+height);

        }
        left=sm.getRect().left+width*2;
        //敵側
        for(int i=0;i < m_nodes[1].length;++i){
            int nt=top+height*i;
            m_nodes[1][i]=new Rect(left,nt,left+width,nt+height);

        }
        m_party[0]=(FleetParty) MainActivity.mainParty;
        m_party[1]=e;

        //攻撃キューを準備
        //このキュー内の順番で攻撃を行う
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

        for(int i=0;i < m_nodes.length;++i){
            Rect[] ar=m_nodes[i];
            for(int j=0;j < ar.length;++j) {
                Rect rc = ar[j];
                r.drawRect(rc);
                FleetCharactor tmp = m_party[i].getMembers()[j];
                if (null != tmp) {
                    r.drawText(rc.left + 8, rc.centerY(), tmp.getName());
                }
            }
        }
    }
    public void onUpdate(IGameSystem s){
        if(0==m_atkQueue.size()){   //戦闘終了?
            s.getStateStack().pop();
            //戦闘結果を表示
            s.getStateStack().push(new SimpleBattleResult(this));
            return;
        }
        
    
        FleetCharactor c=m_atkQueue.get(0);//キューからキャラクターをエンキューする
        m_atkQueue.remove(0);
        FleetCharactor tar=getTarget(c,s.getRandom());//被攻撃側を求める

        //攻撃側、被攻撃側それぞれのバナー枠の位置を求める
        Rect cRect=getRect(c);
        Rect tRect=getRect(tar);
        //攻撃処理を登録する
        s.getStateStack().push(new SimpleCannonFireState(this,c,tar,new Vector(cRect),new Vector(tRect)));
    }
    public void onTouch(IGameSystem s, int x,int y){}
    
    /**
        @return 攻撃対象となるキャラクターを返す
        @param 攻撃を行うキャラクター
        @param 算出に使う乱数オブジェクト
        */
    private FleetCharactor getTarget(FleetCharactor c, Random rand){
        FleetParty tParty=null;
        for(int i=0;i < m_party.length;++i){
            for(int j=0;j < m_party[i].getMembers().length;++j){
                //攻撃を行うのががプレイヤー側か敵側か?
                FleetCharactor tmp=m_party[i].getMembers()[j];
                if(tmp==c){
                    tParty=m_party[((i+1)& 0x1)];
                    break;
                }
            }
        }
        assert null!=tParty;

        //被攻撃側の艦隊から被攻撃側となるキャラクターを求める
        FleetCharactor r=null;
        do{
            r=tParty.getMembers()[rand.nextInt(tParty.getMembers().length)];

        }while(null==r || 0>=r.getLife());
        return r;
    }
    

    /**
        @return 指定されたキャラクターのバナー枠を返す
        @param c バナー枠を求めるキャラクター
    */    
    private Rect getRect(FleetCharactor c){
        for(int i=0;i < m_party.length;++i){
            for(int j=0;j < m_party[i].getMembers().length;++j){
                FleetCharactor tmp=m_party[i].getMembers()[j];
                if(tmp==c){
                    return m_nodes[i][j];
                }
            }
        }
        assert false;
        return null;
    }
}
