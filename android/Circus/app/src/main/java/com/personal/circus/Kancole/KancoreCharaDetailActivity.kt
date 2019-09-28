package com.personal.circus.Kancole

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import com.personal.circus.R
import org.json.JSONObject

class KancoreCharaDetailActivity : AppCompatActivity() {

    companion object{
        @JvmStatic
        val TARGET_CHARA_KEY="targetChara"
    }
    private var m_target:Charactor?=null
    private var m_paramList: ListView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kancore_chara_detail)
        val ab = supportActionBar
        ab!!.show()
        ab!!.setDisplayHomeAsUpEnabled(true)


        m_target=KancoreData.getInstance(assets)!!.getPlayerCharaTbl().elementAt(intent.getIntExtra(TARGET_CHARA_KEY,0))
        m_paramList=findViewById(R.id.KancoreCharaDetailParam)
        val tmp=ArrayList<String>()
        tmp.add(String.format("Name %s",m_target!!.name))
        tmp.add(String.format("Level %d",m_target!!.Level))
        tmp.add(String.format("Exp %d/%d",m_target!!.Exp,m_target!!.ExpNeed))
        tmp.add(String.format("HP %d/%d",m_target!!.Life,m_target!!.MaxLife))
        tmp.add(String.format("Life %s",m_target!!.paramRank.lifeRank.rankName))
        tmp.add(String.format("Atk %s",m_target!!.paramRank.atkRank.rankName))
        tmp.add(String.format("Torpedo %s",m_target!!.paramRank.torpedoRank.rankName))
        tmp.add(String.format("Avoid %s",m_target!!.paramRank.avoidRank.rankName))
        tmp.add(String.format("AirDef %s",m_target!!.paramRank.airDefRank.rankName))
        tmp.add(String.format("AirAtk %s",m_target!!.paramRank.airAtkRank.rankName))
        m_paramList!!.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tmp.toTypedArray())

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
        return super.onOptionsItemSelected(item)
    }

}
