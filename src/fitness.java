/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mo-mia
 */
public class fitness {
    
    public int calcFitness(int a[][], int numweeks, int tt[][]) {
        //Calc fitness here
        int fitness = 0;

        //Detects Clashes
        for (int i = 0; i < 5 * numweeks; i++) {
            for (int j = 0; j < 2; j++) {
                if (a[i][j] != 0) {
                    if (tt[i][j] == 0) {
                        fitness += 5;
                    }

                    if (tt[i][j] == 1) {
                        fitness += 2;
                    }

                    if (tt[i][j] == 2) {
                        fitness -= 1;
                    }
                }

                if (tt[i][j] != 0 && a[i][j] == 0) {
                    fitness += 1;
                }
            }
        }

        //I need to add code that makes sure the system is actually booking all the pracs it needs to
        //but to do that I need the data imported so I'll do that later.
        return fitness;
    }
}
