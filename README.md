# CA670 Concurrent Programming - Assignment 1

Name: Ciaran Courtney
Date: 15 March 2017

## Spec

You are to develop 2 thread-based Java Programs, capable of executing on multiple cores, that can multiply 2 dense 
matrices. The 1st Java program may implement a naive matrix-matrix multiply algorithm. The 2nd Java program must 
implement a significantly more efficient matrix-matrix multiply algorithm.

## Introduction

Matrix multiplication is a binary operation that produces a matrix from two matrices. In general it involves multiplying
corresponding entries from rows in matrix A by columns in matrix B, and summing the result to produce an entry in matrix C.

The most naive method of performing this in programming is by using 3 nested for loops, which in Big O Notation is O(n³).
An example of this is found in Matrix1.java

Note that all code samples generate even sided, square matrices comprised of random integers between 0 and 10.

## Benchmark specs

Intel Core i7-4750HQ 
 - 2.0GHz (up to 3.2GHz turbo)
 - 4 cores (8 threads)
 - L1 Cache	256 KB
 - L2 Cache	1024 KB
 - L3 Cache	6144 KB
 - Geekbench 2 - 32 Bit - Total Score 10366
 
16GB DDR3 1600 RAM

Java JDK 1.8.0_102 x64 on Windows 10

## Matrix1.java (no threading)

This piece of code creates 2 random matrices A & B of shape 1000x1000 and computes the product of them, matrix C.
This takes of the order of 2700 ± 100ms and consumes 5 threads according to Netbeans profiler.


## Matrix2.java (sequential threading per multiplication)

Once again this piece of code creates 2 random matrices A & B and computes the product of them, matrix C. This time 
shape 100x100 was tested as it was found that 1000x1000 was taking an inordinate amount of time compared to Matrix1.
This is assumed to be scheduling overhead, as threads are started and joined.

Execution time was of the order of 1800 ± 100ms and consumes a total of 6 threads according to Netbeans profiler.

## Matrix3.java (concurrent multithreading)

This is a slight modification of Matrix2 where Java Executor Interfaces are used to define a thread pool, of which is
consumes to perform each matrix multiplication.

Results show that a thread count of 2 resulted in best performance.

This was found to be nearly twice as slow as Matrix1 for size=1000, at about 4400ms. Perhaps too sensitive to scheduling
overhead at this relatively small matrix size.
 
![Alt text](results/Matrix3.png?raw=true)

## Matrix4.java (concurrent multithreading)

In this version, Matrix multiplication is split up into 4 quadrants, known as the _Divide & Conquer_ technique.
An ExecutorService is used to calculate each of the four quadrants. 

As before, benchmarks were run for a range matrix sizes and thread pools. Once again it was found that 4,6 and 8 threads
resulted in the best performance.

![Alt text](results/Matrix4.png?raw=true)

### Matrix41.java

Through a slight adjustment in for-loop order, swapping from ijk to ikj, a massive performance increase was found,
especially for matrix sizes 1000 and above.

        for (i = start_row; i <= end_row; i++) {
            for (j = start_col; j <= end_col; j++) {
                for (int k = 0; k < size; k++) {
                    C[i][j] = C[i][j] + A[i][k] * B[k][j];
                }
            }
        }

To:

        for (i = start_row; i <= end_row; i++) {
            for (int k = 0; k < size; k++) {
                for (j = start_col; j <= end_col; j++) {
                    C[i][j] = C[i][j] + A[i][k] * B[k][j];
                }
            }
        }

**Row major order vs. column major order**

In Java it is better to iterate over a row than a column. The naive 3-nested for-loop can be arranged in 6 different 
configurations (3x2x1 = 6). Human intuition assumes a mathematical order of ijk (row by column by index element), but
calculating in the order ikj is the optimal method of performing matrix multiplication in Java.

![Alt text](results/Matrix4Vs41.png?raw=true)

### Matrix42.java

A further 'micro-optimization' can be made by caching certain rows and columns for quicker access later. This also avoids bounds checking

        for (i = start_row; i <= end_row; i++) {
            int[] arowi = A[i];                            // cache row i of A
            int[] crowi = C[i];                            // cache row i of C
            for (int k = 0; k < size; k++) {
                int[] browk = B[k];                        // cache row k of B
                int aik = arowi[k];
                for (j = start_col; j <= end_col; j++) {
                    crowi[j] += aik * browk[j];
                }
            }
        }
        
As a result, there is a slight performance increase  over Matrix41

![Alt text](results/Matrix41Vs42.png?raw=true)

* TODO

    Would global Matrix A B and C variables be faster?
    Employ a more complex algorithm e.g. Strassen

## References

Notes on Row major order vs. column major order in Java and MatrixMultiplication.java sample http://introcs.cs.princeton.edu/java/95linear/
