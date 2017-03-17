import logging
import subprocess
import os
import time

import matplotlib.pyplot as plt

JAVA_BIN = 'C:\Program Files\Java\jdk1.8.0_102'
JAVAC = os.path.join(JAVA_BIN, 'bin', 'javac.exe')
JAVA = os.path.join(JAVA_BIN, 'bin', 'java.exe')

logging.getLogger().setLevel(logging.INFO)

def extract_return_stats(output):
    stat_list = [int(s) for s in output.split(' ') if s.isdigit()]
    return stat_list[0], stat_list[1]


def plot_results(*arg):
    logging.info('Plotting Results...')

    legend = []
    for list in arg:
        legend.append(list[0][2])
        x = []
        y = []
        for result in list:
            x.append(result[0])
            y.append(result[1])

        plt.plot(x, y, linewidth=2)

    plt.xlabel('Matrix Size')
    plt.ylabel('Time (ms)')
    plt.legend(legend, loc='upper left')
    plt.show()


class Benchmark(object):

    def __init__(self, start_size, end_size, step_size, iterations):
        logging.info('Initializing benchmark...')
        self.start_size = start_size
        self.end_size = end_size
        self.step_size = step_size
        self.iterations = iterations

    def run(self, java_class, thread_count):
        classpath = os.path.join('com', 'company')
        java_filename_path = os.path.join(classpath, java_class + '.java')
        java_class_path = 'com/company/{}'.format(java_class)

        # compile java
        subprocess.Popen([JAVAC, '-O', java_filename_path])

        matrix_size = self.start_size
        results = []
        while matrix_size <= self.end_size:
            cmd = [JAVA, '-cp', '.', java_class_path, str(matrix_size), str(thread_count)]
            x = 0
            time_ms = 0
            while x < self.iterations:
                p = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, stdin=subprocess.PIPE)
                stdout, stderr = p.communicate()
                time, thread_count = extract_return_stats(stdout.decode().rstrip())
                x += 1
                time_ms += time

            results.append([matrix_size, time_ms/self.iterations, java_class + ' ' + str(thread_count) + ' threads'])
            logging.info('[{}] average runtime for matrix size {} is {} ms'.format(java_class, matrix_size, time_ms/self.iterations))
            matrix_size += self.step_size

        return results

# init benchmark parameters
b = Benchmark(start_size=100, end_size=1600, step_size=100, iterations=5)

# run each benchmark
matrix3_results = b.run('Matrix3', 4)
time.sleep(1)
matrix43b_results = b.run('Matrix43b', 8)
time.sleep(1)

# plot all results in one graph
plot_results(matrix3_results, matrix43b_results)
