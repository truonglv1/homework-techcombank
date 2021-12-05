package com.tech.homework;

import com.tech.homework.constants.PoolStatus;
import okhttp3.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void testPost1(){
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject object = new JSONObject();
        try {
            object.put("poolId",123456);
            List<Integer> values = new ArrayList<>();
            values.add(1);
            values.add(2);
            object.put("poolValues",values);
            RequestBody body = RequestBody.create(mediaType, object.toString());
            Request request = new Request.Builder()
                    .url("http://localhost:8080/api/v1/pool")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            JSONObject res = new JSONObject(response.body().string());
            assertEquals(PoolStatus.INSERTED, res.getString("status"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPost2(){
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject object = new JSONObject();
        try {
            object.put("poolId",123456);
            object.put("percentile",99.5);

            RequestBody body = RequestBody.create(mediaType, object.toString());
            Request request = new Request.Builder()
                    .url("http://localhost:8080/api/v1/quantile")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            JSONObject res = new JSONObject(response.body().string());
            assertEquals(5, res.getString("quantile"));
            assertEquals(10, res.getString("total"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
