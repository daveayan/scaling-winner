Feature: Greeting V2
  As a user
  I want to create a Greeting
  So that I can be greeted

  Scenario Outline: Create a Greeting
    Given A Greeting V2 API
    When A Greeting V2 is created with "<greeting_name>"
    Then V2 API response code will be "<response_code>"
    And V2 API response greeting id will be "<response_id>"
    And V2 API response content will be "<response_content>"
    And V2 API response from will be "<response_from>"
    And V2 API response to will be "<response_to>"

    Examples:
      | greeting_name | response_code | response_id | response_content | response_from | response_to |
      | World         | 201           | 2           | World            | ABC           | XYZ         |
      | Other World   | 201           | 3           | Other World      | ABC           | XYZ         |
      | !@#$%^&       | 201           | 4           | !@#$%^&          | ABC           | XYZ         |
      |               | 201           | 5           |                  | ABC           | XYZ         |

  Scenario Outline: Get a Greeting
    Given A Greeting V2 API
    When I get the greeting V2 with ID "<greeting_id>"
    Then V2 API response code will be "<response_code>"
    And V2 API response greeting id will be "<response_id>"
    And V2 API response content will be "<response_content>"
    And V2 API response from will be "<response_from>"
    And V2 API response to will be "<response_to>"

    Examples:
      | greeting_id | response_code | response_id | response_content | response_from | response_to |
      | 2           | 200           | 2           | World            | ABC           | XYZ         |
      | 3           | 200           | 3           | Other World      | ABC           | XYZ         |
      | 4           | 200           | 4           | !@#$%^&          | ABC           | XYZ         |
      | 5           | 200           | 5           |                  | ABC           | XYZ         |
