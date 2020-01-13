package com.personal.circus.Moneybook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import kotlin.collections.HashMap

class SearchTransactionResultActivity : AppCompatActivity() ,AdapterView.OnItemClickListener{

    companion object{
        @JvmStatic
        val SEARCH_RESULT_LIST_KEY="SearchResultList"
        val SEARCH_RESULT_DATE_BEGIN_KEY="SearchDateBegin"
        val SEARCH_RESULT_DATE_END_KEY="SearchDateEnd"

    }

    private val m_categoryList=HashMap<String,ArrayList<MoneyBookTransaction>>()
    private var m_resultList:ListView?=null
    private var m_resultDate:TextView?=null
    private var m_resultValue:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_transaction_result)

        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)


        m_resultList=findViewById(R.id.SearchTransactionResultList)


        m_resultList!!.setOnItemClickListener(this)

        val fmt=SimpleDateFormat.getDateInstance()
        val beginDate=fmt.format(Date(intent.getLongExtra(SEARCH_RESULT_DATE_BEGIN_KEY,0)))
        val endDate=fmt.format(Date(intent.getLongExtra(SEARCH_RESULT_DATE_END_KEY,0)))
        Log.i(this.javaClass.name,String.format("Search result date %s %s",beginDate,endDate))
        m_resultDate=findViewById(R.id.SearchResultDate)
        m_resultDate!!.text=String.format("%s - %s",beginDate,endDate)

        m_resultValue=findViewById(R.id.SearchResultValue)

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
        val key=m_resultList!!.adapter.getItem(position)
        val list=m_categoryList.get(key)

        val i=Intent(this, TransactionListActivity::class.java)
/*        val tmp=ArrayList<String>()
        for(t in list!!){
            tmp.add(t.toJSONObject().toString())
        }
        i.putStringArrayListExtra(TransactionListActivity.TRANS_LIST_KEY,tmp)
*/
        i.putExtra(TransactionListActivity.TRANS_LIST_KEY,list!!.toTypedArray())
        i.putExtra(TransactionListActivity.TRANS_BEGIN_DATE_KEY,intent.getLongExtra(SEARCH_RESULT_DATE_BEGIN_KEY,0))
        i.putExtra(TransactionListActivity.TRANS_END_DATE_KEY,intent.getLongExtra(SEARCH_RESULT_DATE_END_KEY,0))
        i.putExtra(TransactionListActivity.TRANS_USAGE_KEY,key as String)
        startActivity(i)

    }
    override fun onStart(){
        super.onStart()
        m_categoryList.clear()
        val tmpTbl=HashMap<String,ArrayList<MoneyBookTransaction>>()
        for(s in (intent.getSerializableExtra(SEARCH_RESULT_LIST_KEY) as Array<MoneyBookTransaction>)){
//            val obj=JSONObject(s)
//            val tran=MoneyBookTransaction(obj)
            val tran=s
            if(!tmpTbl.containsKey(tran.usage)){
                tmpTbl.put(tran.usage,ArrayList<MoneyBookTransaction>())

            }
            tmpTbl.get((tran.usage))!!.add(tran)
        }
        var vIn=0
        var vOut=0
        for(k in tmpTbl.keys){
            var v=0
            val l=tmpTbl.get(k)
            for(i in 0..l!!.size-1){
                val tv=l[i].value
                v+=tv
                if(0 > tv){
                    vOut+=tv
                }else{
                    vIn+=tv
                }
            }
            m_categoryList.put(String.format("%s %d",k,v),l)
        }
        m_resultList!!.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,m_categoryList.keys.toTypedArray())
        m_resultValue!!.text=String.format("収入 %d 支出 %d",vIn,vOut)
    }
}
