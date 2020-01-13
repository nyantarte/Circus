package com.personal.circus.Trainroute

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.personal.circus.R

class TrainRouteMainActivity : AppCompatActivity(),View.OnClickListener {

    private var m_srcStation: TextView?=null
    private var m_dstStation:TextView?=null
    private var m_searchBtn:Button?=null
    private var m_searchResultList:ListView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_route_main)

        m_srcStation=findViewById(R.id.TrainRouteSrcStation)
        m_dstStation=findViewById(R.id.TrainRouteDstStation)

        m_searchBtn=findViewById(R.id.TrainRouteSearch)
        m_searchBtn!!.setOnClickListener(this)

        m_searchResultList=findViewById(R.id.TrainRouteList)
    }

    override fun onClick(v: View?) {
        val src=m_srcStation!!.text.toString()
        val dst=m_dstStation!!.text.toString()


//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
