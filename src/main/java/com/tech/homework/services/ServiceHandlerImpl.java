package com.tech.homework.services;

import com.tech.homework.constants.PoolStatus;
import com.tech.homework.model.Pool;
import com.tech.homework.model.PoolCacheManager;
import com.tech.homework.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceHandlerImpl implements ServiceHandler{
    @Autowired
    PoolCacheManager poolCacheManager;

    @Override
    public String createPool(Pool pool) {
        String status = "";
        try {
            if (poolCacheManager.getCache().asMap().containsKey(pool.getPoolId())){
                List<Integer> valuesOld = poolCacheManager.getPool(pool);
                pool.getPoolValues().addAll(valuesOld);
                status = PoolStatus.APPENDED;
            }else {
                status = PoolStatus.INSERTED;
            }
            List<Integer> valuesSorted =  pool.getPoolValues().stream().sorted().collect(Collectors.toList());
            pool.setPoolValues(valuesSorted);
            poolCacheManager.addPool(pool);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public Response quantile(Pool pool) {
        Response response = new Response();
        try {
            if (poolCacheManager.getCache().asMap().containsKey(pool.getPoolId())){
                int total = poolCacheManager.getCache().asMap().get(pool.getPoolId()).size();
                response.setTotal(total);
                response.setQuantile(caculatorQuantile(pool));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private int caculatorQuantile(Pool pool){
        int quantile = 0;
        try {
            if (poolCacheManager.getCache().asMap().containsKey(pool.getPoolId())){
                List<Integer> values = poolCacheManager.getCache().asMap().get(pool.getPoolId());
                if (pool.getPercentile()==0)
                    quantile = values.get(0);
                else if (pool.getPercentile() == 100)
                    quantile = values.get(values.size()-1);
                else {
                    int index = (int) (pool.getPercentile()/100 * (values.size()+1));
                    quantile = values.get(index);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quantile;
    }
}
