Feature: Item and shipping cost calculations
  Scenario: The user purchases 5 notebooks
    Given The user's name is "Bernard"
    And The user lives in "IL"
    And The user chooses "Standard" shipping
    And The user buys 5 "Notebooks" for 75.00
    Then The total cost is 397.50

  Scenario: The user purchases 1 pencil and 1 dog
    Given The user's name is "Sanders"
    And The user lives in "IL"
    And The user chooses "Standard" shipping
    And The user buys 1 "pencil" for 10.00
    And The user buys 1 "dog" for 50.00
    Then The total cost is 63.6

  Scenario: The user purchases one item for next-day shipping
    Given The user's name is "Robinette"
    And The user lives in "NY"
    And The user chooses "Next Day" shipping
    And The user buys 1 "fake college degree" for 100.00
    Then The total cost is 125.00