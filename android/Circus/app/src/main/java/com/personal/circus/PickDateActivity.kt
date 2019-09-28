package com.personal.circus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CalendarView
import java.util.*
import android.widget.Button
import android.R
import android.app.Activity
import android.widget.TextView
import android.content.Intent
import android.util.Log
import java.text.SimpleDateFormat


class PickDateActivity : AppCompatActivity() ,CalendarView.OnDateChangeListener,View.OnClickListener{

    companion object{
        @JvmStatic
        val TARGET_DATE_KEY="TargetDate";
    }

    private var m_calendar:CalendarView?=null;
    private var m_hourUp1:Button?=null
    private var m_hourUp2: Button?=null
    private var m_minUp1:Button?=null
    private var m_minUp2:Button?=null
    private var m_hourDown1:Button?=null
    private var m_hourDown2: Button?=null
    private var m_minDown1:Button?=null
    private var m_minDown2:Button?=null

    private var m_hour1Text:TextView?=null
    private var m_hour2Text:TextView?=null
    private var m_min1Text:TextView?=null
    private var m_min2Text:TextView?=null
    private var m_ok:Button?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.personal.circus.R.layout.activity_pick_date)


        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)

        var current = Date()
        if (intent.hasExtra(TARGET_DATE_KEY)) {
            //            Log.i(this.getClass().getName(),getIntent().getStringExtra(TARGET_DATE_KEY));
            current.time=intent.getLongExtra(TARGET_DATE_KEY,current.time);

        }
        m_calendar=findViewById<CalendarView>(com.personal.circus.R.id.PickDateCalendar);
        m_calendar!!.setDate(current.getTime());


        m_calendar!!.setOnDateChangeListener(this)

        m_hourUp1=findViewById(com.personal.circus.R.id.PickDateHourUp1)
        m_hourUp1!!.setOnClickListener(this)
        m_hourUp2=findViewById(com.personal.circus.R.id.PickDateHourUp2)
        m_hourUp2!!.setOnClickListener(this)
        m_minUp1=findViewById(com.personal.circus.R.id.PickDateMinUp1)
        m_minUp1!!.setOnClickListener(this)
        m_minUp2=findViewById(com.personal.circus.R.id.PIckDateMinUp2)
        m_minUp2!!.setOnClickListener(this)

        m_hourDown1=findViewById(com.personal.circus.R.id.PickDateHourDown1)
        m_hourDown1!!.setOnClickListener(this)
        m_hourDown2=findViewById(com.personal.circus.R.id.PickDateHourDown2)
        m_hourDown2!!.setOnClickListener(this)
        m_minDown1=findViewById(com.personal.circus.R.id.PickDateMinDown1)
        m_minDown1!!.setOnClickListener(this)
        m_minDown2=findViewById(com.personal.circus.R.id.PickDateMinDown2)
        m_minDown2!!.setOnClickListener(this)

        val cal = Calendar.getInstance()
        cal.time = current

        m_hour1Text=findViewById(com.personal.circus.R.id.PickDateHour1Text)
        m_hour1Text!!.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)/10));

        m_hour2Text=findViewById(com.personal.circus.R.id.PickDateHour2Text)
        m_hour2Text!!.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)%10));

        m_min1Text=findViewById(com.personal.circus.R.id.PickDateMin1Text)
        m_min1Text!!.setText(Integer.toString(cal.get(Calendar.MINUTE)/10));

        m_min2Text=findViewById(com.personal.circus.R.id.PickDateMin2Text)
        m_min2Text!!.setText(Integer.toString(cal.get(Calendar.MINUTE)%10));

        m_ok=findViewById(com.personal.circus.R.id.PickDateOK)
        m_ok!!.setOnClickListener(this)

    }

    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
        m_calendar!!.setDate(Date(year - 1900, month, dayOfMonth).time)
     //   Log.i(self.getClass().getName(), String.format("%d/%d/%d has selected", year, month + 1, dayOfMonth))
    }

    override fun onClick(v: View?) {
        if(m_hourUp1==v){
            var i = Integer.valueOf(m_hour1Text!!.getText().toString())
            if (1 >= i) {
                ++i
            }
            m_hour1Text!!.setText(Integer.toString(i))

        }else if(m_hourUp2==v){
            var i = Integer.valueOf(m_hour2Text!!.getText().toString())
            if (9 > i) {
                ++i
            }
            m_hour2Text!!.setText(Integer.toString(i))

        }else if(m_minUp1==v){
            var i = Integer.valueOf(m_min1Text!!.getText().toString())
            if (5 > i) {
                ++i
            }
            m_min1Text!!.setText(Integer.toString(i))

        }else if(m_minUp2==v){
            var i = Integer.valueOf(m_min2Text!!.getText().toString())
            if (9 > i) {
                ++i
            }
            m_min2Text!!.setText(Integer.toString(i))

        }else if(m_hourDown1==v){
            var i = Integer.valueOf(m_hour1Text!!.getText().toString())
            if (0 < i) {
                --i
            }
            m_hour1Text!!.setText(Integer.toString(i))

        }else if(m_hourDown2==v){
            var i = Integer.valueOf(m_hour2Text!!.getText().toString())
            if (0 < i) {
                --i
            }
            m_hour2Text!!.setText(Integer.toString(i))

        }else if(m_minDown1==v){
            var i = Integer.valueOf(m_min1Text!!.getText().toString())
            if (0 < i) {
                --i
            }
            m_min1Text!!.setText(Integer.toString(i))

        }else if(m_minDown2==v){
            var i = Integer.valueOf(m_min2Text!!.getText().toString())
            if (0 < i) {
                --i
            }
            m_min2Text!!.setText(Integer.toString(i))

        }else if(m_ok==v){
            val i = Intent()


            val target = Date(m_calendar!!.getDate())
            //Log.i(PickDateActivity::class.java!!.getName(), MoneyBook.s_timeStampFormat.format(target))


            val h1 = Integer.valueOf(m_hour1Text!!.getText().toString())
            val h2 = Integer.valueOf(m_hour2Text!!.getText().toString())

            target.setHours(h1 * 10 + h2)

            val m1 = Integer.valueOf(m_min1Text!!.getText().toString())
            val m2 = Integer.valueOf(m_min2Text!!.getText().toString())

            target.setMinutes(m1 * 10 + m2)

            i.putExtra(PickDateActivity.TARGET_DATE_KEY, target.time)

            this.setResult(Activity.RESULT_OK, i)
            finish()

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
}
