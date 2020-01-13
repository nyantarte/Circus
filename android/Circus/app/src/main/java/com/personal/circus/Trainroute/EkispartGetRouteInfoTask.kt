package com.personal.circus.Trainroute

import android.os.AsyncTask
import android.util.Log
import com.personal.circus.Weather.LivedoorAreaInfo
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL
import java.util.ArrayList
class EkispartGetRouteInfoTask: AsyncTask<URL, Void, List<String>> {

    constructor(){}
    override fun doInBackground(vararg params: URL?): List<String>{
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
        Log.i(this.javaClass.name,result.toString())
        return ArrayList<String>()
    }

}