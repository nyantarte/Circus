package com.personal.circus.Kancole

import com.personal.circus.Config

class KancoleConfig{
    companion object {
        const val IO_DIR=Config.IO_DIR+"/Kancole";
        const val CHARA_DIR = IO_DIR + "/Chara";
        const val STAGE_DIR = IO_DIR + "/Stage";
        const val FLEET_DIR = IO_DIR + "/Fleet";
        const val IMAGE_DIR = IO_DIR + "/Image";
        const val SAVE_FILE= IO_DIR+"save.json"
        val MIN_ROOM_SIZE = 48;

        val MAX_ROOM_NUM = 3;


        val PLAYER_MOVE_COUNT = 15;
        val BULLET_MOVE_COUNT = 15;


        val BLOCK_H_NUM = 16;
        val BLOCK_W_NUM = 6;

        val FLEET_MEMBER_NUM=6

        val RESULT_WAIT_COUNT=30

    }
}