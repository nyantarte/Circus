package com.personal.circus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XmlElement extends XmlNode {

    private String m_name;
    private ArrayList<XmlNode> m_child=new ArrayList<>();
    private ArrayList<XmlAttribute> m_attributes=new ArrayList<>();

    public XmlElement(String n){
        m_name=n;
    }
    public int getNodeType(){
        return TYPE_ELEMENT;
    }
    public String getName(){
        return m_name;
    }
    public String getValue(){
        if(null!=m_child) {
            return m_child.get(0).getValue();
        }
        return null;

    }
    public List<XmlNode> getChild(){
        return m_child;
    }
    public List<XmlAttribute> getAtrributeList(){
        return m_attributes;
    }

    public List<XmlElement> find(String s){
        ArrayList<XmlElement> r=new ArrayList<>();
        for (XmlNode n : m_child){
            if(TYPE_ELEMENT== n.getNodeType()){

                XmlElement e=((XmlElement)n);

                if(e.getName().equals(s)){
                    r.add(e);
                }else{
                    r.addAll(e.find(s));
                }


            }
        }
        return r;
    }
    public XmlAttribute getAttribute(String n){
        for(XmlAttribute t:m_attributes){
            if(n.equals(t.getName())){
                return t;
            }
        }
        return null;
    }

}
