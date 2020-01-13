package com.circus.girlsfleet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.circus.girlsfleet.fleet.MapState;

import java.util.Random;
import java.util.Stack;

public class SurfaceActivity extends AppCompatActivity implements IGameSystem,IRenderer,Runnable,SurfaceHolder.Callback {

    private volatile boolean m_isRunning = true;
    private Thread m_thread = null;
    private SurfaceHolder m_surfaceHolder = null;
    private Random m_rad = new Random();
    private ScreenMode m_screenMode;
    private Stack<IGameState> m_stateStack = new Stack<>();

    private Canvas m_canvas;
    private Paint m_paint;
    private int m_screenWidth,m_screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);

        SurfaceView sw = findViewById(R.id.ScreenSurface);
        m_surfaceHolder = sw.getHolder();
        m_surfaceHolder.addCallback(this);
    }

    public ScreenMode getScreenMode() {
        return m_screenMode;
    }

    public AssetManager getAssetManager(){
        return getAssets();
    }
    public Random getRandom(){
        return m_rad;
    }
    public Stack<IGameState> getStateStack(){
        return m_stateStack;
    }
    @Override
    public void onPause() {
        super.onPause();
        m_isRunning = false;
        if (null != m_thread) {
            try {
                m_thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        m_screenMode = ScreenMode.HD_PLUS_VERTICAL;
        m_screenWidth=w;
        m_screenHeight=h;
        adjustSurfacePos(w,h);

        stopGameThread();
        m_isRunning = true;
       // m_stateStack.push(new AzurelaneMapState(this, AzurelaneMapLoader.load("1_1.json",getAssets())));
         m_stateStack.push(new MapState(this, MapLoader.load("1_1.json",getAssets())));
        startGameThread();
    }

    private void adjustSurfacePos(int w,int h) {
        int lOffset=(w-m_screenMode.getRect().width())/2;
        int tOffset=(h-m_screenMode.getRect().height())/2;
        m_screenMode.getRect().offsetTo(lOffset,tOffset);

    }

    private void stopGameThread() {
        if (m_isRunning && null!=m_thread) {
            try {
                m_isRunning = false;
                m_thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void startGameThread() {
        m_isRunning = true;
        m_thread = new Thread(this);
        m_thread.start();

    }

    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void run() {
        long prevFrameTime = 0L;
        long prevUpdateTime = 0L;
        m_paint = new Paint();
        while (m_isRunning) {
            //Log.i(this.getClass().getName(), String.format("%d states in stack", m_stateStack.size()));
            if (0 < m_stateStack.size()) {
                //Log.i(this.getClass().getName(), String.format("Top stack is %s", m_stateStack.peek().toString()));
                m_stateStack.peek().onUpdate(this);
            }

            if (0 == m_stateStack.size()) {
                Log.i(this.getClass().getName(), "Ending the main loop");
                m_isRunning = false;
                break;
            }
            long curTime = System.currentTimeMillis();
            if (FPSHelper.waitFrame(prevFrameTime, prevUpdateTime, curTime)) {
                m_canvas = m_surfaceHolder.lockCanvas();
                setColor(Color.BLACK);
                fillRect(0, 0, m_screenWidth,m_screenHeight);
                if (0 < m_stateStack.size()) {
                    m_stateStack.peek().onDraw(this);
                }

                m_surfaceHolder.unlockCanvasAndPost(m_canvas);
                prevUpdateTime = curTime;

            }
            prevFrameTime = curTime;
        }

        m_canvas = null;
        m_paint = null;
        if (!m_isRunning && m_stateStack.empty()) {
            Log.i(this.getClass().getName(), "Terminate the activity");
            this.finish();
        }

    }


    public void drawRect(Rect r) {
        m_paint.setStyle(Paint.Style.STROKE);
        m_canvas.drawRect(r, m_paint);
    }

    public void drawRect(int l, int t, int w, int h) {
        m_paint.setStyle(Paint.Style.STROKE);
        m_canvas.drawRect(l, t, l + w, t + h, m_paint);
    }

    public void fillRect(Rect r) {
        m_paint.setStyle(Paint.Style.FILL);
        m_canvas.drawRect(r, m_paint);
    }

    public void fillRect(int l, int t, int w, int h) {

        m_paint.setStyle(Paint.Style.FILL);
        m_canvas.drawRect(l,t,l+w,t+h,m_paint);
    }
    public void fillRect(Vector lt,int radius){
        m_paint.setStyle(Paint.Style.FILL);
        m_canvas.drawRect(lt.getX()-radius,lt.getY()-radius,lt.getX()+radius,lt.getY()+radius,m_paint);

    }
    public void drawLine(int x1,int y1,int x2,int y2){
        m_canvas.drawLine(x1,y1,x2,y2,m_paint);
    }
    public void drawText(int l,int t,String txt) {
        m_canvas.drawText(txt,l,t,m_paint);
    }
    public void setColor(int c){
        m_paint.setColor(c);
    }
}
