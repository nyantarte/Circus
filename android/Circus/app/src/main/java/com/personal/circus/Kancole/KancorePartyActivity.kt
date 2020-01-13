package com.personal.circus.Kancole

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.personal.circus.R
import android.view.*
import android.widget.Button

class KancorePartyActivity : AppCompatActivity(),View.OnClickListener {

    private var m_slotBtns=Array<Button?>(6,{i->null})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kancore_party)

        m_slotBtns[0]=findViewById(R.id.KancorePartySlot1)
        m_slotBtns[0]!!.setOnClickListener(this)
        m_slotBtns[1]=findViewById(R.id.KancorePartySlot2)
        m_slotBtns[1]!!.setOnClickListener(this)
        m_slotBtns[2]=findViewById(R.id.KancorePartySlot3)
        m_slotBtns[2]!!.setOnClickListener(this)
        m_slotBtns[3]=findViewById(R.id.KancorePartySlot4)
        m_slotBtns[3]!!.setOnClickListener(this)
        m_slotBtns[4]=findViewById(R.id.KancorePartySlot5)
        m_slotBtns[4]!!.setOnClickListener(this)
        m_slotBtns[5]=findViewById(R.id.KancorePartySlot6)
        m_slotBtns[5]!!.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        for(idx in 0..5) {
            if(m_slotBtns[idx]==v) {
                val i = Intent(this, KancoreCharaNotInPartyActivity::class.java)
                i.putExtra(KancoreCharaNotInPartyActivity.CHARA_PICK_TARGET_KEY, idx)
                this.startActivity(i)
                break
            }

        }
    }

    override fun onStart() {
        super.onStart()

        for(i in 0..5){
            var c = KancoreData.getInstance(assets)!!.getPlayerFleet().members[i]
            if (null != c) {
                m_slotBtns[i]!!.text = c.toString()
            } else {
                m_slotBtns[i]!!.text = "None"
            }
        }
    }
}
