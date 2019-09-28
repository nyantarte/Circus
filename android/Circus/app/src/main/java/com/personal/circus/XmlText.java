package com.personal.circus;

public class XmlText extends XmlNode {
    private String m_text;

    public XmlText(String t){
        m_text=t;
    }
    public int getNodeType(){
        return TYPE_TEXT;
    }
    public String getValue(){
        return m_text;
    }

}
