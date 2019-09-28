package com.personal.circus;

import java.lang.Exception

class FPSHelper {

    companion object {
        @JvmStatic
        fun waitFrame(prevFrameTime: Long, prevViewUpdateTime: Long, currentTime: Long): Boolean {
            val timeElapsed = currentTime - prevFrameTime;
            if (Config.TIME_PER_FRAME > timeElapsed) {
                try {
                    Thread.sleep(Config.TIME_PER_FRAME - timeElapsed);
                } catch (e: Exception) {
                }
                return true;
            }
            if (0.toLong() == timeElapsed)
                return true;
            if (Config.TIME_PER_FRAME * 4 <= (currentTime - prevViewUpdateTime)) {
                return true;
            }

            return false;

        }

    }
}