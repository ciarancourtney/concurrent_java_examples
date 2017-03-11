package com.company;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Matrix3 {

    public static void main(String args[]) {
        // define matrix size
        int size = Integer.parseInt(args[0]);

        int core_count = Runtime.getRuntime().availableProcessors();

        core_count = core_count/2;  // 4 core CPU with HT returns 8 logical processors

        int thread_pool = size/core_count;

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

        ExecutorService threadExecutor = Executors.newFixedThreadPool(thread_pool);

        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                // use each thread to multiply row by column wise
                threadExecutor.execute(new Thread(new WorkerThread3(i, j, A, B, C, size)));
            }
        }
        threadExecutor.shutdown();
        final long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);

        System.out.println("Total Time: " + duration + " miliseconds");
        System.out.println("Total Enumerated Thread Count: " + thread_pool);
    }
}


class WorkerThread3 implements Runnable {
    private int i;
    private int j;
    private int A[][];
    private int B[][];
    private int C[][];
    private int size;

    WorkerThread3(int i, int j, int A[][], int B[][], int C[][], int size) {
        this.i = i;
        this.j = j;
        this.A = A;
        this.B = B;
        this.C = C;
        this.size = size;
    }

    @Override
    public void run() {
        for (int k = 0; k < size; k++) {
            C[i][j] += A[i][k] * B[k][j];
        }
    }
}
