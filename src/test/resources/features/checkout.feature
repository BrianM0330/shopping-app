Feature: Test Checkout Functionality
  Scenario: The user checks out a cart
    Given The user's name is "Sanders"
    And The user lives in "CA"
    And The user chooses "Next Day" shipping
    And The user buys 1 "Gaming PC" for 10000.00
    Then The user checks out