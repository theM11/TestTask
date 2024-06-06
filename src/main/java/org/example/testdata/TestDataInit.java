package org.example.testdata;

import com.google.gson.GsonBuilder;
import org.example.helpers.Order;
import okhttp3.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDataInit {

    /**
     * Possible to load some test data source which makes create methods dynamic
     */
    public TestDataInit() {

    }


    public static void setTestEnvironment() {
        if(System.getProperty("environment").equals("Test")){
            String endpoint = "https://petstore.swagger.io";
            System.setProperty("endpoint", endpoint);
        }
    }

    public static void createOrder() {
        long id = 9223372036854775314L;
        int orderId = 1;
        TestDataInit testDataInit = new TestDataInit();
        MediaType mediaType = MediaType.parse("application/json");
        Order requestOrder = testDataInit.createNewPetOrder(id, orderId);
        RequestBody body = RequestBody.create(testDataInit.buildRequest(requestOrder), mediaType);
        Request request = new Request.Builder()
                .url(System.getProperty("endpoint") + "/v2/store/order")
                .post(body)
                .build();

        Call call = new OkHttpClient().newCall(request);
        try (Response response = call.execute()) {
            String respString = response.body().string();
            System.out.println("[Test Data]: Creating new Order with Id: " + orderId);
        } catch (IOException e) {
            System.err.println("GET PET response failed");
        }
    }

    private String getShipDate() {
        //2024-05-29T07:20:03.272Z
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.sss'Z'");
        return simpleDateFormat.format(date);
    }

    public Order createNewPetOrder(long petId, int orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setPetId(petId);
        order.setQuantity(2);
        order.setShipDate(getShipDate());
        order.setStatus("placed");
        order.setComplete(false);
        //return new CustomGsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(order);
        return order;
    }

    public Order createInvalidOrder() {
        Order order = new Order();
        order.setShipDate("");
        order.setStatus("");
        order.setId(-1);
        return order;
    }

    public String buildRequest(Order order) {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJson(order, Order.class);
    }

    public Order deserializeOrderRequest(String response) {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .fromJson(response, Order.class);
    }
}
