
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 *
 */
public class Timetable {

    int numweeks;
    String[][] stable;
    int itable[][];

    public Timetable(String stable[][], int numweeks) {
        this.stable = stable;
        this.numweeks = numweeks;
        setiTable();
    }

    public void combineiTable(int arrBest[][], int arrSimpleTT[][], int arrTT[][]) {
        //Combining Pracs and SimpleTT
        for (int i = 0; i < 5 * numweeks; i++) {
            System.out.print("\n");
            for (int j = 0; j < 2; j++) {
                if (arrBest[i][j] != 0) {
                    arrSimpleTT[i][j] = 3;
                }
            }
        }

        //Printing SimpleTT
        System.out.println("Timetable with pracs: ");
        for (int i = 0; i < 5 * numweeks; i++) {
            System.out.print("\n");
            for (int j = 0; j < 2; j++) {
                System.out.print(arrSimpleTT[i][j] + " ");
            }
        }

        //Converting to Big Timetable
        for (int i = 0; i < 5 * numweeks; i++) {
            if (arrSimpleTT[i][0] >= 3) {
                arrTT[i][1] = 3;
                arrTT[i][2] = 3;
                arrTT[i][3] = 3;
                arrTT[i][4] = 3;
            }

            if (arrSimpleTT[i][1] >= 3) {
                arrTT[i][7] = 3;
                arrTT[i][8] = 3;
                arrTT[i][9] = 3;
                arrTT[i][10] = 3;
            }

        }

        for (int i = 0; i < 5 * numweeks; i++) {
            System.out.print("\n");
            for (int j = 0; j < 11; j++) {
                System.out.print(arrTT[i][j] + " ");
            }
        }
        itable = arrTT;
    }

    public String[][] getBestTable() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 11; j++) {

                if (itable[i][j] > 2) {
                    stable[i][j] = "PRAC";
                }

            }

        }
        return stable;
    }

    private void setiTable() {
        itable = new int[5 * numweeks][11];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 11; j++) {
                if (stable[i][j] == null) {
                    itable[i][j] = 0;
                } else if (stable[i][j].contains("(L)")) {
                    itable[i][j] = 1;
                } else {
                    itable[i][j] = 2;
                }

            }

        }
    }

    public String ReadFromFile(String disc, String snum) {
        String out = "";
        boolean flag = false;
        try {
            String line = "";
            Scanner scan = new Scanner(new FileReader("Documents/timetableS1.txt"));
            while (scan.hasNext() && flag == false) {
                line = scan.nextLine();

                if (line.contains(snum)) {
                    flag = true;
                }
            }
            String parts[] = line.split("#");

            for (int i = 0; i < parts[3].length(); i++) {
                
                        if (parts[3].charAt(i)==';') {
                    out = out + "\n";
                }else if(parts[3].charAt(i)==','){
                
                out = out + "\t" + parts[3].charAt(i);
                }else{
                    out=out+parts[3].charAt(i);
            }

        }

    }
    catch (FileNotFoundException e

    
        ) {
            System.out.println("ERROR:\t" + e.getMessage());
        out = "User not found...";
    }

    return out ;
}

public void PrintToFile(String studentNum, String timeTableArr[][]) {
        File file = new File("Documents/studentS1data.txt");
        String discipline = "";
        int year = 0;
        try {
            Scanner scfile = new Scanner(file);
            scfile.next();
            while (scfile.hasNextLine()) {
                String line = scfile.nextLine();
                Scanner delim = new Scanner(file).useDelimiter("#");
                String stuNum = delim.next();
                if (stuNum.equals(studentNum)) {
                    year = delim.nextInt();
                    discipline = delim.next();
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR:\t" + ex.getMessage());
        }

        File timetable = new File("Documents/timetableS1.txt");
        try {
            FileWriter flWriter = new FileWriter(timetable);
            BufferedWriter writer = new BufferedWriter(flWriter);
            writer.append(studentNum + "#" + discipline + "#" + year + "#");
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 11; j++) {
                    writer.append(timeTableArr[i][j] + ",");
                }
                writer.append(";");
            }
            writer.newLine();

            writer.close();
        } catch (IOException ex) {
            System.out.println("ERROR:\t" + ex.getMessage());
        }
    }

    public int[][] getiTable() {

        return itable;
    }

    public String[][] getsTable() {
        return stable;
    }

}
