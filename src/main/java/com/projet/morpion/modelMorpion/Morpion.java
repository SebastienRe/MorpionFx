package com.projet.morpion.modelMorpion;

public class Morpion implements IMorpion{
    private double [] matriceDuJeu;

    public static double JOEUR_1 = -1;
    public static double JOEUR_2 = 1;

    private boolean etatDeLaPartie = false;
    private int nombreDeTour = 0;
    public Morpion()
    {
        matriceDuJeu = new double[9];
    }
    @Override
    public boolean isWin() {
        int i = 0;
        boolean valeurDeRetour = false;
        while (i< 9) {

                if (isWinDiagonal(i))
                {
                    System.out.println("tu as gagné");
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
                }else
                    i++;


        }
        return valeurDeRetour;
    }

    private boolean isWinDiagonal(int position)
    {
        if(position == 0) {
            if (matriceDuJeu[position] == -1 && matriceDuJeu[position + 4] == -1 && matriceDuJeu[position + 8] == -1) {
                System.out.println("joueur 1 a gagné");
                etatDeLaPartie = true;
                return true;
            } else if
            (matriceDuJeu[position] == 1 && matriceDuJeu[position + 4] == 1 && matriceDuJeu[position + 8] == 1) {
                System.out.println("joeur 2 a gagné");
                return true;
            } else
                return false;
        }
        if( position == 2)
        {
            if (matriceDuJeu[position] == -1 && matriceDuJeu[position + 2] == -1 && matriceDuJeu[position + 4] == -1) {
                System.out.println("joueur 1 a gagné");
                etatDeLaPartie = true;

            } else if
            (matriceDuJeu[position] == 1 && matriceDuJeu[position + 2] == 1 && matriceDuJeu[position + 4] == 1) {
                System.out.println("joeur 2 a gagné");
                etatDeLaPartie = true;

            } else
                return false;
        }
        return etatDeLaPartie;
    }
    private boolean isWinHorizontal(int position)
    {
        if(position == 0) {
            if (matriceDuJeu[position] == -1 && matriceDuJeu[position + 1] == -1 && matriceDuJeu[position + 2] == -1) {
                System.out.println("joueur 1 a gagné");
                etatDeLaPartie = true;

            } else if
            (matriceDuJeu[position] == 1 && matriceDuJeu[position + 1] == 1 && matriceDuJeu[position + 2] == 1) {
                System.out.println("joueur 2 a gagné");
                etatDeLaPartie = true;

            }
        }
        if(position == 3) {
            if (matriceDuJeu[position] == -1 && matriceDuJeu[position + 1] == -1 && matriceDuJeu[position + 2] == -1) {
                System.out.println("joueur 1 a gagné");
                etatDeLaPartie = true;
                return true;
            } else if
            (matriceDuJeu[position] == 1 && matriceDuJeu[position + 1] == 1 && matriceDuJeu[position + 2] == 1) {
                System.out.println("joueur 2 a gagné");
                etatDeLaPartie = true;
            }
        }
        if(position == 6) {
            if (matriceDuJeu[position] == -1 && matriceDuJeu[position + 1] == -1 && matriceDuJeu[position + 2] == -1) {
                System.out.println("joueur 1 a gagné");
                etatDeLaPartie = true;

            } else if
            (matriceDuJeu[position] == 1 && matriceDuJeu[position + 1] == 1 && matriceDuJeu[position + 2] == 1) {
                System.out.println("joueur 2 a gagné");
                etatDeLaPartie = true;

            }
        }
            return etatDeLaPartie;
    }
    private boolean isWinVerical(int position)
    {
        if(position == 0) {
            if (matriceDuJeu[position] == -1 && matriceDuJeu[position + 3] == -1 && matriceDuJeu[position + 6] == -1) {
                System.out.println("joueur 1 a gagné");
                etatDeLaPartie = true;
            } else if (matriceDuJeu[position] == 1 && matriceDuJeu[position + 3] == 1 && matriceDuJeu[position + 6] == 1) {
                System.out.println("joueur 2 a gagné");
                etatDeLaPartie = true;
            }
        }
            if(position == 1)
            {
                if (matriceDuJeu[position] == -1 && matriceDuJeu[position + 3] == -1 && matriceDuJeu[position + 6] == -1) {
                    System.out.println("joueur 1 a gagné");
                    etatDeLaPartie = true;
                } else if (matriceDuJeu[position] == 1 && matriceDuJeu[position + 3] == 1 && matriceDuJeu[position + 6] == 1) {
                    System.out.println("joueur 2 a gagné");
                    etatDeLaPartie = true;
                }
            }
            if(position == 2)
                if (matriceDuJeu[position] == -1 && matriceDuJeu[position + 3] == -1 && matriceDuJeu[position + 6] == -1) {
                    System.out.println("joueur 1 a gagné");
                    etatDeLaPartie = true;
                } else if (matriceDuJeu[position] == 1 && matriceDuJeu[position + 3] == 1 && matriceDuJeu[position + 6] == 1) {
                    System.out.println("joueur 2 a gagné");
                    etatDeLaPartie = true;
                }
        return etatDeLaPartie;
    }
    @Override
    public boolean isEndGame() {
        int i=0;
        boolean valeurDeRetour = false;
        /*while (i<9)
        {
            if(matriceDuJeu[i] == 0)
            {
                System.out.println("Grille pas rempli entierement");
                valeurDeRetour = false;
                i = 45;
            }else
                i++;
        }*/
        if(this.isGrilleRempli())
        {
            valeurDeRetour = true;
        }
        if(etatDeLaPartie)
            valeurDeRetour = true;
       return valeurDeRetour;
    }

    @Override
    public boolean isPositionPlayable(int position) {
        if (matriceDuJeu[position] == 0) {
            System.out.println("on peut jouer cette position");
            return true;
        } else {
            System.out.println("on ne peut pas jouer cette position");
            return false;
        }
    }

    public double[] getMatriceDuJeu() {
        return matriceDuJeu;
    }

    public void setMatriceDuJeu(double[] matriceDuJeu) {
        this.matriceDuJeu = matriceDuJeu;
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

    public boolean isEtatDeLaPartie() {
        return etatDeLaPartie;
    }

    public void setEtatDeLaPartie(boolean etatDeLaPartie) {
        this.etatDeLaPartie = etatDeLaPartie;
    }
    private boolean isGrilleRempli()
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

    public void setNombreDeTour(int nombreDeTour) {
        this.nombreDeTour = nombreDeTour;
    }
}
