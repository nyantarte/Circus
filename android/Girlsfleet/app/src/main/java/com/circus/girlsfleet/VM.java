package com.circus.girlsfleet;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class VM {
    public static class VMType{
        private HashMap<String,VMType> m_members=new HashMap<>();
        public HashMap<String,VMType> getMembers(){
            return m_members;
        }
        private int m_varNum=0;
        public int getVarNum(){
            return m_varNum;
        }
        public void setVarNum(int i){
            m_varNum=i;
        }
    }
    public static class VMLiteral extends VMType{
        private Object m_value;
        public Object getValue(){
            return m_value;
        }

        public VMLiteral(Object o){
            m_value=o;
        }
    }
    public static class BindValueType extends VMType{
        private VMType m_baseType;
        public VMType getBaseType(){
            return m_baseType;
        }
        private String m_name;
        public String getName(){
            return m_name;
        }
        public BindValueType(String n,VMType b){
            m_name=n;
            m_baseType=b;
        }
    }
    public static class FuncType extends VMType{

    }
    public static class CallType extends VMType{
        private FuncType m_target;
        public FuncType getTarget(){
            return m_target;
        }
        private ArrayList<VMType> m_args=new ArrayList<>();
        public ArrayList<VMType> getArgs(){
            return m_args;
        }
        public CallType(FuncType t){
            m_target=t;
        }

    }
}
