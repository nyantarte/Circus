package com.personal.circus.Weather

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.personal.circus.R
import kotlinx.android.synthetic.main.activity_live_door_weather.view.*
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL


class LivedoorWeatherActivity : AppCompatActivity() {

    private var m_places: ArrayList<LivedoorAreaInfo>?=null;
    private var m_weatherList: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.personal.circus.R.layout.activity_live_door_weather)
        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)
        m_weatherList=findViewById(R.id.LivedoorWeatherList)
        val list=ArrayList<String>()
        try {
            m_places =
                (LivedoorGetAreaInfoTask().execute(URL("http://weather.livedoor.com/forecast/rss/primary_area.xml"))).get() as ArrayList<LivedoorAreaInfo>;

            for(t in m_places!!) {
                if(t.check) {
                    val data =
                        (LivedoorWeatherTask().execute(URL(
                            "http://weather.livedoor.com/forecast/webservice/json/v1?city="+t.id)))

                    val obj = JSONObject(data.get())
                    val tmp = obj.getJSONArray("forecasts").getJSONObject(0).getJSONObject("image")
                        .getString("title")
                    list.add(String.format("%s %s",t.name,tmp))
                }
            }
            m_weatherList!!.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list.toTypedArray())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //main.xmlの内容を読み込む
        val inflater = menuInflater
        inflater.inflate(com.personal.circus.R.menu.livedoor_weather_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            com.personal.circus.R.id.LivedoorWeatherPlacesMenu->{
                val i= Intent(this,LivedoorPlaceActivity::class.java)
                i.putExtra(LivedoorPlaceActivity.TARGET_PLACE_LIST,m_places!!.toTypedArray())
                startActivity(i)
                //return true;
            }
            //アクションバーの戻るボタン
            android.R.id.home -> {
                //結果を呼び出し元アクティビティに返さない
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}