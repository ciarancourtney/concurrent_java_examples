package com.company;


public class Matrix2 {

    public static void main(String args[]) {
        // define matrix size
        int size = 100;  // TODO using size=1000 is magnitudes slower than Matrix1

        // as each matrix is size*size, we know this is the number of
        // threads required to perform multiplication
        int thread_pool = Integer.parseInt(args[1]);;

        // Create random matrix A and B of shape <size> by <size>
        int[][] A = new int[size][size];
        int[][] B = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                A[i][j] = (int) (Math.random() * 10);
                B[i][j] = (int) (Math.random() * 10);
            }
        }

        // Init a new Thread object and count int
        int t_count = 0;
        Thread[] my_thread = new Thread[thread_pool];

        // Create an empty matrix C of shape <size> by <size>
        int[][] C = new int[size][size];
        final long startTime = System.currentTimeMillis();

        try {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    // use each thread to multiply row by column wise
                    my_thread[t_count] = new Thread(new WorkerThread(i, j, A, B, C, size));
                    my_thread[t_count].start();

                    // wait for thread to complete
                    my_thread[t_count].join();
                    t_count++;
                }
            }
        } catch (InterruptedException e) {
            System.err.println("[InterruptedException]" + e.getMessage());
        }

        final long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("Total Time: " + duration + " miliseconds");
        System.out.println("Total Enumerated Thread Count: " + t_count);
    }
}


class WorkerThread implements Runnable {
    private int i;
    private int j;
    private int A[][];
    private int B[][];
    private int C[][];
    private int size;

    WorkerThread(int i, int j, int A[][], int B[][], int C[][], int size) {
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
