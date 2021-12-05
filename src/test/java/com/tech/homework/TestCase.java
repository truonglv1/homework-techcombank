package com.tech.homework;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tech.homework.model.Pool;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCase {

    @Test
    void testCache(){
        CacheLoader<Integer, List<Integer>> loader = new CacheLoader<Integer, List<Integer>>() {
            @Override
            public List<Integer> load(Integer key) {
                return null;
            }
        };

        LoadingCache<Integer, List<Integer>> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .build(loader);
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);

        cache.put(1, list1);
        try {
            assertEquals(list1, cache.get(1));
            list2.addAll(list1);
            cache.put(1, list2);
            assertEquals(list2, cache.get(1));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void calculateQuantile(){
        Pool pool = new Pool();
        pool.setPoolId(123);
        List<Integer> values = new ArrayList<>();
        values.add(3);
        values.add(6);
        values.add(7);
        values.add(8);
        values.add(8);
        values.add(10);
        values.add(13);
        values.add(15);
        values.add(16);
        values.add(20);

        List<Integer> valuesSorted =  values.stream().sorted().collect(Collectors.toList());
        pool.setPoolValues(valuesSorted);
        pool.setPercentile(100);
        int quantile = 0;

        if (pool.getPercentile()==0)
            quantile = valuesSorted.get(0);
        else if (pool.getPercentile() == 100)
            quantile = valuesSorted.get(valuesSorted.size()-1);
        else {
            int index = (int) (pool.getPercentile()/100 * (valuesSorted.size()+1));
            quantile = valuesSorted.get(index);
        }

        assertEquals(20, quantile);
    }
}
