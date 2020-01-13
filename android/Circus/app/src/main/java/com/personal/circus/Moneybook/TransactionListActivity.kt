package com.personal.circus.Moneybook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.personal.circus.R
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TransactionListActivity : AppCompatActivity() ,AdapterView.OnItemClickListener{

    companion object{
        @JvmStatic
        val TRANS_LIST_KEY="TransactionList"
        val TRANS_BEGIN_DATE_KEY="BeginDate"
        val TRANS_END_DATE_KEY="EndDate"
        val TRANS_USAGE_KEY="Usage"
    }
    private var m_tranList:ListView?=null
    private val m_data=ArrayList<MoneyBookTransaction>()
    private var m_date:TextView?=null
    private var m_value:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)



        m_tranList=findViewById(R.id.TransactionListList)
        m_tranList!!.setOnItemClickListener(this)

        m_date=findViewById(R.id.TransactionListDate)

        val fmt=SimpleDateFormat.getDateInstance()
        m_date!!.text=String.format("%s - %s",
            fmt.format(Date(intent.getLongExtra(TRANS_BEGIN_DATE_KEY,0))),
            fmt.format(Date(intent.getLongExtra(TRANS_END_DATE_KEY,0)))
            )
        var vIn=0
        var vOut=0
        for(i in 0..m_data.size-1){
            val t=m_data[i]
            if(0 > t.value){
                vOut+=t.value
            }else{
                vIn+=t.value
            }
        }
        m_value=findViewById(R.id.TransactionListValue)
        m_value!!.text=String.format("収入 %d 支出 %d",vIn,vOut)
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

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val i= Intent(this,EditTransactionListActivity::class.java)
        val tran=m_data.get(position)

        //i.putExtra(EditTransactionListActivity.MOD_TARGET_KEY, tran.toJSONObject().toString())
        i.putExtra(EditTransactionListActivity.MOD_TARGET_KEY, tran)
        startActivity(i)
    }

    override fun onStart() {
        super.onStart()
        for(s in (intent.getSerializableExtra(TRANS_LIST_KEY) as Array<MoneyBookTransaction>)/*intent.getStringArrayListExtra(TRANS_LIST_KEY)*/){
//            val obj=JSONObject(s)
            val obj=s
//            m_data.add(MoneyBookTransaction(obj))
            m_data.add(obj)
        }
        m_tranList!!.adapter=ArrayAdapter<MoneyBookTransaction>(this,android.R.layout.simple_list_item_1,m_data.toTypedArray())
    }


}
