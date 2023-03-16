package com.projet.morpion.models;

public class Morpion{
    private double [] matriceDuJeu;
    private boolean etatDeLaPartie = false;
    private int nombreDeTour = 0;
    private int idWinner = 0;
    private int [] positionWinner= new int[3];
    public Morpion()
    {
        matriceDuJeu = new double[9];
    }

    public boolean isWin() {
        for (int i = 0; i < 9; i++) {
            if (isWinDiagonal(i))
            {
                System.out.println("gagne diagonal");
                return  true;
            }
            if(isWinHorizontal(i))
            {
                System.out.println("gagne horizontal");
                return  true;
            }
            if (isWinVerical(i))
            {
                System.out.println("gagne vertical");
                return true;
            }
        }
        return false;
    }

    private boolean isWinDiagonal(int position)
    {
        if(position == 0) {
            if (matriceDuJeu[position] == matriceDuJeu[position + 4] && matriceDuJeu[position] == matriceDuJeu[position + 8] && matriceDuJeu[position] != 0) {
                positionWinner[0] = position;
                positionWinner[1] = position + 4;
                positionWinner[2] = position + 8;
                idWinner = (int) matriceDuJeu[position];
                return true;
            } else
                return false;
        }
        if( position == 2)
        {
            if (matriceDuJeu[position] == matriceDuJeu[position + 2] && matriceDuJeu[position] == matriceDuJeu[position + 4] && matriceDuJeu[position] != 0) {
                positionWinner[0] = position;
                positionWinner[1] = position + 2;
                positionWinner[2] = position + 4;
                idWinner = (int) matriceDuJeu[position];
                return true;
            } else
                return false;
        }
        return etatDeLaPartie;
    }
    private boolean isWinHorizontal(int position)
    {
        if(position == 0 || position == 3 || position == 6) {
            if (matriceDuJeu[position] == matriceDuJeu[position + 1] && matriceDuJeu[position] == matriceDuJeu[position + 2] && matriceDuJeu[position] != 0) {
                positionWinner[0] = position;
                positionWinner[1] = position + 1;
                positionWinner[2] = position + 2;
                idWinner = (int) matriceDuJeu[position];
                return true;
            }
        }
        return false;
    }

    private boolean isWinVerical(int position)
    {
        if(position == 0 || position == 1 || position == 2) {
            if (matriceDuJeu[position] == matriceDuJeu[position + 3] && matriceDuJeu[position] == matriceDuJeu[position + 6] && matriceDuJeu[position] != 0) {
                positionWinner[0] = position;
                positionWinner[1] = position + 3;
                positionWinner[2] = position + 6;
                idWinner = (int) matriceDuJeu[position];
                return true;
            }
        }
        return false;
    }

    public boolean isPositionPlayable(int position) {
        if (matriceDuJeu[position] == 0) {
            System.out.println("on peut jouer cette position");
            return true;
        } else {
            System.out.println("on ne peut pas jouer cette position");
            return false;
        }
    }

    public void afterPlayerOneMove(int positionPlayed)
    {
        if(isPositionPlayable(positionPlayed))
        {
            matriceDuJeu[positionPlayed] = -1;
            nombreDeTour++;
        }
    }
    public void afterPlayerTwoMove(int positionPlayed)
    {
        if(isPositionPlayable(positionPlayed))
        {
            matriceDuJeu[positionPlayed] = 1;
            nombreDeTour++;
        }
    }

    public boolean isGrilleRempli()
    {
        if(nombreDeTour == 9)
        {
            return true;
        }else
            return false;
    }

    public int getNombreDeTour() {
        return nombreDeTour;
    }

    public int[] getPositionWinner() {
        return positionWinner;
    }

    public int getIdWinner() {
        return idWinner;
    }

    public double[] getMatriceDuJeu() {
        return matriceDuJeu;
    }
}
