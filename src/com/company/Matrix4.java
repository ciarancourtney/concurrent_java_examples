package com.company;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Matrix4 {

    public static void main(String args[]) {
        // define matrix size
        int size = Integer.parseInt(args[0]);
        int thread_pool = Integer.parseInt(args[1]);

        // Create random matrix A and B of shape <size> by <size>
        int[][] A = new int[size][size];
        int[][] B = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                A[i][j] = (int) (Math.random() * 10);
                B[i][j] = (int) (Math.random() * 10);
            }
        }

        // Create an empty matrix C of shape <size> by <size>
        int[][] C = new int[size][size];

        final long startTime = System.currentTimeMillis();

        // execute thread for each quadrent of matrix N.B. this code assumes an even length square matrix
        ExecutorService threadExecutor = Executors.newFixedThreadPool(thread_pool);
        threadExecutor.execute(new Thread(new WorkerThread4(0, (size/2)-1, 0, (size/2)-1, A, B, C, size)));  // Top Left
        threadExecutor.execute(new Thread(new WorkerThread4(0, (size/2)-1, size/2, size-1, A, B, C, size)));  // Top Right
        threadExecutor.execute(new Thread(new WorkerThread4(size/2, size-1, 0, (size/2)-1, A, B, C, size)));  // Bottom Left
        threadExecutor.execute(new Thread(new WorkerThread4(size/2, size-1 , size/2, size-1, A, B, C, size)));  // Bottom Right
        threadExecutor.shutdown();
        
        try {
            threadExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("threadExecutor.awaitTermination caught exception");
        }
        
        final long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("Total Time: " + duration + " miliseconds");
        System.out.println("Total Enumerated Thread Count: " + thread_pool);

    }
}


class WorkerThread4 implements Runnable {
    private int start_row;
    private int end_row;
    private int start_col;
    private int end_col;
    private int A[][];
    private int B[][];
    private int C[][];
    private int size;

    WorkerThread4(int start_row, int end_row, int start_col, int end_col, int A[][], int B[][], int C[][], int size) {
        this.start_row = start_row;
        this.end_row = end_row;
        this.start_col = start_col;
        this.end_col = end_col;
        this.A = A;
        this.B = B;
        this.C = C;
        this.size = size;
    }

    @Override
    public void run() {
        int i = start_row;
        int j = start_col;

        //System.out.println("[(" + start_row + ", " + end_row + "), (" + start_col + ", " + end_col + ")]");
        for (i = start_row; i <= end_row; i++) {
            for (j = start_col; j <= end_col; j++) {
                for (int k = 0; k < size; k++) {
                    C[i][j] = C[i][j] + A[i][k] * B[k][j];
                }
            }
        }
    }
}
