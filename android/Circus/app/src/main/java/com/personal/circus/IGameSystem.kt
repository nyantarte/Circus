package com.personal.circus;
import android.content.res.AssetManager
import java.util.*;
import kotlin.collections.HashMap

interface IGameSystem {
    fun getScreenMode():ScreenMode;
    fun getStateStack():Stack<IGameState>;
    fun getRand():Random;

    fun getAssets():AssetManager

}