package com.circus.girlsfleet.fleet;

import com.circus.girlsfleet.GameConfig;
import com.circus.girlsfleet.Party;

public class FleetParty {

    private Party m_baseData;
    private FleetCharactor[] m_members;

    public FleetParty(){
        m_members=new FleetCharactor[GameConfig.FLEET_MEMBER_MAX];
    }
    public FleetParty(Party p){
        m_baseData=p;
        m_members=new FleetCharactor[m_baseData.getMembers().length];
        for(int i=0;i < m_baseData.getMembers().length;++i){
           if(null!=m_baseData.getMembers()[i]){

               m_members[i]=new FleetCharactor(m_baseData.getMembers()[i]);
           }
        }
    }
    public FleetCharactor[] getMembers(){
        return m_members;
    }

}
