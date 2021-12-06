package com.tech.homework.model;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class PoolCacheManager {
    private CacheLoader<Integer, List<Integer>> loader;
    private LoadingCache<Integer, List<Integer>> cache;
    private final static String outputFile = "data/pool.txt";

    public PoolCacheManager(){
        loader = new CacheLoader<Integer, List<Integer>>() {
            @Override
            public List<Integer> load(Integer key) {
                return getPoolFromFile(key);
            }
        };

        cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(loader);

        cache.putAll(readFromFile());
    }

    public LoadingCache<Integer, List<Integer>> getCache() {
        return cache;
    }

    public synchronized void addPool(Pool pool){
        cache.put(pool.getPoolId(), pool.getPoolValues());
        writeToFile();
    }

    public List<Integer> getPool(Pool pool){
        try {
            return cache.get(pool.getPoolId());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeToFile(){
        File file = new File(outputFile);
        BufferedWriter bf = null;
        try{
            bf = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<Integer, List<Integer>> entry : cache.asMap().entrySet()){
                bf.write(entry.getKey() + ":" + StringUtils.join(entry.getValue(), ","));
                bf.newLine();
            }
            bf.flush();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                bf.close();
            }catch (Exception e){

            }
        }
    }

    private Map<Integer, List<Integer>> readFromFile(){
        Map<Integer, List<Integer>> pool = new HashMap<>();
        BufferedReader br = null;
        try{
            File file = new File(outputFile);
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = br.readLine()) != null){
                String[] arr = line.split(":");
                int poolId = Integer.parseInt(arr[0].trim());
                List<Integer> values = new ArrayList<>();
                for (String val : arr[1].split(",")){
                    values.add(Integer.parseInt(val.trim()));
                }
                if (values.size()>0){
                    pool.put(poolId, values);
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
        }
        return pool;
    }

    private List<Integer> getPoolFromFile(Integer poolId) {

        Map<Integer, List<Integer>> pools = new HashMap<Integer, List<Integer>>();
        pools = readFromFile();

        return pools.get(poolId);
    }
}
