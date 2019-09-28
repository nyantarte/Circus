package com.personal.circus.Moneybook

import android.util.Log
import com.personal.circus.Config
import com.personal.circus.IOUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import java.lang.*;
import java.lang.Integer.parseInt
import java.lang.Long.parseLong
import java.text.SimpleDateFormat

/**
 * @brief ファイル形式の家計簿データを管理するクラス
 */
class StrictMoneyBookFile:IMoneyBookFile{

    private val m_dateFormat=SimpleDateFormat("yyyy/MM/dd HH:mm") /*!ファイル中で使用されている日付形式*/
    private val m_usageList=ArrayList<String>();    /*!用途一覧*/
    private val m_data = ArrayList<MoneyBookTransaction>()  /*!家計簿データ*/



    override val usageList:List<String>
    get(){
        return m_usageList;
    }

    private val m_payMethodList=ArrayList<String>();
    override val paymentMethodList:List<String>
    get(){
        return m_payMethodList;
    }
    private var m_transactionFile=""/*!家計簿データを読み出すファイル*/
    private var m_configFile=""/*!設定を読み出すファイル*/
    private var m_period=1/*!締め日*/

    override val period:Int
    get(){
        return m_period
    }

    companion object {
        @JvmStatic
        var m_obj: StrictMoneyBookFile? = null/*!家計簿起動時に初期化される*/

        /**
         * @brief 家計簿オブジェクトへのインスタンスを返す
         * @return 家計簿、設定データが読み込まれたインスタンスを返す
         */
        fun getInstance(): StrictMoneyBookFile {
            if (null == m_obj) {    //初期化されていないなら初期化を行う
                m_obj = StrictMoneyBookFile(MoneyBookConfig.CONFIG_FILE_NAME, MoneyBookConfig.TRANSACTION_FILE_NAME, 20)
                m_obj!!.loadTransaction()   //家計簿データをロード
            }
            return m_obj!!

        }
    }

    /**
     * @brief コンストラクタ
     * @param configFile 設定を読み出すファイル
     * @param transFile 家計簿データを読み出すファイル
     */
    private constructor(configFile:String,transFile:String,per:Int) {
        m_configFile=configFile
        loadConfigFile(configFile)
        m_transactionFile = transFile
        m_period=per

    }

    override fun getTransactions(beginDate: Date, endDate: Date, paymethodFlags: HashMap<String, Boolean>, usageList: HashMap<String, Boolean>):
    List<MoneyBookTransaction>{
        Collections.sort(m_data,kotlin.Comparator { o1, o2 ->o1.timeStamp.compareTo(o2.timeStamp)  })
        val result=ArrayList<MoneyBookTransaction>()
        Log.i(this.javaClass.name,beginDate.time.toString())
        Log.i(this.javaClass.name,endDate.time.toString())
        for(o in m_data){
            Log.i(this.javaClass.name,o.toString())
            Log.i(this.javaClass.name,o.timeStamp.toString())
            if(beginDate.time <=o.timeStamp && endDate.time>=o.timeStamp && paymethodFlags.get(o.payMethod)!! && usageList.get(o.usage)!!){
                result.add(o)
            }
        }
        return result;
    }

    private fun buildTransactionFromCSV(params:List<String>):MoneyBookTransaction{

        val v=MoneyBookTransaction()
        v.id=parseLong(params[0])
        if(0L==v.id){
            v.id=Date().time
        }
        v.timeStamp=m_dateFormat.parse(params[1]).time
        v.payMethod=params[2]
        v.usage=params[3]
        v.value= parseInt(params[4])
        v.note=params[5]
        return v
    }

    private fun loadConfigFile(file:String){
        try{
            val data= JSONObject(IOUtils.loadPlainText(file,null))
            m_period=data.getInt("period")
            val usages=data.getJSONArray("usageList")
            for(i in 0.. usages.length()-1){
                m_usageList.add(usages.getString(i))
            }

            val paymethods=data.getJSONArray("payMethodList")
            for(i in 0..paymethods.length()-1){
                m_payMethodList.add(paymethods.getString(i))
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    override fun saveConfigFile(){
        try{
            val o=JSONObject()
            o.put("period",m_period)
            val usage=JSONArray()
            for(s in m_usageList){
                usage.put(s)
            }
            o.put("usageList",usage)
            val payMethod=JSONArray()
            for(s in m_payMethodList){
                payMethod.put(s)
            }
            o.put("payMethodList",payMethod)

            val bw=IOUtils.createFile(m_configFile)
            bw.write(o.toString())
            bw.close()
            Log.i(this.javaClass.name,String.format("Usage item nums %d",m_usageList.size))
            Log.i(this.javaClass.name,String.format("Pay method item nums %d",m_payMethodList.size))
            Log.i(this.javaClass.name,"Save result true")
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    private fun loadTransaction(){
        m_data.clear()
        try{
            m_data.ensureCapacity(MoneyBookConfig.TRANSACTION_MAX)
            val br =BufferedReader(InputStreamReader(IOUtils.getInputStreamFromStorage(m_transactionFile)));
/*            var line=br.readLine();
            var params=line.split(",")
            val ver=Integer.parseInt(params[1])
*/


            while(true){
                val line=br.readLine()
                if(null==line)
                    break;
                val params=line.split(",")

                m_data.add(buildTransactionFromCSV(params))

            }

            Collections.sort(m_data,kotlin.Comparator { o1, o2 ->o1.timeStamp.compareTo(o2.timeStamp)  })
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    fun saveTransaction(){
        try {
            val bw = IOUtils.createFile(m_transactionFile)
            for(o in m_data){
                bw.write(String.format("%d,%s,%s,%s,%d,%s",o.id,m_dateFormat.format(Date(o.timeStamp)),o.payMethod,o.usage,o.value,o.note))
                bw.newLine()
            }
            bw.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun addTransaction(data:Array<MoneyBookTransaction?>){
        Collections.sort(m_data,kotlin.Comparator { o1, o2 ->o1.id.compareTo(o2.id)  })
        for(i in 0..data.size-1){
            val hitIdx=m_data.binarySearch(data[i],kotlin.Comparator { o1, o2 ->o1!!.id.compareTo(o2!!.id)  })
            if(0 > hitIdx)
                continue
            m_data[hitIdx]=data[i]!!
            data[i]=null
   //         data[i].id=0
        }
        for(o in data){
            if(null==o)
                continue
            m_data.add(o!!)
        }


    }
    override fun deleteTransaction(t:MoneyBookTransaction) {
        Collections.sort(m_data, kotlin.Comparator { o1, o2 -> o1.id.compareTo(o2.id) })
        val hitIdx = m_data.binarySearch(t, kotlin.Comparator { o1, o2 -> o1.id.compareTo(o2.id) })
        if (0 > hitIdx) {
            Log.i(this.javaClass.name,String.format("Target id transaction has not found.id=%d",t.id))
            return;
        }
        m_data.removeAt(hitIdx)
        Log.i(this.javaClass.name,String.format("Target id transaction removed.id=%d",t.id))
    }




}