package com.circus.girlsfleet;

public class FPSHelper {

        public static boolean waitFrame(long prevFrameTime,long prevViewUpdateTime
                ,long currentTime){

            long timeElapsed = currentTime - prevFrameTime;
            if (Config.TIME_PER_FRAME > timeElapsed) {
                try {
                    Thread.sleep(Config.TIME_PER_FRAME - timeElapsed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            if (0L == timeElapsed)
                return true;
            if (Config.TIME_PER_FRAME * 4 <= (currentTime - prevViewUpdateTime)) {
                return true;
            }

            return false;

        }
}
