package com.personal.circus;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Stack;

public class XmlUtils {

    public static XmlElement parseString(String s) {

        Stack<XmlElement> eStack= new Stack<>();
        eStack.push(new XmlElement(""));
        try {
            XmlPullParser xpp = Xml.newPullParser();

            xpp.setInput(new StringReader(s));
            //解析するXMLファイルの中身を渡す
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.i("MainActivity", "ドキュメント開始");
                        break;
                    case XmlPullParser.START_TAG:
                        Log.i("MainActivity", xpp.getName() + "要素開始");
                        XmlElement e=new XmlElement(xpp.getName());
                        eStack.peek().getChild().add(e);
                        eStack.push(e);
                        int attrCount = xpp.getAttributeCount();
                        for (int i = 0; i < attrCount; ++i) {
                            Log.i("MainActivity", "    " +
                                    i + "番目の属性 = " + xpp.getAttributeName(i));
                            eStack.peek().getAtrributeList().add(new XmlAttribute(xpp.getAttributeName(i),xpp.getAttributeValue(i)));
                        }
                        break;
                    case XmlPullParser.TEXT:
                        Log.i("MainActivity", "テキスト = " + xpp.getText());
                        eStack.peek().getChild().add(new XmlText(xpp.getText()));
                        break;
                    case XmlPullParser.END_TAG:
                        Log.i("MainActivity", xpp.getName() + "要素終了");
                        eStack.pop();
                        break;
                }
                eventType = xpp.next();
                //次のトークンに進む
            }
            Log.i("MainActivity", "ドキュメント終了");
        } catch (XmlPullParserException e) {
            Log.e("MainActivity", "XMLの解析失敗.");
        } catch (IOException e) {
            Log.e("MainActivity", "XMLファイルの読み込みに失敗.");
        }


        return (XmlElement)(eStack.peek().getChild().get(0));
    }
}
