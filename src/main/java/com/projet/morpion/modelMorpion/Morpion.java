package com.projet.morpion.modelMorpion;

public class Morpion implements IMorpion{
    private double [] matriceDuJeu;

    public static double JOEUR_1 = 1;
    public static double JOEUR_2 = 2;

    public Morpion()
    {
        matriceDuJeu = new double[9];
    }
    @Override
    public boolean isWin(double[] matriceDuJeu) {
        return false;
    }

    @Override
    public boolean isEndGame(double[] matriceDujeu) {
        return false;
    }

    public double[] getMatriceDuJeu() {
        return matriceDuJeu;
    }

    public void setMatriceDuJeu(double[] matriceDuJeu) {
        this.matriceDuJeu = matriceDuJeu;
    }
}
