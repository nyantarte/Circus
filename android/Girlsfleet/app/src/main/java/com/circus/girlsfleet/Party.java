package com.circus.girlsfleet;

public class Party {
    private Charactor[] m_members=new Charactor[GameConfig.FLEET_MEMBER_MAX];
    public Charactor[] getMembers(){
        return m_members;
    }

}
