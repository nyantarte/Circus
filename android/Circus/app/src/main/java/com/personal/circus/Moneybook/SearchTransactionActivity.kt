package com.personal.circus.Moneybook

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.personal.circus.PickDateActivity
import com.personal.circus.R
import org.json.JSONObject
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SearchTransactionActivity : AppCompatActivity() ,View.OnClickListener,AdapterView.OnItemClickListener{

    private var m_pickBeginDate: Button?=null
    private var m_pickEndDate: Button?=null
    private var m_beginDate: TextView?=null
    private var m_endDate: TextView?=null
    private var m_payMethodList:ListView?= null
    private var m_payMethodFlagList=ArrayList<Boolean>()
    private var m_usageList:ListView?=null
    private var m_ok:Button?=null
    private var m_usageFlagList=ArrayList<Boolean>()
    private val file=StrictMoneyBookFile.getInstance()//StrictMoneyBookFile(MoneyBookConfig.CONFIG_FILE_NAME,MoneyBookConfig.TRANSACTION_FILE_NAME,20)

    companion object{
        @JvmStatic
        val DEFAULT_BEGIN_DATE_KEY="DefaultBeginDate"
        @JvmStatic
        val DEFAULT_END_DATE_KEY="DefaultEndDate"
        @JvmStatic
        private val REQ_PICK_BEGIN_DATE=1
        @JvmStatic
        private val REQ_PICK_END_DATE=2

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_transaction)
        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)

        m_pickBeginDate=findViewById(com.personal.circus.R.id.SearchTransactionPickBeginDate)
        m_pickBeginDate!!.setOnClickListener(this)
        m_pickEndDate=findViewById(com.personal.circus.R.id.EditTransactionPickEndDate)
        m_pickEndDate!!.setOnClickListener(this)
        m_beginDate=findViewById(com.personal.circus.R.id.EditTransactionBeginDate)
        m_endDate=findViewById(com.personal.circus.R.id.SearchTransactionEndDate)

        if(intent.hasExtra(DEFAULT_BEGIN_DATE_KEY)){
            m_beginDate!!.text=SimpleDateFormat.getDateInstance().format(Date(intent.getLongExtra(DEFAULT_BEGIN_DATE_KEY,0)))
            m_endDate!!.text=SimpleDateFormat.getDateInstance().format(Date(intent.getLongExtra(DEFAULT_END_DATE_KEY,0)))
        }

        m_payMethodList=findViewById(R.id.SearchTransactionPayMethodList)
        m_payMethodList!!.choiceMode=ListView.CHOICE_MODE_MULTIPLE
        m_payMethodList!!.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,file.paymentMethodList.toTypedArray())
        for(i in 0..file.paymentMethodList.size-1){
            m_payMethodFlagList!!.add(true)
            m_payMethodList!!.setItemChecked(i,true)
        }
        m_payMethodList!!.setOnItemClickListener(this)

        m_usageList=findViewById(R.id.SearchTransactionUsageList)
        m_usageList!!.choiceMode=ListView.CHOICE_MODE_MULTIPLE
        m_usageList!!.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,file.usageList.toTypedArray())
        for(i in 0..m_usageList!!.adapter.count-1){
            m_usageFlagList!!.add(true)
            m_usageList!!.setItemChecked(i,true)
        }
        m_usageList!!.setOnItemClickListener(this)

        m_ok=findViewById(R.id.SearchTransactionOK)
        m_ok!!.setOnClickListener(this)
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
    override fun onClick(v: View?) {
        if(m_pickBeginDate==v){
            var i= Intent(this, PickDateActivity::class.java)
            i.putExtra(PickDateActivity.TARGET_DATE_KEY, SimpleDateFormat.getDateInstance().parse(m_beginDate!!.text.toString()))
            startActivityForResult(i, REQ_PICK_BEGIN_DATE)
        }else if(m_pickEndDate==v){
            var i= Intent(this, PickDateActivity::class.java)
            i.putExtra(PickDateActivity.TARGET_DATE_KEY, SimpleDateFormat.getDateInstance().parse(m_endDate!!.text.toString()))
            startActivityForResult(i, REQ_PICK_END_DATE)
        }else if(m_ok==v){

            val i=Intent(this,SearchTransactionResultActivity::class.java)
            val pfl=HashMap<String,Boolean>()
            for(i in 0..file.paymentMethodList.size-1){
                pfl.put(file.paymentMethodList.get(i),m_payMethodFlagList.get(i))
            }
            val ufl=HashMap<String,Boolean>()
            for(i in 0..file.usageList.size-1){
                ufl.put(file.usageList.get(i),m_usageFlagList.get(i))
            }
//            file.loadTransaction()
            val beginDate=SimpleDateFormat.getDateInstance().parse(m_beginDate!!.text.toString())
            val endDate=SimpleDateFormat.getDateInstance().parse(m_endDate!!.text.toString())
            endDate.hours=23
            endDate.minutes=59
            endDate.seconds=59
            val tList= file.getTransactions(beginDate,
                endDate,
                pfl,
                ufl)

            val fmt=SimpleDateFormat.getDateInstance()
            Log.i(this.javaClass.name,String.format("Search date range %s - %s",fmt.format(beginDate),fmt.format(endDate)))
            Log.i(this.javaClass.name,String.format("Search Transaction result count %d",tList.size))

/*            val rList=ArrayList<String>()
            for(t in tList){
                rList.add(t.toJSONObject().toString())
            }
*/
            //   i.putStringArrayListExtra(SearchTransactionResultActivity.SEARCH_RESULT_LIST_KEY,rList)
            i.putExtra(SearchTransactionResultActivity.SEARCH_RESULT_LIST_KEY,tList.toTypedArray() as Array<Serializable>)
            i.putExtra(SearchTransactionResultActivity.SEARCH_RESULT_DATE_BEGIN_KEY,beginDate.time)
            i.putExtra(SearchTransactionResultActivity.SEARCH_RESULT_DATE_END_KEY,endDate.time)
            startActivity(i)



        }

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(m_payMethodList==parent){
            m_payMethodFlagList[position]=!m_payMethodFlagList[position]
            m_payMethodList!!.setItemChecked(position,m_payMethodFlagList[position])
        }else if(m_usageList==parent){
            m_usageFlagList[position]=!m_usageFlagList[position]
            m_usageList!!.setItemChecked(position,m_usageFlagList[position])

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(Activity.RESULT_OK== resultCode){
            when(requestCode){
                REQ_PICK_BEGIN_DATE->{
                    val tmp=Date(data!!.getLongExtra(PickDateActivity.TARGET_DATE_KEY,0))
                    tmp.hours=0
                    tmp.minutes=0
                    tmp.seconds=0
                    m_beginDate!!.text=SimpleDateFormat.getDateInstance().format(tmp)
                }
                REQ_PICK_END_DATE->{
                    val tmp=Date(data!!.getLongExtra(PickDateActivity.TARGET_DATE_KEY,0))
                    tmp.hours=23
                    tmp.minutes=59
                    tmp.seconds=59
                    m_endDate!!.text=SimpleDateFormat.getDateInstance().format(tmp)

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
