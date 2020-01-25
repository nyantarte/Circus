package com.circus.girlsfleet.fleet;

import android.graphics.Color;
import android.graphics.Point;


import com.circus.girlsfleet.Charactor;
import com.circus.girlsfleet.GameConfig;
import com.circus.girlsfleet.MainActivity;
import com.circus.girlsfleet.Map;
import com.circus.girlsfleet.IGameState;
import com.circus.girlsfleet.IGameSystem;
import com.circus.girlsfleet.IRenderer;
import com.circus.girlsfleet.ScreenMode;
import com.circus.girlsfleet.Size;
import com.circus.girlsfleet.Vector;

import java.util.List;
import java.util.Random;

public class MapState implements IGameState {
    private Map m_map;
    private Size m_nodeSize;
    private ScreenMode m_screenMode;
    private Object[][] m_mapGrid;
    private int m_battleNum=0;
    private Vector m_playerPos,m_playerDir;
    private int m_count=0;
    private Point m_playerGrid,m_targetGrid;
    public MapState(IGameSystem s, Map m){
        m_map=m;
        m_nodeSize=new Size(
                s.getScreenMode().getRect().width()/m.getSize().getWidth(),
                s.getScreenMode().getRect().height()/m.getSize().getHeight()
        );
        m_screenMode=s.getScreenMode();
        m_mapGrid=new Object[m_map.getSize().getHeight()][m_map.getSize().getWidth()];
        m_mapGrid[0][0]=new Charactor();
        m_playerGrid=new Point(0,0);

    }
    public void onDraw(IRenderer r){
        if(MainActivity.isDrawSimpleMode) {
            r.setColor(Color.WHITE);

            for (int i = 0; i < m_map.getSize().getHeight(); ++i) {
                for (int j = 0; j < m_map.getSize().getWidth(); ++j) {
                    int x = m_screenMode.getRect().left + m_nodeSize.getWidth() * j;
                    int y = m_screenMode.getRect().top + m_nodeSize.getHeight() * i;
                    r.drawRect(x, y, m_nodeSize.getWidth(), m_nodeSize.getHeight());
                    Object o=m_mapGrid[i][j];
                    if(null!=o) {
                        if (o instanceof Charactor) {
                            Charactor c = (Charactor) o;
                            int cx = x + m_nodeSize.getWidth() / 2;
                            int cy = y + m_nodeSize.getHeight() / 2;
                            r.fillRect(new Vector(cx, cy), 16);
                        }
                    }
                }
            }
            if(null!=m_playerPos){
                r.setColor(Color.GREEN);
                r.fillRect(m_playerPos,16);
            }
        }
    }
    public void onUpdate(IGameSystem s){
        if(GameConfig.MAP_MOVE_COUNT<m_count){
            m_count=0;
        }
        if(0==m_count){
            if(m_map.getBattleNum()== m_battleNum){
                s.getStateStack().pop();
            }
            genEnemy(s);
            //移動の為の初期化
            int px=gridXToScreenX(m_playerGrid.x);
            int py=gridYToScreenY(m_playerGrid.y);
            int tx=gridXToScreenX(m_targetGrid.x);
            int ty=gridYToScreenY(m_targetGrid.y);
            m_playerPos=new Vector(px,py);
            m_playerDir=Vector.sub(new Vector(tx,ty),m_playerPos);
            m_playerDir=Vector.div(m_playerDir, GameConfig.MAP_MOVE_COUNT);
            m_mapGrid[m_playerGrid.y][m_playerGrid.x]=null;

        }
        if(GameConfig.MAP_MOVE_COUNT>m_count){
            //移動実行
            m_playerPos=Vector.add(m_playerPos,m_playerDir);
            ++m_count;
        }
        if(GameConfig.MAP_MOVE_COUNT==m_count){
            FleetParty eParty=new FleetParty();
            List<String> cTbl=m_map.getDropCharaList();
            for(int i=0;i < s.getRandom().nextInt(eParty.getMembers().length);++i)
            {
                String tmpName=cTbl.get(s.getRandom().nextInt(cTbl.size()));
                eParty.getMembers()[i]=new FleetCharactor(MainActivity.charaTbl.get(tmpName));
            }
            m_playerGrid.x=m_targetGrid.x;
            m_playerGrid.y=m_targetGrid.y;
            s.getStateStack().push(new SimpleBattleState(s,eParty));
            ++m_battleNum;
            ++m_count;
        }
    }
    public void onTouch(IGameSystem s, int x,int y){

    }

    private void genEnemy(IGameSystem s){

        int x=-1;
        int y=-1;
        do {
            x = s.getRandom().nextInt(m_mapGrid[0].length);
            y = s.getRandom().nextInt(m_mapGrid.length);
        } while(null!=m_mapGrid[y][x]);

        m_targetGrid=new Point(x,y);

            m_mapGrid[y][x]=new Charactor();



    }
    private int gridXToScreenX(int gx){
        return m_screenMode.getRect().left + gx*m_nodeSize.getWidth()+m_nodeSize.getWidth() / 2;
    }
    private int gridYToScreenY(int gy){
        return m_screenMode.getRect().top + m_nodeSize.getHeight()*gy+m_nodeSize.getHeight() / 2;
    }
}
