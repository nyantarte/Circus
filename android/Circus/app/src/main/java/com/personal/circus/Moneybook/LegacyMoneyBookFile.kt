package com.personal.circus.Moneybook

import com.personal.circus.IOUtils
import java.util.*
import kotlin.collections.ArrayList
import android.R.attr.data
import android.util.Log
import java.io.BufferedReader
import java.io.StringReader
import java.lang.Exception
import java.text.SimpleDateFormat
import java.lang.Boolean.parseBoolean

class LegacyMoneyBookFile:IMoneyBookFile{

    private val m_usageList=ArrayList<String>()
    override val usageList:List<String>
    get(){
        return m_usageList
    }
    private val m_payMethodList=ArrayList<String>()
    override val paymentMethodList:List<String>
    get(){
        return m_payMethodList
    }


    private var m_period=1
    override val period:Int
    get(){
        return m_period
    }

    private val m_transactionList=ArrayList<MoneyBookTransaction>()

    private val m_dateFormat= SimpleDateFormat("yyyy/M/d H:m")

    constructor(file:String){
        loadFile(file)
    }
    override fun getTransactions(beginDate: Date, endDate: Date, paymethodFlags: HashMap<String, Boolean>, usageList: HashMap<String, Boolean>):
            List<MoneyBookTransaction>
    {
        val l=ArrayList<MoneyBookTransaction>()
        return l
    }


    override fun saveConfigFile(){

    }


    fun saveDataToStrictFile(configFile:String,transFile:String){
        val tmp=StrictMoneyBookFile.getInstance()//StrictMoneyBookFile(configFile,transFile,period)
        val usages=tmp.usageList as ArrayList<String>
        for(us in m_usageList){
            if(-1==usages.indexOf(us)){
                usages.add(us)
            }
        }
//        usages.addAll(m_usageList)
        val payMethods=tmp.paymentMethodList as ArrayList<String>
        for(pm in m_payMethodList){
            if(-1==payMethods.indexOf(pm)){
                payMethods.add(pm)
            }
        }
//        payMethods.addAll(m_payMethodList)
        tmp.saveConfigFile()

        tmp.addTransaction(m_transactionList.toTypedArray())
        tmp.saveTransaction()
    }

    fun loadFile(file:String){
        try{
            val data= IOUtils.loadPlainText(file, null);
            val r = BufferedReader(StringReader(data))
            var line = r.readLine()
            var verNo=0
            if (0 == line.length) {
                verNo = 0
            } else {
                verNo =
                    Integer.parseInt(line.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0])
            }
            Log.i(this.javaClass.name,String.format("Legacy file version %d.",verNo))
            line = r.readLine()
            m_period=(Integer.parseInt(line.split(",")[0]))
            Log.i(this.javaClass.name,String.format("Period %d.",m_period))
            line = r.readLine()
            for (param in line.split(",")) {
                m_usageList.add(param)
            }
            line = r.readLine()
            for (param in line.split(",")) {
                m_payMethodList.add(param)
                Log.i(this.javaClass.name,String.format("Pay method %s.",param))
            }
            var idStart=Date().time
            while (true) {
                line=r.readLine()
                if(null==line){
                    break;
                }
                val params = line.split(",")
                val t = MoneyBookTransaction()
                t.timeStamp = m_dateFormat.parse(params[0]).time
                t.payMethod = params[1]
                t.value = Integer.parseInt(params[2])
                t.note = params[3]
                t.usage = params[4]
                t.id=idStart++
                if(0 < verNo){
                    if(!parseBoolean(params[5])){
                        t.value=-t.value
                    }

                }
/*
                if (0 < verNo) {
                    t.isIncome = java.lang.Boolean.parseBoolean(params[5])
                }
*/
                m_transactionList.add(t)

                if (-1 == m_usageList.indexOf(t.usage)) {
                    m_usageList.add(t.usage)
                }
                if (-1 == m_payMethodList.indexOf(t.payMethod)) {
                    m_payMethodList.add(t.payMethod)
                }
            }


        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun deleteTransaction(t: MoneyBookTransaction) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}