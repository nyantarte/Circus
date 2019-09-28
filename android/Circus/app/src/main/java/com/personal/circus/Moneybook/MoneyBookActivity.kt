package com.personal.circus.Moneybook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.personal.circus.IOUtils
import com.personal.circus.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


/**
 * @brief 家計簿
 */
class MoneyBookActivity : AppCompatActivity() {

    private var m_beginDate=Date().time /*!集計開始日付*/
    private var m_endDate=Date().time   /*!集計終了日付*/
    private var m_file:IMoneyBookFile?=null/*!家計簿データを管理するオブジェクト*/
    private var m_dateRange:TextView?=null
    private var m_value:TextView?=null
    companion object{
        @JvmStatic
        val FILE_TYPE_KEY="FileType"
        @JvmStatic
        val FILE_TYPE_ORIGIN=0
        val FILE_TYPE_LEGACY=1

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_book)
        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)

        IOUtils.createDir(MoneyBookConfig.IO_DIR)
        val ft=intent.getIntExtra(FILE_TYPE_KEY, FILE_TYPE_ORIGIN)
        when(ft){
            FILE_TYPE_ORIGIN->{
                m_file=StrictMoneyBookFile.getInstance()//StrictMoneyBookFile(MoneyBookConfig.CONFIG_FILE_NAME, MoneyBookConfig.TRANSACTION_FILE_NAME,20)
            }
        }
        buildDateRange(m_file!!.period)


        m_dateRange=findViewById<TextView>(R.id.MoneyBookDateRange)
        val fmt=SimpleDateFormat.getDateInstance()
        m_dateRange!!.setText(String.format("%s - %s",fmt.format(Date(m_beginDate)),fmt.format(Date(m_endDate))))

        m_value=findViewById(R.id.MoneyBookValue)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //main.xmlの内容を読み込む
        val inflater = menuInflater
        inflater.inflate(R.menu.money_book_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.AddTransaction ->{
                val i=Intent(this, EditTransactionListActivity::class.java)
                startActivity(i)
            }
            R.id.moneybook_search ->{
                val i=Intent(this, SearchTransactionActivity::class.java)
                i.putExtra(SearchTransactionActivity.DEFAULT_BEGIN_DATE_KEY,m_beginDate)
                i.putExtra(SearchTransactionActivity.DEFAULT_END_DATE_KEY,m_endDate)
                startActivity(i)

            }
            R.id.ImportLegacyFile->{
                val leagcyFile=LegacyMoneyBookFile(MoneyBookConfig.LAGACY_FILE_NAME)
                leagcyFile.saveDataToStrictFile(MoneyBookConfig.CONFIG_FILE_NAME,MoneyBookConfig.TRANSACTION_FILE_NAME)
                //m_file=StrictMoneyBookFile(MoneyBookConfig.CONFIG_FILE_NAME,MoneyBookConfig.TRANSACTION_FILE_NAME,20)
            }

            R.id.EditUsage->{
                val i=Intent(this,EditUsageActivity::class.java)
                startActivity(i)
            }
            //アクションバーの戻るボタン
            android.R.id.home -> {
                //結果を呼び出し元アクティビティに返さない
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private  fun buildDateRange(period:Int){
        var c = Calendar.getInstance()
        c.time = Date()
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)

        if (period > c.get(Calendar.DAY_OF_MONTH)) {
            c.add(Calendar.MONTH, -1)
            c.set(Calendar.DAY_OF_MONTH, period)
            m_beginDate = com.personal.circus.DateUtils.adjustHolyday(c).getTime().time
        } else {
            c.set(Calendar.DAY_OF_MONTH, period)
            m_beginDate = com.personal.circus.DateUtils.adjustHolyday(c).getTime().time
        }
        c.add(Calendar.MONTH, 1)
        c.set(Calendar.DAY_OF_MONTH, period)
        c = com.personal.circus.DateUtils.adjustHolyday(c)
        c.add(Calendar.DAY_OF_MONTH, -1)
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)
        m_endDate = c.time.time
    }

    override fun onResume() {
        super.onResume()

        val pmTbl=HashMap<String,Boolean>()
        for(s in m_file!!.paymentMethodList){
            pmTbl.put(s,true)
        }

        val usTbl=HashMap<String,Boolean>()
        for(s in m_file!!.usageList){
            usTbl.put(s,true)
        }
        val l=m_file!!.getTransactions(Date(m_beginDate),Date(m_endDate),pmTbl,usTbl)

        var inCome=0
        var payment=0
        for(t in l){
            if(0 < t.value){
                inCome+=t.value
            }else{
                payment+=Math.abs(t.value)
            }

        }
        m_value!!.text=String.format("収入 %d  支出 %d",inCome,payment)

    }

}
