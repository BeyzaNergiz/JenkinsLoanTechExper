@smoke
Feature: As a user, I want to be able to create a new user ticket record through API connection.

 Scenario: When a POST request with valid authorization information and correct data (subject, priority, message)
 is sent to the user/ticket/add endpoint, the returned status code should be 200, and the message in the response
 body should be verified as "Ticket opened successfully!"

  Given The API user sets "user/ticket/add" path parameters
  And The API user prepares a POST request containing the correct data to send to the user ticket add endpoint
  When The API user sends a POST request and saves the response from the user ticket add endpoint with valid authorization information
  Then The API user verifies that the status code is 200
  And The API user verifies that the message information in the response body is "Ticket opened successfully!"


 Scenario: When a POST request with valid authorization information and no data (subject, priority, message)
 is sent to the user/ticket/add endpoint, the returned status code should be 203, and the remark in the response
 body should be verified as "failed"

  Given The API user sets "user/ticket/add" path parameters
  And The API user prepares a POST request without data to send to the user ticket add endpoint
  When The API user sends a POST request and saves the response from the user ticket add endpoint with valid authorization information
  Then The API user verifies that the status code is 203
  And The API user verifies that the remark information in the response body is "failed"


 Scenario: When a POST request with valid authorization information and incomplete missing data
 (priority,message) is sent to the user/ticket/add endpoint, the returned status code should be 203,
 and the remark in the response body should be verified as "failed"

  Given The API user sets "user/ticket/add" path parameters
  And The API user prepares a POST request with missing data to send to the user ticket add endpoint.
  When The API user sends a POST request and saves the response from the user ticket add endpoint with valid authorization information
  Then The API user verifies that the status code is 203
  And The API user verifies that the remark information in the response body is "failed"


 Scenario: Verify that when a POST request with invalid authorization information and correct data
 (subject, priority, message) is sent to the 'user/ticket/add' endpoint, the returned status code is 401,
 and the error message in the response body is "Unauthorized request"

  Given The API user sets "user/ticket/add" path parameters
  And The API user prepares a POST request containing the correct data to send to the user ticket add endpoint
  When The API user sends a POST request and saves the response from the user ticket add endpoint with invalid authorization information
  Then The API user verifies that the status code is 401
  And The API user verifies that the error information in the response body is "Unauthorized request"


 Scenario Outline: The creation of a new ticket record intended to be generated through the API should be
 verified. This can be confirmed by sending a GET request to the 'user/ticket/detail/{{id}}' endpoint with
 the Opened ticked id returned in the response body, thus validating that the record has been created

  Given The API user sets "user/ticket/detail/<id>" path parameters
  And The API user saves the response from the user ticket detail endpoint with valid authorization information
  Then The API user verifies that the status code is 200
  And The API user verifies that the success attribute in the response body is true
  Then The API user verifies that the id information in the response body is <valueId>

  Examples:
   | id  | valueId |
   | 450| 450    |

