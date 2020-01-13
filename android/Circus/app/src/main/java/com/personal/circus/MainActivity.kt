package com.personal.circus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.personal.circus.AnalogCamera.CameraActivity
import com.personal.circus.Kancole.KancoreMenuActivity
import com.personal.circus.Moneybook.MoneyBookActivity
import com.personal.circus.Weather.LivedoorWeatherActivity
import com.personal.circus.Trainroute.*;

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.personal.circus. R.layout.activity_main)


        IOUtils.createDir(Config.IO_DIR)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //main.xmlの内容を読み込む
        val inflater = menuInflater
        inflater.inflate(com.personal.circus.R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            com.personal.circus.R.id.main_menu_game->{
                val i=Intent(this,KancoreMenuActivity::class.java)
                this.startActivity(i)

            }
            com.personal.circus.R.id.main_menu_moneybook->{
                val i=Intent(this, MoneyBookActivity::class.java)
                i.putExtra(MoneyBookActivity.FILE_TYPE_KEY,MoneyBookActivity.FILE_TYPE_ORIGIN)
                this.startActivity(i)

            }
            com.personal.circus.R.id.main_menu_camera->{
                val i=Intent(this, CameraActivity::class.java)
                this.startActivity(i)

            }
            com.personal.circus.R.id.main_menu_weather->{
                val i=Intent(this, LivedoorWeatherActivity::class.java)
                this.startActivity(i)

            }
            com.personal.circus.R.id.main_menu_trainroute->{
                val i=Intent(this, TrainRouteMainActivity::class.java)
                this.startActivity(i)

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
