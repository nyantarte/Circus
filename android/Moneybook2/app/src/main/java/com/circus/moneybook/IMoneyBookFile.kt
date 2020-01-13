package com.circus.moneybook;
import java.util.*

interface IMoneyBookFile {

    val usageList:List<String>;
    val paymentMethodList:List<String>;

    val period:Int
    fun getTransactions(beginDate: Date,endDate:Date,paymethodFlags:HashMap<String,Boolean>,usageList:HashMap<String,Boolean>):
            List<MoneyBookTransaction>
    fun deleteTransaction(t:MoneyBookTransaction)

    fun saveConfigFile()
}