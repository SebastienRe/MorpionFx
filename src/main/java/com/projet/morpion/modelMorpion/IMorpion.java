package com.projet.morpion.modelMorpion;

public interface IMorpion {
    public boolean isWin();
    public boolean isEndGame();
    public boolean isPositionPlayable(int position);
}
