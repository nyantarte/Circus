package com.personal.circus.Kancole

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import com.personal.circus.R
import java.io.Serializable

class CatalogActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener ,AdapterView.OnItemClickListener{

    private val TYPE_CHARA=0
    private var m_type:Spinner?=null
    private var m_list:ListView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)

        m_type=findViewById(R.id.CatalogType)
        m_type!!.setOnItemSelectedListener(this)
        m_type!!.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayOf("Charactor"))
        m_list=findViewById(R.id.CatalogList)
        m_list!!.setOnItemClickListener(this)
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        if(TYPE_CHARA==position){
            m_list!!.adapter=
                ArrayAdapter(this,android.R.layout.simple_list_item_1,KancoreData.getInstance(assets)!!.getCharaTbl().values.toTypedArray())
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if(TYPE_CHARA==m_type!!.selectedItemPosition){
            val c=m_list!!.adapter.getItem(position) as Charactor
            val i= Intent(this,KancoreCharaDetailActivity::class.java)

            i.putExtra(KancoreCharaDetailActivity.IS_FROM_CATALOG,true)
            i.putExtra(KancoreCharaDetailActivity.TARGET_CHARA_KEY,position)
            startActivity(i)
        }
    }
}
