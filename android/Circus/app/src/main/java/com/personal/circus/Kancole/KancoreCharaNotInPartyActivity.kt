package com.personal.circus.Kancole
import android.app.Activity
import android.content.Intent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.personal.circus.R
import java.lang.Exception

class KancoreCharaNotInPartyActivity : AppCompatActivity() ,AdapterView.OnItemClickListener{

    companion object{
        const val CHARA_PICK_TARGET_KEY="pickTarget"
    }
    private var m_notInPartyList:ListView?=null
    private var m_targetIdx:Int=0   /*!設定対象となるプレイヤーの艦隊でのスロットインデックス*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kancore_chara_not_in_party)
        val ab = supportActionBar
        ab!!.show()
        ab!!.setDisplayHomeAsUpEnabled(true)

        m_notInPartyList=findViewById(R.id.KancoreNotInPartyList)
        m_notInPartyList!!.adapter=ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,KancoreData.getInstance(assets)!!.getPlayerCharaListNotInFleet())
        m_notInPartyList!!.setOnItemClickListener(this)

        m_targetIdx=intent.getIntExtra(CHARA_PICK_TARGET_KEY,-1)


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
        return false
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        if(-1!=m_targetIdx) {
            try {
                val c =
                    (m_notInPartyList!!.adapter.getItem(position)) as com.personal.circus.Kancole.Charactor

                KancoreData.getInstance(assets)!!.getPlayerFleet().members.set(m_targetIdx, c)
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
        finish()
    }
}
