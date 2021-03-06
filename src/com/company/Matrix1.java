package com.company;


public class Matrix1
{
    public static void main(String[] args)
    {
        // define matrix size
        int size = Integer.parseInt(args[0]);

        // Create random matrix A and B of shape <size> by <size>
        int [][] A = new int[size][size];
        int [][] B = new int[size][size];

        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                A[i][j] = (int) (Math.random()*10);
                B[i][j] = (int) (Math.random()*10);
            }
        }

        // Create an empty matrix C of shape <size> by <size>
        int[][] C = new int[size][size];

        // Calculate using 3 for loops the result of A.B = C
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        final long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);

        // print C matrix row by row
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[i].length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Total Time: " + duration + " miliseconds");
        System.out.println("Fixed Thread Count: " + 1);
    }
}


