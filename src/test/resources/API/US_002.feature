@smoke
Feature: As a user, I want to access the ticket details of a specified user through the API connection using their ID.

  Scenario Outline: #TC1 :
  Verify that when a GET request with valid authorization information and correct data (id)
  is sent to the 'user/ticket/detail/{{id}}' endpoint, the returned status code is 200, and the success
  information in the response body is "true"

    Given The API user sets "user/ticket/detail/<id>" path parameters
    And The API user saves the response from the user ticket detail endpoint with valid authorization information
    Then The API user verifies that the status code is 200
    And The API user verifies that the success attribute in the response body is true

    Examples:
      | id  |
      | 161 |


  Scenario: #TC2 :
  Verify that when a GET request with valid authorization information and lacking the 'id' is sent
  to the 'user/ticket/detail/{{id}}' endpoint, the returned status code is 203, and the message information
  in the response body is "No id"

    Given The API user sets "user/ticket/detail" path parameters
    And The API user saves the response from the user ticket detail endpoint with valid authorization information
    Then The API user verifies that the status code is 203
    And The API User verifies that the message information in the response body is "No id"


  Scenario Outline: #TC3 :
  Verify that when a GET request with valid authorization information and containing an (id)
  that does not exist in the records is sent to the 'user/ticket/detail/{{id}}' endpoint, the returned status
  code is 203, and the message information in the response body is "No ticket."

    Given The API user sets "user/ticket/detail/<id>" path parameters
    And The API user saves the response from the user ticket detail endpoint with valid authorization information
    Then The API user verifies that the status code is 203
    And The API User verifies that the message information in the response body is "No ticket."


    Examples:
      | id  |
      | 777 |


  Scenario Outline: #TC4 :
  Verify that when a GET request with invalid authorization information is sent to the
  'user/ticket/detail/{{id}}' endpoint, the returned status code is 401, and the error message in the
  response body is "Unauthorized request"

    Given The API user sets "user/ticket/detail/<id>" path parameters
    Then The API user records the response with invalid authorization information, verifies that the status code is '401' and confirms that the error information is Unauthorized

    Examples:
      | id  |
      | 161 |


  Scenario Outline: #TC5 :
  The contents of the data (id, user_id, name, email, ticket, subject, status, priority,
  last_reply, created_at, updated_at) within the response body should be verified

    Given The API user sets "user/ticket/detail/<id>" path parameters
    And The API user saves the response from the user ticket detail endpoint with valid authorization information
    Then The API user verifies that the content of the data field in the response body includes <id>, <user_id>, "<name>", "<email>", "<ticket>", "<subject>", <status>, <priority>, "<last_reply>", "<created_at>", "<updated_at>"

    Examples:
      | id  | user_id | name         | email                 | ticket | subject       | status  | priority  | last_reply          | created_at                  | updated_at                 |
      | 161 | 19      | beyza nergiz | beyzanergiz@gmail.com | 687946  | Test Ticket  | 0       | 0         | 2023-12-28 02:16:39 | 2023-12-28T07:16:39.000000Z | 2023-12-28T07:16:39.000000Z |