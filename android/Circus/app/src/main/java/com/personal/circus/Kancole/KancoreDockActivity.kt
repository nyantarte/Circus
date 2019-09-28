package com.personal.circus.Kancole

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

import android.R
import android.content.Intent
import android.widget.ArrayAdapter


class KancoreDockActivity : AppCompatActivity() ,AdapterView.OnItemClickListener{

    var m_dockList:ListView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ab = supportActionBar
        ab!!.show()
        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(com.personal.circus.R.layout.activity_kancore_dock)


        m_dockList=findViewById(com.personal.circus.R.id.KancoreDockList)
        m_dockList!!.setOnItemClickListener(this)
        m_dockList!!.adapter=ArrayAdapter<Any>(this,android.R.layout.simple_list_item_1,
            KancoreData.getInstance(assets)!!.getPlayerCharaTbl().toTypedArray())



    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val c=parent!!.adapter.getItem(position) as Charactor
        val i= Intent(this,KancoreCharaDetailActivity::class.java)
        i.putExtra(KancoreCharaDetailActivity.TARGET_CHARA_KEY,position)
        startActivity(i)


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

