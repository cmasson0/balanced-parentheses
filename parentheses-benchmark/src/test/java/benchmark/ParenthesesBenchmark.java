package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import parentheses.BalancedParentheses;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@State(Scope.Thread)
public class ParenthesesBenchmark {

    @Param("100")
    private String size;

    public static void main(String... args) throws IOException, RunnerException {
        Options opt = new OptionsBuilder()
                .include(ParenthesesBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .timeUnit(TimeUnit.MILLISECONDS)
                .mode(Mode.AverageTime)
                .forks(5)
                .resultFormat(ResultFormatType.CSV)
                .param("size", IntStream.rangeClosed(0,29).map(i -> 100 + i*100).mapToObj(String::valueOf).toArray(String[]::new))
                .build();
        new Runner(opt).run();
    }

    @Benchmark
    public void testMethod(Blackhole bh) {
        bh.consume(BalancedParentheses.numberOfBalancedStrings(Integer.valueOf(size)));
    }


}
