Feature: Real-time Exchange Rate Data Retrieval
  As a user
  I want to retrieve real-time exchange rates for specific currencies

  @general
  Scenario: Successful request with valid parameters
    When User make a GET request to "/latest" with base currency "USD" and symbols "EUR,GBP,JPY"
    Then the response status should be 200
    And the response should contain the base currency "USD"
    And the response should contain exchange rates for "EUR,GBP,JPY"
    And the response should contain a valid timestamp
    And the response should contain success True and current date

  @authentication
  Scenario: Missing API key
    When User make a GET request to "/latest" without an API key
    Then the response status should be 401
    And the response should contain an error message "Unauthorized"

  Scenario: Invalid API key
    When User make a GET request to "/latest" with an invalid API key
    Then the response status should be 401
    And the response should contain an error message "Unauthorized"

  @parameter_base
  Scenario: Valid base currency parameter
    When User make a GET request to "/latest" with base currency "EUR"
    Then the response status should be 200
    And the response should contain the base currency "EUR"

  Scenario: Invalid base currency parameter
    When User make a GET request to "/latest" with base currency "XYZ"
    Then the response status should be 200
    And the response body should contain error code 201 and an error message "invalid_base_currency"

  Scenario: No base currency parameter
    When User make a GET request to "/latest"
    Then the response status should be 200
    And the response should contain the base currency "EUR"
    And the response should contain exchange rates for all 171 available currencies

  @parameter_symbols
  Scenario: Valid symbols parameter
    When User make a GET request to "/latest" with symbols "AUD,JPY,CAD"
    Then the response status should be 200
    And the response should contain exchange rates for "AUD,JPY,CAD"

  Scenario: Invalid symbols parameter
    When User make a GET request to "/latest" with symbols "XXX,YYY"
    Then the response status should be 200
    And the response body should contain error code 202 and an error message "invalid_currency_codes"

  @negative
  Scenario: Malformed URL
    When User make a GET request to "/"
    Then the response status should be 404
    And the response should contain an error message "Not Found"