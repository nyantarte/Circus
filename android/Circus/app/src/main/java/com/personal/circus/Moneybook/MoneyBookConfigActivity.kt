package com.personal.circus.Moneybook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.personal.circus.R

class MoneyBookConfigActivity : AppCompatActivity() {

    private var m_period:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_book_config)
        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)

        m_period=findViewById(R.id.MoneyBookConfigPeriod)

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
        return false
    }
}
