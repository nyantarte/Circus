package com.personal.circus.Moneybook

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.personal.circus.PickDateActivity
import com.personal.circus.R
import kotlinx.android.synthetic.main.activity_edit_transaction_list.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class EditTransactionListActivity : AppCompatActivity() ,View.OnClickListener,AdapterView.OnItemClickListener{

    private var m_pickDate:Button?=null
    private var m_targetDate:TextView?=null
    private var m_addTransaction:Button?=null
    private val m_dataList=ArrayList<MoneyBookTransaction>()
    private var m_transactionList:ListView?=null
    private var m_ok:Button?=null
    private var m_total:TextView?=null
    companion object{
        @JvmStatic
        val REQ_PICK_DATE=1
        @JvmStatic
        val REQ_EDIT_TRANSACTION=2

        @JvmStatic
        val MOD_TARGET_KEY="ModTargetKey"


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaction_list)
        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)


        m_pickDate=findViewById(com.personal.circus.R.id.EditTransactionPickDate)
        m_pickDate!!.setOnClickListener(this)

        m_targetDate=findViewById(com.personal.circus.R.id.EditTransactionListTargetDate)
        m_targetDate!!.text=SimpleDateFormat.getInstance().format(Date())



        m_addTransaction=findViewById(com.personal.circus.R.id.EditTransactionListAdd)
        m_addTransaction!!.setOnClickListener(this)


        m_transactionList=findViewById(R.id.EditTransactionList)
        if(intent.hasExtra(MOD_TARGET_KEY)){
//            val obj=JSONObject(intent.getStringExtra(MOD_TARGET_KEY))
//            val t=MoneyBookTransaction(obj)
            val t=intent.getSerializableExtra(MOD_TARGET_KEY) as MoneyBookTransaction
            m_dataList.add(t)
            m_targetDate!!.text=SimpleDateFormat.getInstance().format(Date(t.timeStamp))
            m_transactionList!!.adapter=ArrayAdapter<MoneyBookTransaction>(this,android.R.layout.simple_list_item_1,m_dataList.toTypedArray())
        }
        m_transactionList!!.setOnItemClickListener(this)
        m_ok=findViewById(R.id.EditTransactionOK)
        m_ok!!.setOnClickListener(this)
        m_total=findViewById(R.id.EditTransactionListTotal)
    }

    override fun onClick(v: View?) {
        if(m_pickDate==v){
            val i=Intent(this,PickDateActivity::class.java)
            i.putExtra(PickDateActivity.TARGET_DATE_KEY,SimpleDateFormat.getInstance().parse(m_targetDate!!.text.toString()).time)
            this.startActivityForResult(i,REQ_PICK_DATE)
        }else if(m_addTransaction==v){
            val i=Intent(this,EditTransactionActivity::class.java)
            this.startActivityForResult(i, REQ_EDIT_TRANSACTION)

        }else if(m_ok==v){
            val file=StrictMoneyBookFile.getInstance()//StrictMoneyBookFile(MoneyBookConfig.CONFIG_FILE_NAME,MoneyBookConfig.TRANSACTION_FILE_NAME,20)
            //file.loadTransaction()
            file.addTransaction(m_dataList!!.toTypedArray())
            file.saveTransaction()
            this.finish()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(Activity.RESULT_OK== resultCode){

            when(requestCode){
                REQ_PICK_DATE->{
                    //取引時刻を受け取る
                    val targetDate=data!!.getLongExtra(PickDateActivity.TARGET_DATE_KEY,Date().time)
                    m_targetDate!!.text=SimpleDateFormat.getInstance().format(Date(targetDate))
                    for(t in m_dataList){
                        t.timeStamp=targetDate
                    }
                    return;
                }
                REQ_EDIT_TRANSACTION->{
//                    val obj=JSONObject(data!!.getStringExtra(EditTransactionActivity.TARGET_OBJ_KEY))
//                    val tran=MoneyBookTransaction(obj)
                    Log.i(this.javaClass.name,"Edit result is new or modify")
                    val tran=data!!.getSerializableExtra(EditTransactionActivity.TARGET_OBJ_KEY) as MoneyBookTransaction
                    tran.timeStamp=SimpleDateFormat.getInstance().parse(m_targetDate!!.text.toString()).time
                    Log.i(this.javaClass.name,String.format("Edit result is %s",tran.toString()))

                    //リストの更新
                    var insertFlag=true //新規か編集かはこの時点ではわからない
                    for(i in 0..m_dataList.size-1){
                        val o=m_dataList.get(i)
                        if(o.id==tran.id){  //idが一致したので編集

                            Log.i(javaClass.name,"It is modify")
                            m_dataList[i]=tran  //データを入れ替え
                            insertFlag=false
                            break
                        }
                    }
                    if(insertFlag) {    //新規
                        Log.i(javaClass.name,"It is new")
                        m_dataList.add(tran)  //リスト末尾に追加
                    }
                    m_transactionList!!.adapter=ArrayAdapter<MoneyBookTransaction>(this,android.R.layout.simple_list_item_1,m_dataList.toTypedArray())
                    //金額の合計を更新
                    var v=0
                    for(t in m_dataList){
                        v+=t.value
                    }
                    m_total!!.text=Integer.toString(v)
                    return;
                }
            }
        }else if(EditTransactionActivity.RESULT_DELETE==resultCode){
    //        val t=MoneyBookTransaction(JSONObject(data!!.getStringExtra(EditTransactionActivity.TARGET_OBJ_KEY)))
            Log.i(this.javaClass.name,"Edit result is delete")

            val t=intent.getSerializableExtra(EditTransactionActivity.TARGET_OBJ_KEY) as MoneyBookTransaction
            val file=StrictMoneyBookFile.getInstance()
            file.deleteTransaction(t)
            m_dataList.binarySearch (t,kotlin.Comparator { o1, o2 ->o1.id.compareTo(o2.id)  })
            m_transactionList!!.adapter=ArrayAdapter<MoneyBookTransaction>(this,android.R.layout.simple_list_item_1,m_dataList.toTypedArray())

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val i=Intent(this,EditTransactionActivity::class.java)
//        i.putExtra(EditTransactionActivity.TARGET_OBJ_KEY,m_dataList.get(position).toJSONObject().toString())
        i.putExtra(EditTransactionActivity.TARGET_OBJ_KEY,m_dataList.get(position))
        this.startActivityForResult(i, REQ_EDIT_TRANSACTION)
    }
}
