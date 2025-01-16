package org.example;

import ilog.concert.IloException;

public class Main {
    public static void main(String[] args) throws IloException {
        short[][] C = {
                {11, 13, 16, 41, 7, 17, 21}, // Série 1
                {9, 11, 18, 37, 13, 18, 46}, // Série 2
                {3, 23, 31, 53, 11, 17, 21}  // Série 3
        };

        Jeux ex = new Jeux(C,100);

        if(ex.solve()){
            System.out.println("Solution trouvee.");

            //Afficher les series choisies
            double[] solutionY = ex.getSolutionY();
            for(int i = 0 ; i<solutionY.length ; i++){
                if(solutionY[i] == 1) {
                    System.out.println("Serie choisie : S" + (i+1));
                }
            }

            //Afficher les nombres choisies
            double[][] solutionX = ex.getSolutionX();
            for(int i = 0 ; i<solutionX.length ; i++){
                if(solutionY[i] == 1){
                    System.out.println("Nombres choisie dans la serie "+(i+1)+" :");
                    for(int j = 0 ; j<solutionX[i].length ; j++){
                        if(solutionX[i][j] == 1){
                            System.out.println("N"+(j+1)+":"+C[i][j]);
                        }
                    }

                }
            }
        } else {
            System.out.println("Aucune solution trouvée.");
        }
    }
}