# CA670 Concurrent Programming - Assignment 1

## Spec

You are to develop 2 thread-based Java Programs, capable of executing on multiple cores, that can multiply 2 dense 
matrices. The 1st Java program may implement a naive matrix-matrix multiply algorithm. The 2nd Java program must 
implement a significantly more efficient matrix-matrix multiply algorithm.

## Introduction

Matrix multiplication is a binary operation that produces a matrix from two matrices. In general it involves multiplying
corresponding entries from rows in matrix A by columns in matrix B, and summing the result to produce an entry in matrix C.

The most naive method of performing this in programming is by using 3 nested for loops, which in Big O Notation is O(n³).
An example of this is found in Matrix1.java

## Benchmark specs

Intel Core i7-4750HQ 
 - 2.0GHz (up to 3.2GHz turbo)
 - 4 cores (8 threads)
 - L1 Cache	256 KB
 - L2 Cache	1024 KB
 - L3 Cache	6144 KB
 - Geekbench 2 - 32 Bit - Total Score 10366
16GB DDR3 1600 RAM
Java JDK 1.8.0_102 

## Matrix1.java (No Multithreading)

This piece of code creates 2 random matrices A & B of shape 1000x1000 and computes the product of them, matrix C.
This takes of the order of 2700 ± 100ms and consumes 5 threads according to Netbeans profiler.

