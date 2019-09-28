package com.personal.circus.Moneybook

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.personal.circus.Kancole.Charactor
import com.personal.circus.R
import org.json.JSONObject
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import java.lang.Float.parseFloat;

/**
 * @brief 取引結果を編集するActivity
 */
class EditTransactionActivity : AppCompatActivity() ,View.OnClickListener{

    companion object{
        @JvmStatic
        val TARGET_OBJ_KEY="TargetObj"          /*!編集対象の取引を呼び出し元から指定するときにintentに渡す*/
        val RESULT_DELETE=Activity.RESULT_OK-1  /*!編集結果が削除の場合、呼び出し元に通知する*/
    }
    private var m_isIncome:RadioButton?=null
    private var m_isPayment:RadioButton?=null
    private var m_payMethod:Spinner?=null
    private var m_usageList:Spinner?=null
    private var m_value:TextView?= null
    private var m_note: EditText?=null
    private var m_ok:Button?=null
    private var m_clear:Button?= null
    private var m_bs:Button?=null
    private var m_div:Button?= null
    private var m_mul:Button?=null
    private var m_plus:Button?=null
    private var m_minus:Button?=null
    private var m_equal:Button?=null
    private var m_delete:Button?=null
    private var m_target:MoneyBookTransaction?=null
    private var m_file:IMoneyBookFile?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaction)
        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)

        //Activity上のコントロールを取得、イベントプロシージャを設定
        m_isIncome = findViewById(com.personal.circus.R.id.EditTransactionIncome)
        m_isIncome!!.setOnClickListener(this)

        m_isPayment = findViewById(R.id.EditTransactionPayment)
        m_isPayment!!.setOnClickListener(this)

        m_payMethod = findViewById(R.id.EditTransactionPayMethod)
        m_usageList = findViewById(R.id.EditTransactionUsage)

        m_value = findViewById(R.id.EditTransactionValue)
        m_note = findViewById(R.id.EditTransactionNote)
        m_ok = findViewById(R.id.EditTransactionOk)
        m_ok!!.setOnClickListener(this)

        m_div=findViewById(R.id.EditTransactionDiv)
        m_div!!.setOnClickListener(this)
        m_mul=findViewById(R.id.EditTransactionMul)
        m_mul!!.setOnClickListener(this)
        m_plus=findViewById(R.id.EditTransactionPlus)
        m_plus!!.setOnClickListener(this)
        m_minus=findViewById(R.id.EditTransactionMinus)
        m_minus!!.setOnClickListener(this)
        m_equal=findViewById(R.id.EditTransactionEqual)
        m_equal!!.setOnClickListener(this)
        m_clear=findViewById(R.id.EditTransactionClear)
        m_clear!!.setOnClickListener(this)
        m_bs=findViewById(R.id.EditTransactionBS)
        m_bs!!.setOnClickListener(this)

        var b=findViewById<Button>(R.id.EditTransaction0)
        b.setOnClickListener(this)
        b=findViewById<Button>(R.id.EditTransaction1)
        b.setOnClickListener(this)
        b=findViewById<Button>(R.id.EditTransaction2)
        b.setOnClickListener(this)
        b=findViewById<Button>(R.id.EditTransaction3)
        b.setOnClickListener(this)
        b=findViewById<Button>(R.id.EditTransaction4)
        b.setOnClickListener(this)
        b=findViewById<Button>(R.id.EditTransaction5)
        b.setOnClickListener(this)
        b=findViewById<Button>(R.id.EditTransaction6)
        b.setOnClickListener(this)
        b=findViewById<Button>(R.id.EditTransaction7)
        b.setOnClickListener(this)
        b=findViewById<Button>(R.id.EditTransaction8)
        b.setOnClickListener(this)
        b=findViewById<Button>(R.id.EditTransaction9)
        b.setOnClickListener(this)
        b=findViewById(R.id.EditTransactionDot)
        b.setOnClickListener(this)

        val file = StrictMoneyBookFile.getInstance()//StrictMoneyBookFile(MoneyBookConfig.CONFIG_FILE_NAME, MoneyBookConfig.TRANSACTION_FILE_NAME, 20)
        m_file=file
        this.m_payMethod!!.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, file.paymentMethodList.toTypedArray())
        this.m_usageList!!.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, file.usageList.toTypedArray())
        //呼び出し元から編集対象が指定されていないなら新規作成
        if (intent.hasExtra(TARGET_OBJ_KEY)) {

            //指定されているので、既存の取引を編集
/*            val json = JSONObject(intent.getStringExtra(TARGET_OBJ_KEY))
            val tmp = MoneyBookTransaction(json)
*/

            val tmp=intent.getSerializableExtra(TARGET_OBJ_KEY) as MoneyBookTransaction
            m_target=tmp
            //収入、支出のラジオボタンを設定
            if (tmp.value < 0) {    //支出
                m_isPayment!!.isChecked = true
                m_isIncome!!.isChecked = false
            } else {    //収入

                m_isPayment!!.isChecked = false
                m_isIncome!!.isChecked = true

            }



            this.m_payMethod!!.setSelection(file.paymentMethodList.indexOf(tmp.payMethod))
            this.m_usageList!!.setSelection(file.usageList.indexOf(tmp.usage))
            this.m_value!!.setText(Integer.toString(Math.abs(tmp.value)))
            this.m_note!!.setText(tmp.note)

        }
        m_delete=findViewById(R.id.EditTransactionDelete)
        m_delete!!.setOnClickListener(this)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            //アクションバーの戻るボタン
            android.R.id.home -> {
                //結果を呼び出し元アクティビティに返さない
                setResult(Activity.RESULT_CANCELED)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        if(m_isIncome==v){
            m_isIncome!!.isChecked=true
            m_isPayment!!.isChecked=false
        }else if(m_isPayment==v){
            m_isPayment!!.isChecked=true
            m_isIncome!!.isChecked=false
        }else if(m_clear==v){
            m_value!!.text="0"
        }else if(m_bs==v){
            if(0 < m_value!!.text.length){
                m_value!!.text=m_value!!.text.substring(0,m_value!!.text.length-1)
            }
        }
        else if(m_equal==v){
            var tmp=""
            val queue=ArrayList<String>()
            for(c in m_value!!.text.toString()) {
                if ('*' == c || '/' == c || '-' == c || '+' == c) {
                    if (0 < tmp.length) {
                        queue.add(tmp)
                        tmp = ""
                    }
                    queue.add(Character.toString(c))
                }else{
                    tmp=tmp+c
                }
            }
            if(0 < tmp.length){
                queue.add(tmp)
            }
            Log.i(this.javaClass.name,String.format("Queue size %d",queue.size))
            var i=0
            var v= parseFloat(queue[i++])
            while(i < queue.size-1)
            {
                if("+".equals(queue[i])){
                    v+= parseFloat(queue[++i])
                    ++i
                }else if("-".equals(queue[i])){
                    v-= parseFloat(queue[++i])
                    ++i

                }else if("*".equals(queue[i])){
                    v*= parseFloat(queue[++i])
                    ++i

                }else if("/".equals(queue[i])){
                    v/= parseFloat(queue[++i])
                    ++i

                }
            }
            m_value!!.text=v.toString()
        }
        else if(m_ok==v){
            m_equal!!.callOnClick()
            var res=MoneyBookTransaction()
            if(intent.hasExtra(TARGET_OBJ_KEY)) {
//                val json = JSONObject(intent.getStringExtra(TARGET_OBJ_KEY))
//                val tmp = MoneyBookTransaction(json)

//                res.id = tmp.id
//                Log.i("",tmp.id.toString())
                Log.i(this.javaClass.name,"Edit mode modify")
                res=m_target!!
            }else{
                res.id=(Date().time)
                Log.i(this.javaClass.name,"Edit mode new")
            }
            res.value=parseFloat(m_value!!.text.toString()).toInt()
            if(m_isPayment!!.isChecked){
                res.value=-res.value
            }

            res.payMethod=m_payMethod!!.selectedItem.toString()
            res.usage=m_usageList!!.selectedItem.toString()
            res.note=m_note!!.text.toString()
            Log.i(javaClass.name,String.format("id=%d,value=%d,payMethod=%s,usage=%s,note=%s",res.id,res.value,res.payMethod,res.usage,res.note))
            val i=Intent()
//            i.putExtra(TARGET_OBJ_KEY,res.toJSONObject().toString())

            i.putExtra(TARGET_OBJ_KEY,res as Serializable)
            setResult(Activity.RESULT_OK,i)
            finish()

        }else if(m_delete==v){
            if(intent.hasExtra(EditTransactionActivity.TARGET_OBJ_KEY)){
                //val t=MoneyBookTransaction(JSONObject(intent.getStringExtra(EditTransactionActivity.TARGET_OBJ_KEY)))

                val i=Intent()
//                i.putExtra(EditTransactionActivity.TARGET_OBJ_KEY,intent.getStringExtra(EditTransactionActivity.TARGET_OBJ_KEY))
                i.putExtra(EditTransactionActivity.TARGET_OBJ_KEY,m_target)
//                m_file!!.deleteTransaction(m_target!!)
                this.setResult(RESULT_DELETE,i)
                this.finish()
            }
        }
        else {
            val b = v as Button
            if ("0".equals(m_value!!.text)) {
                m_value!!.text = b.text.toString()
            } else {
                m_value!!.text = m_value!!.text.toString() + b.text.toString()
            }
        }
    }
}
