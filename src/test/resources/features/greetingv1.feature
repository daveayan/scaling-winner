Feature: Greeting V1
  As a user
  I want to create a Greeting
  So that I can be greeted

  Scenario Outline: Create a Greeting
    Given A Greeting V1 API
    When A Greeting V1 is created with "<greeting_name>"
    Then V1 API response code will be "<response_code>"
    And V1 API response greeting id will be "<response_id>"
    And V1 API response content will be "<response_content>"

    Examples:
      | greeting_name | response_code | response_id | response_content |
      | World         | 201           | 2           | World            |
      | Other World   | 201           | 3           | Other World      |
      | !@#$%^&       | 201           | 4           | !@#$%^&          |
      |               | 201           | 5           |                  |

  Scenario Outline: Get a Greeting
    Given A Greeting V1 API
    When I get the greeting V1 with ID "<greeting_id>"
    Then V1 API response code will be "<response_code>"
    And V1 API response greeting id will be "<response_id>"
    And V1 API response content will be "<response_content>"

    Examples:
      | greeting_id | response_code | response_id | response_content |
      | 2           | 200           | 2           | World            |
      | 3           | 200           | 3           | Other World      |
      | 4           | 200           | 4           | !@#$%^&          |
      | 5           | 200           | 5           |                  |
