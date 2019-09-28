package com.personal.circus;

class Config {

    companion object {
        const val IO_DIR = "Circus";


        const val FPS = 15;
        const val TIME_PER_FRAME: Long = (1000 / FPS).toLong();


        val FONT_SIZE: Float = 64.toFloat();
        val ICON_SIZE=256

    }
}
