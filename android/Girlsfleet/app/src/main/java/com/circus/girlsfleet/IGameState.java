package com.circus.girlsfleet;

public interface IGameState {
    void onDraw(IRenderer r);
    void onUpdate(IGameSystem s);
    void onTouch(IGameSystem s, int x,int y);
}
