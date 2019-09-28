package com.personal.circus;


interface IGameState {
    fun onDraw(r: IRenderer);
    fun onUpdate(s: IGameSystem);
    fun onTouch(s: IGameSystem, x: Int, y: Int);

}