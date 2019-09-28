package com.personal.circus;

public class XmlAttribute extends XmlNode {
    private String m_name,m_value;

    public XmlAttribute(String n,String v){
        m_name=n;
        m_value=v;
    }

    public String getName(){
        return m_name;
    }
    public int getNodeType(){
        return TYPE_ATTRIBUTE;
    }
    public String getValue(){
        return m_value;
    }
}
