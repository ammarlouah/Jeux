package org.example;

import ilog.concert.*;
import ilog.cplex.*;

import java.io.IOException;

public class Jeux {
    private short[][] A;
    private int nbSerie,nbNombre,bornSup;
    private IloCplex modele;
    private IloNumVar[][] x;
    private IloNumVar[] y;

    public Jeux(short[][] A,int bornSup){
        this.A = A;
        nbSerie = A.length;
        nbNombre = A[0].length;
        this.bornSup = bornSup;
        try{
            modele = new IloCplex();
            x = new IloNumVar[nbSerie][nbNombre];
            y = new IloNumVar[nbSerie];
            createModele();
        }catch(IloException e){
            e.printStackTrace();
        }
    }

    private void createModele() throws IloException{
        createVariables();
        createConstraints1();
        createConstraints2();
        createFonctionObj();
    }

    public void createVariables(){
        try {
            y = modele.boolVarArray(nbSerie);
            for(int i = 0; i < nbSerie; i++){
                    x[i] = modele.boolVarArray(nbNombre);
            }
        } catch (IloException e) {
            e.printStackTrace();
        }
    }

    public void createConstraints1() throws IloException {
        for(int i = 0; i < nbSerie; i++){
            IloLinearNumExpr expr = modele.linearNumExpr();
            for(int j = 0; j < nbNombre; j++){
                expr.addTerm(A[i][j],x[i][j]);
            }
            expr.addTerm(-1*bornSup,y[i]);
            modele.addLe(expr,0);

        }
    }

    public void createConstraints2() throws IloException {
        IloLinearNumExpr expr = modele.linearNumExpr();
        for(int i = 0; i < nbSerie; i++){
            expr.addTerm(1,y[i]);
        }
        modele.addEq(expr,1);
    }

    public void createFonctionObj() throws IloException {
        IloLinearNumExpr objectif = modele.linearNumExpr();
        for(int i = 0; i < nbSerie; i++){
            for(int j = 0; j < nbNombre; j++){
                objectif.addTerm(A[i][j],x[i][j]);
            }
        }
        modele.addMaximize(objectif);
    }

    public boolean solve(){
        boolean hasSolved = false;
        try {
            hasSolved = modele.solve();
        } catch (IloException e) {
            e.printStackTrace();
        }
        return hasSolved;
    }

    public double[][] getSolutionX() throws IloException {
        double[][] solution = new double[nbSerie][nbNombre];
        for(int i = 0; i < nbSerie; i++){
            solution[i] = modele.getValues(x[i]);
        }
        return solution;
    }

    public double[] getSolutionY() throws IloException {
        double[] solution = new double[nbSerie];
        solution = modele.getValues(y);
        return solution;
    }


}
