package com.wavesplatform.steps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.internal.http.Status;
import com.jayway.restassured.response.Response;
import com.wavesplatform.MethodEnum;
import com.wavesplatform.response.addresses.Balance;
import io.qameta.allure.Step;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.jayway.restassured.RestAssured.given;
import static com.wavesplatform.TestVariables.*;

public class BackendSteps {

    private String token;

    private String URL = getProtocol().concat(getHost());

    private final int TIMEOUT = 60;


//    @Step("Send POST-request")
//    public <S, T> T sendPost(S requestData, Class<T> responseRes, MethodEnum method) throws UnsupportedEncodingException {
//        return sendPost(requestData, responseRes, method, null);
//    }

    @Step("Send POST-request")
    public <S, T> T sendPost(S requestData, Class<T> responseRes, MethodEnum method, String... args) throws UnsupportedEncodingException {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(requestData);
        Hooks.saveTextAttachment("Request Body Json <" + method + "> ", json);
        Response response = postRequest(json, method, args);

        String responseBody = response.getBody().asString();
        Hooks.saveTextAttachment("Response Body Json<" + method + "> ", responseBody);

        return deserialize(responseBody, responseRes);
    }

    @Step("Send POST-request and wait 200")
    public <S, T> T sendPostAndWaitSuccess(S requestData, Class<T> responseRes, MethodEnum method, Object[] args) throws UnsupportedEncodingException, InterruptedException, TimeoutException {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(requestData);
        Hooks.saveTextAttachment("Request Body Json <" + method + "> ", json);
        int i = 0;
        Response response;
        String responseBody = "";
        while (true) {
            response = postRequest(json, method, args);
            responseBody = response.getBody().asString();
            Hooks.saveTextAttachment("Response Body Json<" + method + "> ", responseBody);
            if (Status.SUCCESS.matches(response.statusCode())){
                break;
            }
            TimeUnit.SECONDS.sleep(1);
            if (++i == TIMEOUT) {
                throw new TimeoutException("Timed out after waiting for " + i + " seconds");
            }
        }

        Hooks.saveTextAttachment("Response Body Json<" + method + "> ", responseBody);
        return deserialize(responseBody, responseRes);
    }

    @Step("Send GET-request and wait 200")
    public <S, T> T sendGetAndWaitSuccess(Class<T> responseRes, MethodEnum method, String... args) throws UnsupportedEncodingException, InterruptedException, TimeoutException {
        Gson gson = new GsonBuilder().create();
        Hooks.saveTextAttachment("Request URL ", String.format(method.getMethodName(), args));

        int i = 0;
        Response response;
        while (true) {
            response = getRequest(method, args);
            if (Status.SUCCESS.matches(response.statusCode())){
                break;
            }
            TimeUnit.SECONDS.sleep(1);
            if (++i == TIMEOUT) {
                throw new TimeoutException("Timed out after waiting for " + i + " seconds");
            }
        }

        String responseBody = response.getBody().asString();
        Hooks.saveTextAttachment("Response Body Json<" + String.format(method.getMethodName(), args) + "> ", responseBody);
        return deserialize(responseBody, responseRes);
    }

    @Step("Выполняется get-запрос")
    public <T> T sendGet(Class<T> responseRes, MethodEnum method, String... args) throws UnsupportedEncodingException {
        Gson gson = new GsonBuilder().create();
        Response response = getRequest(method, args);

        String responseBody = response.getBody().asString();
        Hooks.saveTextAttachment("Response Body Json<" + method + ">", responseBody);
        return deserialize(responseBody, responseRes);
    }

    @Step("Deserialize child node")
    public <S, T> T deserialize(S requestData, Class<T> responseRes) throws UnsupportedEncodingException {
        Gson gson = new GsonBuilder()
                .create();
        String serialized = gson.toJson(requestData);
        T responseResult = deserialize(serialized, responseRes);
        return (T) responseResult;
    }

    public <T> T deserialize(String jsonString, Class<T> clazz) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(jsonString, clazz);
    }

    private void setToken(String token) {
        this.token = token;
    }

    private String getToken() {
        return token;
    }

    private Response getRequest(MethodEnum method, Object[] args) {
        return given()
                .contentType("application/json")
                .get(URL.concat(String.format(method.getMethodName(), args)));
    }

    private Response getRequest(MethodEnum method) {
        return given()
                .contentType("application/json")
                .get(URL.concat(method.getMethodName()));
    }

    private Response postRequest(String requestBody, MethodEnum method) {
        return given()
                .contentType("application/json")
                .body(requestBody)
                .post(URL.concat(method.getMethodName()));
    }

    private Response postRequest(String requestBody, MethodEnum method, Object[] args) {

        return given()
                .contentType("application/json")
                .body(requestBody)
                .post(URL.concat(String.format(method.getMethodName(), args)));
    }


}
