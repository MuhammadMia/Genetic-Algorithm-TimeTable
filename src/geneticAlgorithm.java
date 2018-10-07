
public class geneticAlgorithm {

    int numweeks;
    Timetable objTable;
    fitness fitness = new fitness();

    int arrTT[][];
    /*
    arrTT simplifies to arrSimpleTT where each row in arrSimpleTT represents the specified group of periods.
    Ex. A lecture or tut anywhere in periods means a clash in the AM Session of a prac. The highest priority determines
    the number given to the slot. Meaning if theres a lecture (1) and a tut (2), the number in the slot will bea  2.
     */

    int arrSimpleTT[][];

    /*
    arrSimpleTT and arrPracTT are now the same dimensions and therefore can be easily compared.
     */
    int arrA1[][];

    int arrA2[][];

    int arrA3[][];

    int arrA4[][];

    int fittest = 0;

    int arrFittest[][];

    int secondFittest;

    int arrSecondFittest[][];

    int best = 0;

    int arrBest[][];

    private void init() {

        simplifyTT();

        best = -100;
        PopulateAs();
        //Running Genetic Algorithm
        for (int i = 0; i < 10000; i++) {
            int a1Fitness = fitness.calcFitness(arrA1, numweeks, arrSimpleTT);
            if (a1Fitness > best) {
                best = a1Fitness;
                for (int j = 0; j < 5 * numweeks; j++) {
                    for (int k = 0; k < 2; k++) {
                        arrBest[j][k] = arrA1[j][k];
                    }
                }
            }

            int a2Fitness = fitness.calcFitness(arrA2, numweeks, arrSimpleTT);
            if (a2Fitness > best) {
                best = a2Fitness;
                for (int j = 0; j < 5 * numweeks; j++) {
                    for (int k = 0; k < 2; k++) {
                        arrBest[j][k] = arrA2[j][k];
                    }
                }
            }

            int a3Fitness = fitness.calcFitness(arrA3, numweeks, arrSimpleTT);
            if (a3Fitness > best) {
                best = a3Fitness;
                for (int j = 0; j < 5 * numweeks; j++) {
                    for (int k = 0; k < 2; k++) {
                        arrBest[j][k] = arrA3[j][k];
                    }
                }
            }

            int a4Fitness = fitness.calcFitness(arrA4, numweeks, arrSimpleTT);
            if (a4Fitness > best) {
                best = a4Fitness;
                for (int j = 0; j < 5 * numweeks; j++) {
                    for (int k = 0; k < 2; k++) {
                        arrBest[j][k] = arrA4[j][k];
                    }
                }
            }

            System.out.println("\nGeneration " + i);
            System.out.println(a1Fitness);
            System.out.println(a2Fitness);
            System.out.println(a3Fitness);
            System.out.println(a4Fitness);

            selection(a1Fitness, a2Fitness, a3Fitness, a4Fitness);

            crossover();

            mutate();
        }

        //Printing Best Solution out of 10000 Generations
        for (int i = 0; i < 5 * numweeks; i++) {
            System.out.print("\n");
            for (int j = 0; j < 2; j++) {
                System.out.print(arrBest[i][j] + " ");
            }
        }

        System.out.println("Best Fitness: " + best);

        System.out.println("Timetable without pracs: ");
        for (int i = 0; i < 5 * numweeks; i++) {
            System.out.print("\n");
            for (int j = 0; j < 2; j++) {
                System.out.print(arrSimpleTT[i][j] + " ");
            }
        }
        objTable.combineiTable(arrBest, arrSimpleTT, arrTT);

    }

    public void simplifyTT() {
        /*
        arrTT simplifies to arrSimpleTT where each row in arrSimpleTT represents the specified group of periods.
        Ex. A lecture or tut anywhere in periods means a clash in the AM Session of a prac. The highest priority determines
        the number given to the slot. Meaning if theres a lecture (1) and a tut (2), the number in the slot will bea  2.
         */

        //Check for Clashes in the AM Session
        for (int i = 0; i < 5 * numweeks; i++) {
            for (int j = 1; j <= 4; j++) {
                if (arrSimpleTT[i][0] != 2) {
                    if (arrTT[i][j] == 1) {
                        arrSimpleTT[i][0] = 1;
                    }
                }

                if (arrTT[i][j] == 2) {
                    arrSimpleTT[i][0] = 2;
                }

                if (arrTT[i][j] == 0) {
                    //Do Nothing
                }
            }
        }

        //Check for Clashes in the PM Session
        for (int i = 0; i < 5 * numweeks; i++) {
            for (int j = 7; j <= 10; j++) {
                if (arrSimpleTT[i][1] != 2) {
                    if (arrTT[i][j] == 1) {
                        arrSimpleTT[i][1] = 1;
                    }
                }

                if (arrTT[i][j] == 2) {
                    arrSimpleTT[i][1] = 2;
                }

                if (arrTT[i][j] == 0) {
                    //Do Nothing
                }
            }
        }
    }

    public void PopulateTable(String stable[][]) {
        arrTT = new int[5 * numweeks][11];
        arrSimpleTT = new int[5 * numweeks][2];
        arrFittest = new int[5 * numweeks][2];
        arrSecondFittest = new int[5 * numweeks][2];

        arrBest = new int[5 * numweeks][2];

        objTable = new Timetable(stable, numweeks);
        arrTT = objTable.getiTable();
        
        for (int i = 1; i < numweeks; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 11; k++) {
                    arrTT[j + (5 * i)][k] = arrTT[j][k];
                }
            }
        }

    }

    public void PopulateAs() {
        arrA1 = new int[5 * numweeks][2];
        arrA2 = new int[5 * numweeks][2];
        arrA3 = new int[5 * numweeks][2];
        arrA4 = new int[5 * numweeks][2];

        int template[][] = {
            {1, 0},
            {1, 0},
            {1, 0},
            {1, 0},
            {1, 0}
        };

        for (int i = 0; i < numweeks; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 2; k++) {
                    arrA1[j + (5 * i)][k] = template[j][k];
                }
            }
        }

        int template2[][] = {
            {0, 1},
            {0, 1},
            {0, 1},
            {0, 1},
            {0, 1}
        };

        for (int i = 0; i < numweeks; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 2; k++) {
                    arrA2[j + (5 * i)][k] = template2[j][k];
                }
            }
        }

        int template3[][] = {
            {1, 1},
            {1, 1},
            {1, 0},
            {0, 0},
            {0, 0}
        };

        for (int i = 0; i < numweeks; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 2; k++) {
                    arrA3[j + (5 * i)][k] = template3[j][k];

                }

            }
        }

        int template4[][] = {
            {1, 0},
            {0, 1},
            {1, 0},
            {0, 1},
            {1, 0}
        };

        for (int i = 0; i < numweeks; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 2; k++) {
                    arrA4[j + (5 * i)][k] = template4[j][k];
                }
            }
        }

    }


    public void selection(int a1Fitness, int a2Fitness, int a3Fitness, int a4Fitness) {
        //Choose fittest here
        int highest = -1000;

        if (a1Fitness > highest) {
            highest = a1Fitness;

            for (int i = 0; i < 5 * numweeks; i++) {
                for (int j = 0; j < 2; j++) {
                    arrFittest[i][j] = arrA1[i][j];
                }
            }
        }

        if (a2Fitness > highest) {
            highest = a2Fitness;

            for (int i = 0; i < 5 * numweeks; i++) {
                for (int j = 0; j < 2; j++) {
                    arrFittest[i][j] = arrA2[i][j];
                }
            }
        }

        if (a3Fitness > highest) {
            highest = a3Fitness;

            for (int i = 0; i < 5 * numweeks; i++) {
                for (int j = 0; j < 2; j++) {
                    arrFittest[i][j] = arrA3[i][j];
                }
            }
        }

        if (a4Fitness > highest) {
            highest = a4Fitness;

            for (int i = 0; i < 5 * numweeks; i++) {
                for (int j = 0; j < 2; j++) {
                    arrFittest[i][j] = arrA4[i][j];
                }
            }
        }

        fittest = highest;

        //Determining second fittest
        highest = -1000;
        if (a1Fitness > highest && a1Fitness < fittest) {
            highest = a1Fitness;
            secondFittest = highest;
            for (int i = 0; i < 5 * numweeks; i++) {
                for (int j = 0; j < 2; j++) {
                    arrSecondFittest[i][j] = arrA1[i][j];
                }
            }
        }
        if (a2Fitness > highest && a2Fitness < fittest) {
            highest = a2Fitness;
            secondFittest = highest;
            for (int i = 0; i < 5 * numweeks; i++) {
                for (int j = 0; j < 2; j++) {
                    arrSecondFittest[i][j] = arrA2[i][j];
                }
            }
        }
        if (a3Fitness > highest && a3Fitness < fittest) {
            highest = a3Fitness;
            secondFittest = highest;
            for (int i = 0; i < 5 * numweeks; i++) {
                for (int j = 0; j < 2; j++) {
                    arrSecondFittest[i][j] = arrA3[i][j];
                }
            }
        }
        if (a4Fitness > highest && a4Fitness < fittest) {
            highest = a4Fitness;
            secondFittest = highest;
            for (int i = 0; i < 5 * numweeks; i++) {
                for (int j = 0; j < 2; j++) {
                    arrSecondFittest[i][j] = arrA4[i][j];
                }
            }
        }
    }

    public void crossover() {
        //Combine fittest members here

        int temp;
        for (int i = 0; i < 1 + 5 * numweeks / 2; i++) {
            for (int j = 0; j < 2; j++) {
                temp = arrFittest[i][j];
                arrFittest[i][j] = arrSecondFittest[i][j];
                arrSecondFittest[i][j] = temp;
            }

        }
        for (int i = 0; i < 5 * numweeks; i++) {
            for (int j = 0; j < 2; j++) {
                arrA1[i][j] = arrFittest[i][j];
                arrA2[i][j] = arrSecondFittest[i][j];
            }
        }
    }

    public void mutate() {
        //Add random mutation here
        int rndInt;
        //Clone a1 and a2 into a3 and a4
        for (int i = 0; i < 5 * numweeks; i++) {
            for (int j = 0; j < 2; j++) {
                arrA3[i][j] = arrA1[i][j];
                arrA4[i][j] = arrA2[i][j];
            }
        }
        //Mutate
        for (int i = 0; i < 5 * numweeks; i++) {
            for (int j = 0; j < 2; j++) {
                rndInt = (int) (Math.random() * 2);
                if (rndInt == 1) {
                    //Mutate a3
                    if (arrA3[i][j] == 1) {
                        arrA3[i][j] = 0;
                    } else {
                        arrA3[i][j] = 1;
                    }
                    //Mutate a4
                    if (arrA4[i][j] == 1) {
                        arrA4[i][j] = 0;
                    } else {
                        arrA4[i][j] = 1;
                    }
                }
            }
        }

    }

    public Timetable getFittedTable() {

        return objTable;
    }

    public geneticAlgorithm(String stable[][], int numweeks) {
        this.numweeks = numweeks;
        PopulateTable(stable);
        init();
    }

}
