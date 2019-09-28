package com.personal.circus.Moneybook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.personal.circus.R
import android.view.MenuItem




class EditUsageActivity : AppCompatActivity(),AdapterView.OnItemClickListener {

    private var m_usageList:ListView?=null
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

}
