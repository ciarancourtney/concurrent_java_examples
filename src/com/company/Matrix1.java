package com.company;


public class Matrix1
{
    public static void main(String[] args)
    {
        // define matrix size
        int size = 100;

        // Create random matrix A of size X size
        int [][] A = new int[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                A[i][j] = (int) (Math.random()*10);
            }
        }

        // Create random matrix B of size X size
        int [][] B = new int[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                B[i][j] = (int) (Math.random()*10);
            }
        }

        // init a zero matrix C of size X size
        int[][] C = new int[size][size];

        // Calculate using 4 for loops the result of A.B = C
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        // print C matrix row by row
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[i].length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }
}


