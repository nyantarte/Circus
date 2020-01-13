package com.circus.girlsfleet.script.lisp;

import com.circus.girlsfleet.VM;

import java.util.ArrayList;
import java.util.Stack;

public class LispParser {
    private ArrayList<String> m_tokens=new ArrayList<>();
    private int m_parsePos=0;
    private VM m_vm;
    private Stack<VM.VMType> m_typeStack=new Stack<>();

    public LispParser(VM vm){
        m_vm=vm;
        m_typeStack.push(new VM.VMType());
    }
    public void registType(String n,VM.VMType t){
        m_typeStack.peek().getMembers().put(n,t);
    }
    public void parse(String src){
        splitToken(src);

    }
    private VM.VMType parseNamespace()
    throws Exception
    {
        while(m_tokens.size() >  m_parsePos){
            String tok=m_tokens.get(m_parsePos);
            if(!"(".equals(tok)){
                     parsingError(String.format("( is needed! Not %s",tok));
            }
            tok=m_tokens.get(m_parsePos+1);
            if("let".equals(tok)){
                parseLet();
            }else{
                parseCall();
            }
        }
        return m_typeStack.peek();
    }
    private VM.VMType parseCall()
    throws Exception
    {
        String name=m_tokens.get(++m_parsePos);
        VM.VMType t=findMember(name);
        if(null==t){
            parsingError(String.format("Invalid token %s",name));
        }
        VM.CallType ct=new VM.CallType((VM.FuncType)t);
        while(m_tokens.size()> m_parsePos && !")".equals(m_tokens.get(++m_parsePos))){
            String tok=m_tokens.get(m_parsePos);
            VM.VMType at;
            if("(".equals(tok)){
                at=(parseCall());
                tok=m_tokens.get(m_parsePos);
                if(m_tokens.size()<= m_parsePos){
                    parsingError(") needs");
                }

            }else{

                at=parseValue();
            }
            ct.getArgs().add(at);
        }
        return t;

    }
    private void parseLet()
    throws Exception
    {
        assert "let".equals(m_tokens.get(++m_parsePos));
        String name=m_tokens.get(++m_parsePos);
        if(null!=findMember(name)){
            parsingError(String.format("%s is registered!",name));
        }else{
            int idx=m_typeStack.peek().getVarNum();
            registType(name,new VM.BindValueType(name,parseValue()));
            m_typeStack.peek().setVarNum(++idx);
        }
    }
    private VM.VMType parseValue()
    throws Exception{
        String tok=m_tokens.get(++m_parsePos);
        if('\''==tok.charAt(0)){
            char eb=tok.charAt(tok.length()-1);
            if('\''!=eb){
                parsingError(String.format("String literals need \' not %s",eb));
            }
            return new VM.VMLiteral(tok.substring(1,tok.length()-1));

        }else if(Character.isDigit(tok.charAt(0))){
            return new VM.VMLiteral(Integer.parseInt(tok));
        }
        parsingError(String.format("Invalid token %s",tok));
        return null;
    }
    private VM.VMType findMember(String n){
        if(m_typeStack.peek().getMembers().containsKey(n)){
            return m_typeStack.peek().getMembers().get(n);
        }
        return null;
    }
    private void parsingError(String msg)
    throws Exception
    {
        throw new Exception(msg);
    }
    private void splitToken(String src){
        while(0< src.length()) {
            if(Character.isWhitespace(src.charAt(0))) {
                src = skipWhiteSpace(src);
            }
            else if (Character.isLetter(src.charAt(0))) {
                src=storeIdent(src);
            }else if(Character.isDigit(src.charAt(0))){
                src=storeDigit(src);
            }else if('\''==src.charAt(0)){
                src=storeLitString(src);
            }else{
                src=storeOthers(src);
            }
        }
    }
    private String skipWhiteSpace(String src){
        while(0 < src.length() && Character.isWhitespace(src.charAt(0))){
            src=src.substring(1);
        }
        return src;
    }
    private String storeIdent(String src){
        StringBuilder sb=new StringBuilder();
        while(0 < src.length() && Character.isLetter(src.charAt(0))){
            sb.append(src.charAt(0));
            src=src.substring(1);
        }
        m_tokens.add(sb.toString());
        return src;
    }
    private String storeDigit(String src){
        StringBuilder sb=new StringBuilder();
        while(0 < src.length() && Character.isDigit(src.charAt(0))){
            sb.append(src.charAt(0));
            src=src.substring(1);
        }
        m_tokens.add(sb.toString());
        return src;
    }
    private String storeLitString(String src){
        StringBuilder sb=new StringBuilder();
        if('\''==src.charAt(0)){
            sb.append(src.charAt(0));
            src=src.substring(1);
            while (0 < src.length() && '\''!=src.charAt(0)){
                sb.append(src.charAt(0));
                src=src.substring(1);

            }
            if(0 < src.length()){
                sb.append(src.charAt(0));
                src=src.substring(1);
            }

            m_tokens.add(sb.toString());
        }
        return src;
    }
    private String storeOthers(String src){
        assert 0 <src.length();
        if('('==src.charAt(0) || ')'==src.charAt(0)){
            m_tokens.add(src.substring(0,1));
            src=src.substring(1);
        }
        return src;
    }
}
