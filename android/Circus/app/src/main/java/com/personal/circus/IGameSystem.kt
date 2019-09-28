package com.personal.circus;
import java.util.*;
import kotlin.collections.HashMap

interface IGameSystem {
    fun getScreenMode():ScreenMode;
    fun getStateStack():Stack<IGameState>;
    fun getRand():Random;


}