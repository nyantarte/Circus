package com.circus.girlsfleet;

import android.content.res.AssetManager;

import java.util.Random;
import java.util.Stack;

public interface IGameSystem {

    ScreenMode getScreenMode();
    Stack<IGameState> getStateStack();
    Random getRandom();
    AssetManager getAssetManager();

}
