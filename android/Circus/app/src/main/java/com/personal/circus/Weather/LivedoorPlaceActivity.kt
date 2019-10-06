package com.personal.circus.Weather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.personal.circus.R

class LivedoorPlaceActivity : AppCompatActivity() ,AdapterView.OnItemClickListener{
    companion object{
        @JvmStatic
        val TARGET_PLACE_LIST="placeList"
    }

    private var m_placeData:Array<LivedoorAreaInfo>?=null;
    private var m_placeList:ListView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedoor_place)
        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)

        m_placeData=            intent.getSerializableExtra(
            TARGET_PLACE_LIST) as Array<LivedoorAreaInfo>

        m_placeList=findViewById(R.id.LivedoorWeatherPlaceList)
        m_placeList!!.choiceMode=ListView.CHOICE_MODE_MULTIPLE
        m_placeList!!.adapter=ArrayAdapter<LivedoorAreaInfo>(this,android.R.layout.simple_list_item_multiple_choice,
            m_placeData!!)

        for(i in 0..m_placeData!!.size-1){
            m_placeList!!.setItemChecked(i,m_placeData!![i].check)
        }

        m_placeList!!.setOnItemClickListener(this)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            //アクションバーの戻るボタン
            android.R.id.home -> {
                //結果を呼び出し元アクティビティに返さない
                finish()
                return true
            }

        }
        return onOptionsItemSelected(item)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        m_placeData!![position].check= !m_placeData!![position].check;
        m_placeList!!.setItemChecked(position,m_placeData!![position].check)
        Log.i(this.javaClass.name,String.format("%s,%s",m_placeData!![position].name,m_placeData!![position].check.toString()))
    }
}
