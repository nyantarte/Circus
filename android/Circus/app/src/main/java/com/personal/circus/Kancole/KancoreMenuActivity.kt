package com.personal.circus.Kancole

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.R
import android.content.Intent
import android.view.Menu
import android.view.MenuItem;
import com.personal.circus.SurfaceActivity


class KancoreMenuActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.personal.circus.R.layout.activity_kancore_menu)


        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)
        KancoreData.getInstance(assets)!!.loadState()



    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //main.xmlの内容を読み込む
        val inflater = menuInflater
        inflater.inflate(com.personal.circus.R.menu.kancore_menu, menu)
        return true
    }

    override fun onClick(v: View?) {
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        KancoreData.getInstance(assets)!!.saveState()

        when (item.getItemId()) {
            //アクションバーの戻るボタン
            android.R.id.home -> {
                //結果を呼び出し元アクティビティに返さない
                finish()
                return true
            }
            com.personal.circus.R.id.KancoreDock-> {
                val i = Intent(this, KancoreDockActivity::class.java)
                this.startActivity(i)

            }
            com.personal.circus.R.id.KancoreDungeon-> {
                val i = Intent(this, SurfaceActivity::class.java)
                i.putExtra(SurfaceActivity.GAME_TYPE,SurfaceActivity.GAME_KANCORE)
                this.startActivity(i)

            }
            com.personal.circus.R.id.KancoreRootBox->{
                val i = Intent(this, SurfaceActivity::class.java)
                i.putExtra(SurfaceActivity.GAME_TYPE,SurfaceActivity.GAME_KANCORE_ROOT_BOX)
                this.startActivity(i)

            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onStart() {
        super.onStart()
        for(c in KancoreData.getInstance(assets)!!.getPlayerCharaTbl()){
            c.Life=c.MaxLife
        }

    }
}
