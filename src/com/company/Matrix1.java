package com.company;


public class Matrix1
{
    public static void main(String[] args)
    {

        int [] [] A = {
            {20, 18, 22, 20},
            {18, 20, 18, 21},
            {16, 18, 16, 20},
            {25, 24, 22, 24}
        };

        int [] [] B = {
            {20, 18, 22, 20},
            {18, 20, 18, 21},
            {16, 18, 16, 20},
            {25, 24, 22, 24}
        };

        int shape = A.length;  // assume [NxN].[NxN]

        int[][] C = new int[shape][shape];  // init a C = NxN matrix of zeros

        for (int i = 0; i < shape; i++) {
            for (int j = 0; j < shape; j++) {
                for (int k = 0; k < shape; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[i].length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }
}


