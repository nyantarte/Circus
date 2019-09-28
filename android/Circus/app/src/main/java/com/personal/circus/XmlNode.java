package com.personal.circus;

public abstract  class XmlNode {

    public static final int TYPE_ELEMENT=0;
    public static final int TYPE_ATTRIBUTE=TYPE_ELEMENT+1;
    public static final int TYPE_DOC=TYPE_ATTRIBUTE+1;
    public static final int TYPE_TEXT=TYPE_DOC+1;

    public abstract int getNodeType();
    public abstract String getValue();
}
