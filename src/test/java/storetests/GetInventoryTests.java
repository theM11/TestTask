package storetests;

import com.google.gson.Gson;
import org.example.helpers.APIResponse;
import org.example.helpers.Order;
import org.example.testdata.TestDataInit;
import okhttp3.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetInventoryTests {

    OkHttpClient client = new OkHttpClient();


    @BeforeAll
    static void runBeforeTests() {
        TestDataInit.setTestEnvironment();
        TestDataInit.createOrder();
    }

    @Test
    void getPetInventory_positive() {
        Request request = new Request.Builder()
                .url(System.getProperty("endpoint") + "/v2/store/inventory")
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            String respString = response.body().string();
            System.out.println(respString);
            Map resp = new Gson().fromJson(respString, Map.class);
            Assertions.assertNotNull(resp);
        } catch (IOException e) {
            System.err.println("GET PET response failed");
        }
    }

    @Test
    void getPurchaseOrderById_positive() {
        int orderId = 1;
        long petId = 9223372036854775313L;
        TestDataInit testDataInit = new TestDataInit();
        Order requestOrder = testDataInit.createNewPetOrder(petId, orderId); // compare object which was sent in different test

        Request request = new Request.Builder()
                .url(System.getProperty("endpoint") + "/v2/store/order/" + orderId)
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            String respString = response.body().string();
            System.out.println(respString);
            Order responseOrder = testDataInit.deserializeOrderRequest(respString);
            assertEquals(requestOrder.isComplete(), responseOrder.isComplete());
            assertEquals(requestOrder.getShipDate(), responseOrder.getShipDate());
            assertEquals(requestOrder.getPetId(), responseOrder.getPetId());
            assertEquals(requestOrder.getId(), responseOrder.getId());
            assertEquals(requestOrder.getStatus(), responseOrder.getStatus());
            assertEquals(requestOrder.getQuantity(), responseOrder.getQuantity());
        } catch (IOException e) {
            System.err.println("GET PET response failed");
        }
    }

    @Test
    void getPurchaseOrderById_invalid_id() {
        TestDataInit testDataInit = new TestDataInit();
        Order requestOrder = testDataInit.createInvalidOrder();
        Request getRequest = new Request.Builder()
                .url(System.getProperty("endpoint") + "/v2/store/order/" + requestOrder.getId())
                .get()
                .build();
        client = new OkHttpClient();
        Call getCall = client.newCall(getRequest);
        try (Response getResponse = getCall.execute()) {
            String resp = getResponse.body().string();
            APIResponse response = new Gson().fromJson(resp, APIResponse.class);
            assertEquals(requestOrder.getOrderNotFoundCode(), getResponse.code());
            assertEquals(requestOrder.getOrderNotFoundMessage(), response.getMessage());
        } catch (IOException get) {
            System.err.println("GET request failed");
        }
    }
}
