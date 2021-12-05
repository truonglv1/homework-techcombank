package com.tech.homework.services;

import com.tech.homework.model.Pool;
import com.tech.homework.model.Response;

public interface ServiceHandler {
    String createPool(Pool pool);

    Response quantile(Pool pool);
}
