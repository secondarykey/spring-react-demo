package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.DemoApplication;
import com.example.demo.model.Times;
import com.example.demo.util.DateUtil;

@SpringBootTest(classes = DemoApplication.class)
public class BenchmarkTimeRepository {
	
	@Autowired
	TimeRepository repository;
    @BeforeEach
    void setup() {
    	for ( int i =0; i < 10; i++) {
    		Times time = new Times();
    		Date date = DateUtil.parse("2020-10-11 12:13:14");
    		OffsetDateTime zone = DateUtil.zone(date, "JST");
    		time.setValue(zone.toString());
    		time.setDate(date);
    		time.setTime(date);
    		time.setDateWithout(date);
    		time.setOffsetWith(zone);
    		repository.save(time);
    	}
    }

    @Test
    void benchMark() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkTimeRepository.class.getSimpleName())
                .forks(1) // 1回実行
                .warmupIterations(1) // 1回繰り返し
                .build();
        new Runner(opt).run();
    }

    @Benchmark
    void benchmarkQuery() {
		Iterable<Times> list = repository.findAll();
		List<Times> times = new ArrayList<>();
		list.forEach(times::add);
		assertEquals(times.size(),10);
    }
    
}
