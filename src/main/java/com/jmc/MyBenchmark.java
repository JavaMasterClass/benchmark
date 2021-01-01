package com.jmc;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Measurement(time = 15, iterations = 2)
@Warmup(time = 15, iterations = 1)
public class MyBenchmark {

    @Param("100000")
    public int n;

    @Benchmark
    public int fib_dp() { 
        int f[] = new int[n + 1];   
        f[0] = 0;
        if (n > 0) {
            f[1] = 1;
        }   
  
        for (int i = 2; i <= n; i++)
            f[i] = f[i - 1] + f[i - 2];  
                
        return f[n]; 
    }

    @Benchmark
    public int fib_so() 
    { 
        int a = 0, b = 1, c; 
        if (n == 0) 
            return a; 
        for (int i = 2; i <= n; i++) { 
            c = a + b; 
            a = b; 
            b = c; 
        } 
        return b;
    }
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
