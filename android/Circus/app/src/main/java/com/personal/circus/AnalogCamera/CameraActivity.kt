package com.personal.circus.AnalogCamera

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.media.Image
import android.support.v4.graphics.BitmapCompat
import android.util.Log
import android.view.*
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_camera.*
import android.R
import android.content.pm.ActivityInfo
import android.graphics.drawable.GradientDrawable
import android.view.SurfaceHolder
import com.personal.circus.ScreenMode


class CameraActivity : AppCompatActivity() ,SurfaceHolder.Callback,Camera.PreviewCallback{


    private val REQ_CAMERA=1
    private var m_imageView:ImageView?=null
    private var m_camera: Camera?=null
    private var m_texture:Bitmap?=null
    private var m_surfaceHolder:SurfaceHolder?=null
    private var m_surfaceView:SurfaceView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.personal.circus.R.layout.activity_camera)
        val bar = this.supportActionBar
        bar!!.setDisplayHomeAsUpEnabled(true)
        //bar.hide()

        //this.requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        m_surfaceView=findViewById(com.personal.circus.R.id.CameraSurface)
        m_surfaceHolder=m_surfaceView!!.holder
        m_surfaceHolder!!.addCallback(this)
        m_surfaceHolder!!.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //main.xmlの内容を読み込む
        val inflater = menuInflater
        inflater.inflate(com.personal.circus.R.menu.camera_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            //アクションバーの戻るボタン
            android.R.id.home -> {
                //結果を呼び出し元アクティビティに返さない
                finish()
                return true
            }
            com.personal.circus.R.id.camera_menu_take_picture->{
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        m_camera=Camera.open()
        m_camera!!.setPreviewDisplay(m_surfaceHolder)
        m_camera!!.setPreviewCallback(this)

    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        m_camera!!.stopPreview()
        val params = m_camera!!.getParameters()

//        params.setPreviewFormat(format)
//        params.setPreviewSize(width, height)

//        m_camera!!.setParameters(params)
        m_camera!!.setDisplayOrientation(90)
        m_camera!!.startPreview()
    }
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        m_camera!!.stopPreview()
        m_camera!!.release()
    }

    override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
//        Log.i("","test")
    }
}
