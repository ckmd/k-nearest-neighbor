/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nearestneighbor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Rachmad
 */
public class NearestNeighbor {

    static FileInputStream filestream;
    static DataInputStream in;
    static BufferedReader br;
    static String[] label;
    static double[][] result;
    static ArrayList<String[]> data;
    static ArrayList<String[]> data2;
    static Scanner scan;
    static Scanner scank;
    static int[][] rank;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //input data test
        filestream = new FileInputStream("datatest.txt");
        in = new DataInputStream(filestream);
        br = new BufferedReader(new InputStreamReader(in));
        data2 = new ArrayList<>();
        String strLine2;
        int z = 1;

        while ((strLine2 = br.readLine()) != null) {//memecah label, attribut, dan data
                data2.add(strLine2.split(","));//data get didapat per baris
            z++;
        }
        //input data training
        filestream = new FileInputStream("dataset.txt");
        in = new DataInputStream(filestream);
        br = new BufferedReader(new InputStreamReader(in));
        data = new ArrayList<>();
        scan = new Scanner(System.in);
        scank = new Scanner(System.in);

        //dipecah per data (memiliki x, y , class)
        String strLine;

        int i = 1;
        while ((strLine = br.readLine()) != null) {
            if (i == 1) {
                label = strLine.split("\t");
            } else {
                data.add(strLine.split("\t"));//data get didapat per baris
            }
            i++;
        }
        int totalData = data.size();
        result = new double[totalData][3];
        rank = new int[4][2];

        System.out.println("total data training : "+data.size());
        System.out.println("total data test : "+data2.size());
        int loop = 0;
        int hasil = 0;

        //input nilai k
        System.out.print("Masukkan nilai k : ");
        int inputk = scank.nextInt();
        System.out.println("data \tasli \tklas \tket");
        while (loop < 15) {
            String data20 = data2.get(loop)[0];
            String data21 = data2.get(loop)[1];
            String data22 = data2.get(loop)[2];
            //input data test
//            System.out.print("Masukkan data test : ");
//            String input = scan.nextLine();
//            String[] dataTest = input.split(", ");

            //dihitung jarak 1 per 1 dengan data yang lain
            for (int a = 0; a < data.size(); a++) {
                double valuex = pow((Integer.parseInt(data20) - Integer.parseInt(data.get(a)[0])), 2);
                double valuey = pow((Integer.parseInt(data21) - Integer.parseInt(data.get(a)[1])), 2);
                double value = valuex + valuey;
                result[a][0] = Math.sqrt(value);
                result[a][1] = 1 + a;
                result[a][2] = Double.valueOf(data.get(a)[2]);
//            System.out.println(result[a][0] + "cm dari data ke : " + result[a][1]+" class : "+result[a][2]);
            }
//            System.out.println("jumlah data : " + result.length);

            //menyortir hasil yang sudah dihitung
            java.util.Arrays.sort(result, new java.util.Comparator<double[]>() {
                public int compare(double[] a, double[] b) {
                    return Double.compare(a[0], b[0]);
                }
            });
//        for (int b = 0; b < data.size(); b++) {
//            System.out.println(result[b][0] + " cm dari data ke : " + result[b][1]+" class : "+result[b][2]);
//        }
            //mengambil k data terdekat
//            for (int c = 0; c < inputk; c++) {
//                System.out.println(result[c][0] + " cm data terdekat : " + result[c][1] + " class : " + result[c][2]);
//            }
            //melakukan voting dengan nilai k
            for (int e = 0; e < 4; e++) {
                rank[e][0] = 0;
                rank[e][1] = e + 1;
            }
            //voting
            for (int d = 0; d < inputk; d++) {
                if (result[d][2] == 1.0) {
                    rank[0][0]++;
                } else if (result[d][2] == 2.0) {
                    rank[1][0]++;
                } else if (result[d][2] == 3.0) {
                    rank[2][0]++;
                } else {
                    rank[3][0]++;
                }
            }
            java.util.Arrays.sort(rank, new java.util.Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    return Integer.compare(a[0], b[0]);
                }
            });
//            System.out.println("class terbanyak : " + rank[3][1]);

            //menyimpan ke dalam array true/false
            System.out.print("ke "+(loop+1)+"\t( "+data22+",\t"+rank[3][1]+" ) ");
            if (Integer.parseInt(data22) == rank[3][1]) {
                System.out.println("\tbenar");
                hasil++;
            } else {
                System.out.println("\tsalah");
            }
            //menampilkan hasil akhir presentasi error
            loop++;
        }
        float hasilAkhir = (float) hasil / 15 * 100;
        System.out.println("presentasi benar : " + hasilAkhir);
    }

}
