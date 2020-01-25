package com.circus.girlsfleet.fleet;

import com.circus.girlsfleet.Charactor;

import java.util.HashMap;

public class FleetCharactor {

    private Charactor m_baseData;
    private int m_life,m_maxLife;
    public int getMaxLife(){
        return m_maxLife;
    }
    public int getLife(){
        return m_life;
    }
    public void setLife(int v){
        m_life=v;
    }
    private int m_atk;
    public int getAtk(){
        return m_atk;
    }
    public String getName(){
        return m_baseData.getName();
    }

    private static HashMap<Charactor.PARAMETER,Integer> s_paramTbl=new HashMap<>();
    static{
        s_paramTbl.put(Charactor.PARAMETER.S,0x11);
        s_paramTbl.put(Charactor.PARAMETER.A,0xF);
        s_paramTbl.put(Charactor.PARAMETER.B,0x7);
        s_paramTbl.put(Charactor.PARAMETER.C,0x3);
        s_paramTbl.put(Charactor.PARAMETER.D,0x1);
        s_paramTbl.put(Charactor.PARAMETER.E,0);
    }

    public FleetCharactor(Charactor c){
        m_baseData=c;
        m_life=s_paramTbl.get(m_baseData.getLife());
        m_atk=s_paramTbl.get(m_baseData.getAtk());
        for(int i=0;i < m_baseData.getLevel();++i){
            m_life+=Math.max(1,m_life*0.1f);
            m_atk+=Math.max(1,m_atk*0.1f);

        }
        m_maxLife=m_life;

    }

}
