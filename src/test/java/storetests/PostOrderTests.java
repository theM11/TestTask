package storetests;

import com.google.gson.Gson;
import org.example.helpers.APIResponse;
import org.example.helpers.Order;
import org.example.testdata.TestDataInit;
import okhttp3.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostOrderTests {
    OkHttpClient client = new OkHttpClient();

    @BeforeAll
    static void runBeforeTests() {
        TestDataInit.setTestEnvironment();
    }

    @Test
    void postPetOrder_positive() {
        System.out.println(getClass().getCanonicalName());
        long id = 9223372036854775314L;
        int orderId = 2;
        TestDataInit testDataInit = new TestDataInit();
        MediaType mediaType = MediaType.parse("application/json");
        Order requestOrder = testDataInit.createNewPetOrder(id, orderId);
        RequestBody body = RequestBody.create(testDataInit.buildRequest(requestOrder), mediaType);
        Request request = new Request.Builder()
                .url(System.getProperty("endpoint") + "/v2/store/order")
                .post(body)
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            String respString = response.body().string();
            System.out.println(respString);
            Order orderResponse = testDataInit.deserializeOrderRequest(respString);
            assertEquals(requestOrder.getId(), orderResponse.getId());
            assertEquals(requestOrder.getPetId(), orderResponse.getPetId());
            assertEquals(requestOrder.getShipDate(), orderResponse.getShipDate());
            assertEquals(requestOrder.getQuantity(), orderResponse.getQuantity());
            assertEquals(requestOrder.getStatus(), orderResponse.getStatus());
            assertEquals(requestOrder.isComplete(), orderResponse.isComplete());
        } catch (IOException e) {
            System.err.println("GET PET response failed");
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 3, 5, 8})
    void postMultiplePetOrder_positive(int orderIds) {
        long id = 9223372036854775314L;
        TestDataInit testDataInit = new TestDataInit();
        MediaType mediaType = MediaType.parse("application/json");
        Order requestOrder = testDataInit.createNewPetOrder(id, orderIds);
        RequestBody body = RequestBody.create(testDataInit.buildRequest(requestOrder), mediaType);
        Request request = new Request.Builder()
                .url(System.getProperty("endpoint") + "/v2/store/order")
                .post(body)
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            String respString = response.body().string();
            System.out.println(respString);
            Order orderResponse = testDataInit.deserializeOrderRequest(respString);
            assertEquals(requestOrder.getId(), orderResponse.getId());
            assertEquals(requestOrder.getPetId(), orderResponse.getPetId());
            assertEquals(requestOrder.getShipDate(), orderResponse.getShipDate());
            assertEquals(requestOrder.getQuantity(), orderResponse.getQuantity());
            assertEquals(requestOrder.getStatus(), orderResponse.getStatus());
            assertEquals(requestOrder.isComplete(), orderResponse.isComplete());
        } catch (IOException e) {
            System.err.println("GET PET response failed");
        }
    }

    @Test
    void postPetOrder_invalid_order_negative() {
        //long id = 9223372036854775314L;
        TestDataInit testDataInit = new TestDataInit();
        MediaType mediaType = MediaType.parse("application/json");
        Order requestOrder = testDataInit.createInvalidOrder();
        RequestBody body = RequestBody.create(testDataInit.buildRequest(requestOrder), mediaType);
        Request request = new Request.Builder()
                .url(System.getProperty("endpoint") + "/v2/store/order")
                .post(body)
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            String responseBodyString = response.body().string();
            assertEquals(requestOrder.getInvalidOrderCode(), response.code());
            APIResponse resp = new Gson().fromJson(responseBodyString, APIResponse.class); //
        } catch (IOException e) {
            System.err.println("GET PET response failed");
        }
    }

}
