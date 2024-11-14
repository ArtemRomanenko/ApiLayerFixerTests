package steps;

import api.ApiClient;
import dto.ApiLayerLatestErrorDto;
import dto.ApiLayerLatestResponseDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.DateFormatter;

import java.time.OffsetDateTime;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ApiLayerLatestEndpointSteps {

    private final ApiClient apiClient;

    public ApiLayerLatestEndpointSteps(ApiClient apiClient) {
        this.apiClient = apiClient;
    }


    @When("User make a GET request to {string} with base currency {string} and symbols {string}")
    public void userSendRequestToLatestWithBaseAndSymbols(String path, String base, String symbols) {
        apiClient.response = apiClient.sendRequestWithBaseAndSymbols(path, base, symbols);
    }

    @Then("the response status should be {int}")
    public void validateResponseStatus(int statusCode) {
        assertEquals(statusCode, apiClient.response.getStatusCode());
    }

    @And("the response should contain the base currency {string}")
    public void verifyResponseContainsBaseCurrency(String baseCurrency) {
        ApiLayerLatestResponseDto responseDto = apiClient.response.then().extract().as(ApiLayerLatestResponseDto.class);
        assertEquals(baseCurrency, responseDto.getBase());
    }

    @And("the response should contain exchange rates for {string}")
    public void verifyResponseContainsRatesForSymbols(String symbols) {
        ApiLayerLatestResponseDto responseDto = apiClient.response.then().extract().as(ApiLayerLatestResponseDto.class);
        String[] splitSymbols = symbols.split(",");
        assertAll("symbols",
                () -> assertNotNull(responseDto.getRates().get(splitSymbols[0])),
                () -> assertNotNull(responseDto.getRates().get(splitSymbols[1])),
                () -> assertNotNull(responseDto.getRates().get(splitSymbols[2]))
        );
    }

    @And("the response should contain a valid timestamp")
    public void verifyTimeStampIsNotEmpty() {
        ApiLayerLatestResponseDto responseDto = apiClient.response.then().extract().as(ApiLayerLatestResponseDto.class);
        assertNotNull(responseDto.getTimestamp());
    }

    @When("User make a GET request to {string} without an API key")
    public void userSendRequestWithoutApiKey(String path) {
        apiClient.response = apiClient.sendRequestWithoutApiKey(path);
    }

    @And("the response should contain an error message {string}")
    public void verifyErrorMessage(String errorMessage) {
        assertTrue(apiClient.response.getStatusLine().contains(errorMessage));
    }

    @And("the response body should contain error code {int} and an error message {string}")
    public void verifyErrorMessageInBody(int errorCode, String errorMessage) {
        ApiLayerLatestErrorDto errorResponseDto = apiClient.response.then().extract().as(ApiLayerLatestErrorDto.class);
        assertAll("errors",
                () -> assertEquals(String.valueOf(errorCode), errorResponseDto.getError().get("code")),
                () -> assertEquals(errorMessage, errorResponseDto.getError().get("type")),
                () -> assertFalse(errorResponseDto.getSuccess())
        );
    }

    @And("the response should contain success True and current date")
    public void responseContainsSuccessValueAndCurrentDate() {
        ApiLayerLatestResponseDto responseDto = apiClient.response.then().extract().as(ApiLayerLatestResponseDto.class);
        String currentDate = DateFormatter.formattedDate(OffsetDateTime.now());
        assertAll("success",
                () -> assertTrue(responseDto.getSuccess()),
                () -> assertEquals(currentDate, responseDto.getDate())
        );
    }

    @When("User make a GET request to {string} with an invalid API key")
    public void userSendRequestWithInvalidApiKey(String path) {
        apiClient.response = apiClient.sendRequestWithInvalidApiKey(path);
    }

    @When("User make a GET request to {string} with base currency {string}")
    public void userSendRequestWithCurrency(String path, String baseCurrency) {
        apiClient.response = apiClient.sendRequestWithBaseOnly(path, baseCurrency);
    }

    @When("User make a GET request to {string}")
    public void userSendRequestWithoutBaseCurrency(String path) {
        apiClient.response = apiClient.sendRequestWithoutBaseAndSymbols(path);
    }

    @When("User make a GET request to {string} with symbols {string}")
    public void userSendRequestWithSymbolsOnly(String path, String symbols) {
        apiClient.response = apiClient.sendRequestWithSymbolsOnly(path, symbols);
    }

    @And("the response should contain exchange rates for all {int} available currencies")
    public void responseContainsAllExchangeRates(int numberOfAllRates) {
        ApiLayerLatestResponseDto responseDto = apiClient.response.then().extract().as(ApiLayerLatestResponseDto.class);
        assertEquals(numberOfAllRates, responseDto.getRates().size());
    }
}
