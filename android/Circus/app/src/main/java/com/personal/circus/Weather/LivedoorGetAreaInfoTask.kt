package com.personal.circus.Weather

import android.os.AsyncTask
import android.util.Log
import android.util.Xml
import com.personal.circus.XmlUtils
import org.xmlpull.v1.XmlPullParser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL

class LivedoorGetAreaInfoTask: AsyncTask<URL, Void, List<LivedoorAreaInfo>> {
    constructor(){}
    override fun doInBackground(vararg params: URL?): List<LivedoorAreaInfo> {

        val result = StringBuilder()
        var con: HttpURLConnection? = null
        try {
            // ローカル処理
            // コネクション取得
            con = params[0]!!.openConnection() as HttpURLConnection
            con.setReadTimeout(10000 /* milliseconds */);
            con.setConnectTimeout(15000 /* milliseconds */);
            con.requestMethod="GET"
            con!!.doInput = true
            con.connect()

            // HTTPレスポンスコード
            val status = con.responseCode
            Log.d("debug", Integer.toString(status))
            if (status == HttpURLConnection.HTTP_OK) {

                Log.d("debug", result.toString())

                // 通信に成功した
                // テキストを取得する
                val ist = con.inputStream
                val inReader = InputStreamReader(ist)
                val bufReader = BufferedReader(inReader)
                var line: String? = null
                // 1行ずつテキストを読み込む
                while(true){
                    line=bufReader.readLine()
                    if(null==line)
                        break
                    result.append(line)
                }
                bufReader.close()
                inReader.close()
                ist.close()
            }
            Log.d("debug", result.toString())

        } catch (e1: MalformedURLException) {
            e1.printStackTrace()
        } catch (e1: ProtocolException) {
            e1.printStackTrace()
        } catch (e1: IOException) {
            e1.printStackTrace()
        } finally {
            con?.disconnect()
        }
//        return result.toString()
        return parseXML(result.toString());
    }

    private fun parseXML(s:String):List<LivedoorAreaInfo>{

        val r=ArrayList<LivedoorAreaInfo>();

        val e=XmlUtils.parseString(s);
        val list=e.find("city");
        for(t in list){
            r.add(LivedoorAreaInfo(t.getAttribute("title").value,t.getAttribute("id").value,t.getAttribute("source").value))
        }

        return r;
    }
}