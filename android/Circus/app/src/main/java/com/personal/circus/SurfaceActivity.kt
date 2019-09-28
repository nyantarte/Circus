package com.personal.circus

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import java.util.*
import kotlin.collections.HashMap
import android.R
import android.graphics.Color
import android.util.Log
import com.personal.circus.Kancole.DungeonState
import com.personal.circus.Kancole.RootState

class SurfaceActivity : AppCompatActivity() ,IGameSystem,Runnable,SurfaceHolder.Callback {
    @Volatile
    private var m_isRunning = false
    private var m_thread: Thread? = null
    private var m_surfaceHolder: SurfaceHolder? = null
    private val m_stateStack = Stack<IGameState>()
    private val m_renderer=(CanvasRenderer() as IRenderer)
    private var m_rand: Random = Random();
    private var m_screenMode: ScreenMode? = null

    companion object{
        @JvmStatic
        val GAME_TYPE="GameType"
        val GAME_KANCORE="Kancore"
        val GAME_KANCORE_ROOT_BOX="KancoreRootBox"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 19) {
            val decor = this.window.decorView
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContentView(com.personal.circus.R.layout.activity_surface)

        //アクションバーを非表示
        val ab = supportActionBar
        ab!!.hide()


        //乱数器を生成
        m_rand.setSeed(System.currentTimeMillis())

        //SurfaceViewの初期化
        val sv = findViewById<SurfaceView>(com.personal.circus.R.id.GameSurface)

        m_surfaceHolder = sv.holder
        m_surfaceHolder?.addCallback(this)



    }



    /**
     *
     * @return javaデフォルトのRandオブジェクトを返す
     */
    override fun getRand(): Random {
        return m_rand
    }

    override fun getScreenMode(): ScreenMode {
        return m_screenMode!!
    }

    override fun getStateStack(): Stack<IGameState> {
        return m_stateStack
    }
    override fun onPause() {
        super.onPause()
        m_isRunning = false
        if(null!=m_thread) {
            try {
                m_thread!!.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun onResume() {
        super.onResume()
//        m_isRunning = true
//                m_thread!!.start();
        if (Build.VERSION.SDK_INT >= 19) {
            val decor = this.window.decorView
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {}
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        m_screenMode = ScreenMode.HD_PLUS_VERTICAL
        m_screenMode!!.setScreenSize(width,height)
        Log.i(this.javaClass.name, String.format("The window has resized.width=%d,height=%d", width, height))

        if(m_isRunning){
            try{
                m_isRunning=false
                m_thread!!.join()
            }catch (e:java.lang.Exception){
                e.printStackTrace()
            }

        }

        m_isRunning=true
        if(intent.hasExtra(GAME_TYPE)){
            m_stateStack.clear()
            val type=intent.getStringExtra(GAME_TYPE)
            if(GAME_KANCORE.equals(type) ){
                m_stateStack.add(DungeonState(this))
            }else if(GAME_KANCORE_ROOT_BOX.equals(type)){
                m_stateStack.add(RootState(this))
            }
        }
        //ゲームスレッドを生成
        //アクティビティの構築が終わったら開始する
        m_thread = Thread(this)
        m_thread!!.start()

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    override fun run() {

        var prevFrameTime: Long = 0
        var prevUpdateTime: Long = 0
        while (m_isRunning) {
            Log.i(this.javaClass.name,String.format("%d states in stack",m_stateStack.size))
            if (0 < m_stateStack.size) {
                Log.i(this.javaClass.name,String.format("Top stack is %s",m_stateStack.peek().toString()))
                m_stateStack.peek().onUpdate(this)
            }

            if(0==m_stateStack.size){
                Log.i(this.javaClass.name,"Ending the main loop")
                m_isRunning=false
                break
            }
            val curTime = System.currentTimeMillis()
            if (FPSHelper.waitFrame(prevFrameTime, prevUpdateTime, curTime)) {
                val c = m_surfaceHolder!!.lockCanvas() ?: continue
                (m_renderer as CanvasRenderer).bindCanvas(c)
                m_renderer!!.setColor(Color.BLACK)
                m_renderer!!.fillRect(0, 0, m_screenMode!!.getScreenWidth(), m_screenMode!!.getScreenHeight())
                if (0 < m_stateStack.size) {
                    m_stateStack.peek().onDraw(m_renderer!!)
                }

                m_surfaceHolder!!.unlockCanvasAndPost(c)
                prevUpdateTime = curTime

            }
            prevFrameTime = curTime
        }

        if(!m_isRunning && m_stateStack.empty()) {
            Log.i(this.javaClass.name,"Terminate the activity")
            this.finish()
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (0 < m_stateStack.size && MotionEvent.ACTION_UP == event.action) {
            m_stateStack.peek().onTouch(this, event.x.toInt(), event.y.toInt())
        }
        return true
    }




}
