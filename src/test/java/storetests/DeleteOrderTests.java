package storetests;

import org.example.testdata.TestDataInit;
import com.google.gson.Gson;
import org.example.helpers.APIResponse;
import org.example.helpers.Order;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DeleteOrderTests {

    OkHttpClient client = new OkHttpClient();

    @BeforeAll
    static void runBeforeTests() {
        TestDataInit.setTestEnvironment();
        TestDataInit.createOrder();
    }

    @Test
    void deleteOrder_positive() {
        System.out.println(this.getClass().getPackageName());
        System.out.println(this.getClass().getPackage());
        int orderId = 1;
        APIResponse expectedResponse = new APIResponse();
        expectedResponse.setCode(200);
        Request request = new Request.Builder()
                .url(System.getProperty("endpoint") + "/v2/store/order/" + orderId)
                .delete()
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            String resp = response.body().string();
            System.out.println(resp);
            assertEquals(expectedResponse.getCode(), response.code());
        } catch (IOException e) {
            System.err.println("GET PET response failed");
        }
    }

    @Test
    void deleteOrder_order_not_found_negative() {
        int orderId = -1;
        Order order = new Order();
        Request request = new Request.Builder()
                .url(System.getProperty("endpoint") + "/v2/store/order/" + orderId)
                .delete()
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            String resp = response.body().string();
            APIResponse apiResponse = new Gson().fromJson(resp, APIResponse.class);
            assertEquals(order.getOrderNotFoundCode(), response.code());
            assertEquals(order.getOrderNotFoundMessage(), apiResponse.getMessage());
        } catch (IOException e) {
            System.err.println("GET PET response failed");
        }
    }

    @Test
    void deleteOrder_invalid_id_negative() {
        int orderId = -58;
        Order order = new Order();
        Request request = new Request.Builder()
                .url(System.getProperty("endpoint") + "/v2/store/order/" + orderId)
                .delete()
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            String resp = response.body().string();
            System.out.println(resp);
            APIResponse apiResponse = new Gson().fromJson(resp, APIResponse.class);
            assertEquals(order.getOrderNotFoundCode(), apiResponse.getCode());
            assertEquals(order.getOrderNotFoundMessage(), apiResponse.getMessage());
        } catch (IOException e) {
            System.err.println("GET PET response failed");
        }
    }

}
