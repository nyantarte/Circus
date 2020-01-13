package com.circus.girlsfleet;



public class GameConfig {
    public static final String IO_DIR=Config.IO_DIR+"/Game";
    public static final String CHARA_DIR=IO_DIR+"/Chara";
    public static final String MAP_DIR=IO_DIR+"/Map";

    public static final int FLEET_MEMBER_MAX=6;
    public static final int MAP_MOVE_COUNT=Config.FPS;
    public static final int BATTLE_STATE_COUNT=3*60*Config.FPS;
}
