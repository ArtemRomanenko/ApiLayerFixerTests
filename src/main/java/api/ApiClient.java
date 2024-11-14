package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.PropertyReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public Response response;
    private final PropertyReader reader = new PropertyReader();
    private final String baseUri = reader.getProperty("base.uri");
    private final String apiKey = System.getenv("API_KEY");


    private RequestSpecification getRequestSpecification(String basePath) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.PARAMS)
                .log(LogDetail.URI)
                .build();
    }

    public Response sendRequestWithBaseAndSymbols(String path, String base, String symbols) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("base", base);
        queryParams.put("symbols", symbols);

        return given()
                .spec(getRequestSpecification(path))
                .header("apikey", apiKey)
                .queryParams(queryParams)
                .get();
    }

    public Response sendRequestWithBaseOnly(String path, String base) {
        return given()
                .spec(getRequestSpecification(path))
                .header("apikey", apiKey)
                .queryParam("base", base)
                .get();
    }

    public Response sendRequestWithoutBaseAndSymbols(String path) {
        return given()
                .spec(getRequestSpecification(path))
                .header("apikey", apiKey)
                .get();
    }

    public Response sendRequestWithSymbolsOnly(String path, String symbols) {
        return given()
                .spec(getRequestSpecification(path))
                .header("apikey", apiKey)
                .queryParam("symbols", symbols)
                .get();
    }

    public Response sendRequestWithoutApiKey(String path) {
        return given()
                .spec(getRequestSpecification(path))
                .get();
    }

    public Response sendRequestWithInvalidApiKey(String path) {
        String invalidApiKey = "R9jW5LmDZpTrVy8o27XUB4KJtQ1P83rE";
        return given()
                .spec(getRequestSpecification(path))
                .header("apikey", invalidApiKey)
                .get();
    }
}
