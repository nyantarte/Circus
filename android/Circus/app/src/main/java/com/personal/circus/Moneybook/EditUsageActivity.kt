package com.personal.circus.Moneybook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.personal.circus.R
import android.view.MenuItem
import android.widget.*
import java.util.*;


class EditUsageActivity : AppCompatActivity(),AdapterView.OnItemClickListener,View.OnClickListener {

    private var m_usageList:ListView?=null
    private var m_addUsageBtn:Button?=null
    private var m_editUsageValue:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.personal.circus.R.layout.activity_edit_usage)
        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)

        val file = StrictMoneyBookFile.getInstance()//StrictMoneyBookFile(MoneyBookConfig.CONFIG_FILE_NAME, "", 20)

        m_usageList = findViewById(com.personal.circus.R.id.EditUsageList)
        m_usageList!!.choiceMode = ListView.CHOICE_MODE_SINGLE
        m_usageList!!.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, file.usageList.toTypedArray())
        m_usageList!!.setOnItemClickListener(this)
        m_addUsageBtn=findViewById(R.id.EditUsageAdd)
        m_addUsageBtn!!.setOnClickListener(this)
        m_editUsageValue=findViewById(R.id.EditUsageValue)

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        m_usageList!!.setItemChecked(position, true)
        Log.i(this.javaClass.name,String.format("%d item clicked",position))
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(p0: View?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if(m_addUsageBtn==p0){
            val t=m_editUsageValue!!.text.toString()
            val file=StrictMoneyBookFile.getInstance()
            val usageL=file.usageList as ArrayList<String>
            usageL.add(t)
            m_usageList!!.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,usageL)
        }
    }
}
