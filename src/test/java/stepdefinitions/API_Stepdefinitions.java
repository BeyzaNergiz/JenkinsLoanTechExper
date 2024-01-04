package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import utilities.Authentication;
import utilities.ConfigReader;

import java.util.Arrays;

import static hooks.HooksAPI.spec;
import static io.restassured.RestAssured.given;


public class API_Stepdefinitions {
    public static String fullPath;
    Response response;
    String mesaj;
    JsonPath jsonPath;
    JSONObject requestBody;

    @Given("The API user sets {string} path parameters")
    public void the_apı_user_sets_path_parameters(String rawPaths) {
        String[] paths = rawPaths.split("/");

        System.out.println(Arrays.toString(paths));

        StringBuilder tempPath = new StringBuilder("/{");


        for (int i = 0; i < paths.length; i++) {

            String key = "pp" + i;
            String value = paths[i].trim();

            spec.pathParam(key, value);

            tempPath.append(key + "}/{");
        }
        tempPath.deleteCharAt(tempPath.lastIndexOf("/"));
        tempPath.deleteCharAt(tempPath.lastIndexOf("{"));

        fullPath = tempPath.toString();
        System.out.println("fullPath = " + fullPath);
    }

    @And("The API user saves the response from the user ticket list endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserTicketListEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("user"))
                .when()
                .get(fullPath);

        response.prettyPrint();
    }

    @Then("The API user verifies that the status code is {int}")
    public void theAPIUserVerifiesThatTheStatusCodeIs(int status) {
        response.then()
                .assertThat()
                .statusCode(status);
    }

    @And("The API user verifies that the remark information in the response body is {string}")
    public void theAPIUserVerifiesThatTheRemarkInformationInTheResponseBodyIs(String remark) {
        response.then()
                .assertThat()
                .body("remark", Matchers.equalTo(remark));
    }


    @Then("The API user records the response with invalid authorization information, verifies that the status code is '401' and confirms that the error information is Unauthorized")
    public void theAPIUserRecordsTheResponseWithInvalidAuthorizationInformationVerifiesThatTheStatusCodeIsAndConfirmsThatTheErrorInformationIsUnauthorized() {
        try {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .get(fullPath);
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));
    }

    @Then("Verify the information of the one with the id {int} in the API user response body: {int}, {string}, {string}, {string}, {string}, {int}, {int}, {string}, {string}, {string}")
    public void verify_the_information_of_the_one_with_the_id_in_the_apı_user_response_body(int dataIndex, int user_id, String name, String email, String ticket, String subject, int status, int priority, String last_reply, String created_at, String updated_at) {
        jsonPath = response.jsonPath();

        Assert.assertEquals(user_id, jsonPath.getInt("data[" + dataIndex + "].user_id"));
        Assert.assertEquals(name, jsonPath.getString("data[" + dataIndex + "].name"));
        Assert.assertEquals(email, jsonPath.getString("data[" + dataIndex + "].email"));
        Assert.assertEquals(ticket, jsonPath.getString("data[" + dataIndex + "].ticket"));
        Assert.assertEquals(subject, jsonPath.getString("data[" + dataIndex + "].subject"));
        Assert.assertEquals(status, jsonPath.getInt("data[" + dataIndex + "].status"));
        Assert.assertEquals(priority, jsonPath.getInt("data[" + dataIndex + "].priority"));
        Assert.assertEquals(last_reply, jsonPath.getString("data[" + dataIndex + "].last_reply"));
        Assert.assertEquals(created_at, jsonPath.getString("data[" + dataIndex + "].created_at"));
        Assert.assertEquals(updated_at, jsonPath.getString("data[" + dataIndex + "].updated_at"));
    }

    @And("The API user saves the response from the user ticket detail endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserTicketDetailEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("user"))
                .when()
                .get(fullPath);

        response.prettyPrint();
    }

    @And("The API user verifies that the success attribute in the response body is true")
    public void theAPIUserVerifiesThatTheSuccessAttributeInTheResponseBodyIsTrue() {
        response.then()
                .assertThat()
                .body("success", Matchers.equalTo(true));
    }

    @And("The API User verifies that the message information in the response body is {string}")
    public void theAPIUserVerifiesThatTheMessageInformationInTheResponseBodyIs(String message) {
        response.then()
                .assertThat()
                .body("data.message", Matchers.equalTo(message));
    }

    @Then("The API user verifies that the content of the data field in the response body includes {int}, {int}, {string}, {string}, {string}, {string}, {int}, {int}, {string}, {string}, {string}")
    public void the_api_user_verifies_that_the_content_of_the_data_field_in_the_response_body_includes(int id, int user_id, String name, String email, String ticket, String subject, int status, int priority, String last_reply, String created_at, String updated_at) {
        jsonPath = response.jsonPath();

        Assert.assertEquals(id, jsonPath.getInt("data.id"));
        Assert.assertEquals(user_id, jsonPath.getInt("data.user_id"));
        Assert.assertEquals(name, jsonPath.getString("data.name"));
        Assert.assertEquals(email, jsonPath.getString("data.email"));
        Assert.assertEquals(ticket, jsonPath.getString("data.ticket"));
        Assert.assertEquals(subject, jsonPath.getString("data.subject"));
        Assert.assertEquals(status, jsonPath.getInt("data.status"));
        Assert.assertEquals(priority, jsonPath.getInt("data.priority"));
        Assert.assertEquals(last_reply, jsonPath.getString("data.last_reply"));
        Assert.assertEquals(created_at, jsonPath.getString("data.created_at"));
        Assert.assertEquals(updated_at, jsonPath.getString("data.updated_at"));
    }

    @And("The API user prepares a POST request containing the correct data to send to the user ticket add endpoint")
    public void theAPIUserPreparesAPOSTRequestContainingTheCorrectDataToSendToTheUserTicketAddEndpoint() {
        /*
        {
    "subject":"Test Ticket",
    "priority":"Medium",
    "message":"Test Ticket Message"
}
         */
        requestBody = new JSONObject();
        requestBody.put("subject", "Test Ticket");
        requestBody.put("priority", "Medium");
        requestBody.put("message", "Test Ticket Message");

    }

    @When("The API user sends a POST request and saves the response from the user ticket add endpoint with valid authorization information")
    public void theAPIUserSendsAPOSTRequestAndSavesTheResponseFromTheUserTicketAddEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("user"))
                .when()
                .body(requestBody.toString())
                .post(fullPath);

        response.prettyPrint();
    }

    @Then("The API user verifies that the message information in the response body is {string}")
    public void the_api_user_verifies_that_the_message_information_in_the_response_body_is(String message) {
        response.then()
                .assertThat()

                .body("message", Matchers.equalTo(message));


    }

    @And("The API user prepares a POST request without data to send to the user ticket add endpoint")
    public void theAPIUserPreparesAPOSTRequestWithoutDataToSendToTheUserTicketAddEndpoint() {
        requestBody = new JSONObject();
    }

    @And("The API user prepares a POST request with missing data to send to the user ticket add endpoint.")
    public void theAPIUserPreparesAPOSTRequestWithMissingDataToSendToTheUserTicketAddEndpoint() {
        requestBody = new JSONObject();
        requestBody.put("subject", "Test Ticket");
    }

    @When("The API user sends a POST request and saves the response from the user ticket add endpoint with invalid authorization information")
    public void theAPIUserSendsAPOSTRequestAndSavesTheResponseFromTheUserTicketAddEndpointWithInvalidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                .when()
                .body(requestBody.toString())
                .post(fullPath);

        response.prettyPrint();
    }

    @And("The API user verifies that the error information in the response body is {string}")
    public void theAPIUserVerifiesThatTheErrorInformationInTheResponseBodyIs(String error) {
        response.then()
                .assertThat()
                .body("message.error[0]", Matchers.equalTo(error));
    }

    @Then("The API user verifies that the id information in the response body is {int}")

    public void the_api_user_verifies_that_the_id_information_in_the_response_body_is(int id) {


        jsonPath = response.jsonPath();

        Assert.assertEquals(id, jsonPath.getInt("data.id"));
    }

    @And("The API user saves the response from the user ticket close endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserTicketCloseEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("user"))
                .when()
                .patch(fullPath);

        response.prettyPrint();
    }


    @Then("The API user prepares a PATCH request containing the correct data to send to the user profile endpoint")
    public void theAPIUserPreparesAPATCHRequestContainingTheCorrectDataToSendToTheUserProfileEndpoint() {
        /*
        {
    "firstname":"Mehmet",
    "lastname" :"Genç",
    "address" :"New York",
    "state":"New York City",
    "zip":"125874",
    "city":"New York City"
}
         */
        requestBody=new JSONObject();
        requestBody.put("firstname","Nergiz");
        requestBody.put("lastname","Erdem");
        requestBody.put("address","New York");
        requestBody.put("state","New York City");
        requestBody.put("zip","125874");
        requestBody.put("city","New York City");


    }
    @And("The API user saves the response from the user profile endpoint with admin valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserProfileEndpointWithAdminValidAuthorizationInformation() {

        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("user"))
                .when()
                .body(requestBody.toString())
                .patch(fullPath);

        response.prettyPrint();
    }

    @And("The API user saves the response from the api loanplans details endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheApiLoanplansDetailsEndpointWithValidAuthorizationInformation() {

    }










































































































































































































    // AYSEGUL
    @And("The API user saves the response from the user list loan detail endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserListLoanDetailEndpointWithValidAuthorizationInformation() {

        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("user"))
                .when()
                .get(fullPath);

        response.prettyPrint();
    }


    @And("The API user saves the response from the user list loan endpoint with invalid authorization information, verifies that the status code is {int} and confirms that the error information in is {string}")
    public void theAPIUserSavesTheResponseFromTheUserListLoanEndpointWithInvalidAuthorizationInformationVerifiesThatTheStatusCodeIsAndConfirmsThatTheErrorInformationInIs(int arg0, String arg1) {
        try {
            response = given()
                    .spec(spec)
                    .contentType(ContentType.JSON)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .get(fullPath);
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized request"));

    }


    @Then("The API user verifies that the id information in the response body is \\{int}")
    public void theAPIUserVerifiesThatTheIdInformationInTheResponseBodyIsInt(int id) {
        jsonPath = response.jsonPath();
        Assert.assertEquals(id, jsonPath.getInt("data.id"));
    }


        @Given("The API user saves the response from the api withdrawal approve endpoint with valid authorization information")
        public void the_api_user_saves_the_response_from_the_api_withdrawal_approve_endpoint_with_valid_authorization_information
        () {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                    .when()
                    .patch(fullPath);

            response.prettyPrint();

        }
        @Given("The API user saves the response from the api withdrawal approve endpoint with invalid authorization information")
        public void the_apı_user_saves_the_response_from_the_api_withdrawal_approve_endpoint_with_invalid_authorization_information
        () {
            try {
                response = given()
                        .spec(spec)
                        .header("Accept", "application/json")
                        .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                        .when()
                        .patch(fullPath);

                response.prettyPrint();
            } catch (Exception e) {

                System.out.println("mesaj: " + mesaj);

                Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));

            }

        }


        @And("The API user saves the response from the api subscriber delete endpoint with valid authorization information")
        public void theAPIUserSavesTheResponseFromTheApiSubscriberDeleteEndpointWithValidAuthorizationInformation () {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                    .when()
                    .delete(fullPath);

            response.prettyPrint();

        }


    @Then("The API user saves the response from the api subscriber delete endpoint with invalid authorization information and confirms that the status code is '401' and the error message is Unauthorized")
    public void theAPIUserSavesTheResponseFromTheApiSubscriberDeleteEndpointWithInvalidAuthorizationInformationAndConfirmsThatTheStatusCodeIsAndTheErrorMessageIsUnauthorized() {

    }

    @Then("The API user saves the response from the user ticket close endpoint with invalid authorization information and verifies that the status code is '401' and the error message is Unauthorized")
    public void theAPIUserSavesTheResponseFromTheUserTicketCloseEndpointWithInvalidAuthorizationInformationAndVerifiesThatTheStatusCodeIsAndTheErrorMessageIsUnauthorized() {
        try {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .patch(fullPath);

            response.prettyPrint();
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));

    }

    @And("The API user Verifies that the status information in the response body is {int}")
    public void theAPIUserVerifiesThatTheStatusInformationInTheResponseBodyIsStatus(int status) {

        jsonPath = response.jsonPath();

        Assert.assertEquals(status, jsonPath.getInt("data.status"));
    }

    @And("The API user saves the response from the user ticket delete endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserTicketDeleteEndpointWithValidAuthorizationInformation() {

        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("user"))
                .when()
                .delete(fullPath);

        response.prettyPrint();
    }

    @Then("The API user saves the response from the user ticket delete endpoint with invalid authorization information and confirms that the status code is '401' and the error message is Unauthorized")
    public void theAPIUserSavesTheResponseFromTheUserTicketDeleteEndpointWithInvalidAuthorizationInformationAndConfirmsThatTheStatusCodeIsAndTheErrorMessageIsUnauthorized() {

        try {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .delete(fullPath);

            response.prettyPrint();
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));

    }























































































































































































































































































































































































































































































































































































































































































































    // Mustafa API
    @And("The API user saves the response from the api categories list endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheApiCategoriesListEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .get(fullPath);

        response.prettyPrint();
    }

    @And("The API user saves the response from the api categories list endpoint with invalid authorization information and verifies that the status code is {string} and the error message is Unauthorized")
    public void theAPIUserSavesTheResponseFromTheApiCategoriesListEndpointWithInvalidAuthorizationInformationAndVerifiesThatTheStatusCodeIsAndTheErrorMessageIsUnauthorizedRequest() {
        try {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .get(fullPath);
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));
    }

    @And("The API user saves the response from the api categories list endpoint with invalid authorization information")
    public void theAPIUserSavesTheResponseFromTheApiCategoriesListEndpointWithInvalidAuthorizationInformation() {
        try {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .get(fullPath);
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));
    }

    @And("The API user saves the response from the user api subscribe add endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserApiSubscribeAddEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .body(requestBody.toString())
                .post(fullPath);

        response.prettyPrint();
    }

    @Then("The API user prepares a POST request containing the correct data to send to the api subscriber add endpoint")
    public void theAPIUserPreparesAPOSTRequestContainingTheCorrectDataToSendToTheApiSubcriberAddEndpoint() {
        requestBody=new JSONObject();
        requestBody.put("email","megenc@gmail.com");
    }

    @Given("Then Verify the information of the one with the id {int} in the API user response body: {int}, {string}, {string}, {string}, {int}, {string}, {string}")
    public void thenVerifyTheInformationOfTheOneWithTheIdInTheAPIUserResponseBody(int dataIndex, int id, String name, String image, String description, int status, String created_at, String updated_at) {
        jsonPath = response.jsonPath();

        Assert.assertEquals(id, jsonPath.getInt("data[" + dataIndex + "].id"));
        Assert.assertEquals(name, jsonPath.getString("data[" + dataIndex + "].name"));
        Assert.assertEquals(image, jsonPath.getString("data[" + dataIndex + "].image"));
        Assert.assertEquals(description, jsonPath.getString("data[" + dataIndex + "].description"));
        Assert.assertEquals(status, jsonPath.getInt("data[" + dataIndex + "].status"));
        Assert.assertEquals(created_at, jsonPath.getString("data[" + dataIndex + "].created_at"));
        Assert.assertEquals(updated_at, jsonPath.getString("data[" + dataIndex + "].updated_at"));
    }

    @Then("The API user prepares a POST request containing the incorrect data to send to the api subscriber add endpoint")
    public void theAPIUserPreparesAPOSTRequestContainingTheIncorrectDataToSendToTheApiSubcriberAddEndpoint() {
        requestBody=new JSONObject();
        requestBody.put("emaill","emailemail.com");
    }

    @And("The API user saves the response from the user api subsscribe details endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserApiSubscribeDetailsEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .get(fullPath);

        response.prettyPrint();
    }

    @Then("The API user prepares a POST request containing empty data to send to the api subcriber add endpoint")
    public void theAPIUserPreparesAPOSTRequestContainingEmptyDataToSendToTheApiSubcriberAddEndpoint() {
        requestBody=new JSONObject();

    }

    @When("The API user sends a POST request and saves the response from the api subscriber add endpoint with invalid authorization information")
    public void theAPIUserSendsAPOSTRequestAndSavesTheResponseFromTheApiSubscriberAddEndpointWithInvalidAuthorizationInformation() {
        try {
            response = given()
                    .spec(spec)
                    .contentType(ContentType.JSON)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .body(requestBody.toString())
                    .post(fullPath);
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));
    }

    @And("The API user saves the response from the user api withdrawal delete endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserApiWithdrawalDeleteEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .delete(fullPath);

        response.prettyPrint();
    }

    @Then("The API user saves the response from the api withdrawal delete id endpoint with invalid authorization information and confirms that the status code is {string} and the error message is Unauthorized")
    public void theAPIUserSavesTheResponseFromTheApiWithdrawalDeleteEndpointWithInvalidAuthorizationInformationAndConfirmsThatTheStatusCodeIsAndTheErrorMessageIsUnauthorized() {
        try {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .delete(fullPath);
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));
    }

    @Then("The API user saves the response from the user api withdrawal delete id endpoint with invalid authorization information and verifies that the status code is {string} and the error message is Unauthorized")
    public void theAPIUserSavesTheResponseFromTheUserApiWithdrawalDeleteIdEndpointWithInvalidAuthorizationInformationAndVerifiesThatTheStatusCodeIsAndTheErrorMessageIsUnauthorized(int id) {
        try {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .delete(fullPath);
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));
    }
    @Given("Then Verify the information of the one with the id {int} in the API user response body: {int}, {string}, {string}, {int}, {string}, {string}")
    public void then_verify_the_information_of_the_one_with_the_id_in_the_apı_user_response_body(int dataIndex, int id, String name, String description, int status, String created_at, String updated_at) {

        jsonPath = response.jsonPath();
        Assert.assertEquals(id, jsonPath.getInt("data[" + dataIndex + "].id"));
        Assert.assertEquals(name, jsonPath.getString("data[" + dataIndex + "].name"));
        Assert.assertNull( jsonPath.getString("data[" + dataIndex + "].image"));
        Assert.assertEquals(description, jsonPath.getString("data[" + dataIndex + "].description"));
        Assert.assertEquals(status, jsonPath.getInt("data[" + dataIndex + "].status"));
        Assert.assertEquals(created_at, jsonPath.getString("data[" + dataIndex + "].created_at"));
        Assert.assertEquals(updated_at, jsonPath.getString("data[" + dataIndex + "].updated_at"));

    }

    @And("The API user saves the response from the user api withdrawal details endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserApiWithdrawalDetailsEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .get(fullPath);

        response.prettyPrint();
    }
















    @And("The API user saves the response from the user profile endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserProfileEndpointWithValidAuthorizationInformation() {

        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .body(requestBody.toString())
                .patch(fullPath);

        response.prettyPrint();

    }

    @Then("The API user prepares a PATCH request containing the incomplete data to send to the user profile endpoint")
    public void theAPIUserPreparesAPATCHRequestContainingTheIncompleteDataToSendToTheUserProfileEndpoint() {

        requestBody = new JSONObject();
        requestBody.put("address", "New York");
        requestBody.put("state", "New York City");
        requestBody.put("zip", "125874");
        requestBody.put("city", "New York City");

    }

    @Then("The API user prepares a PATCH request containing the without data to send to the user profile endpoint")
    public void theAPIUserPreparesAPATCHRequestContainingTheWithoutDataToSendToTheUserProfileEndpoint() {
        requestBody = new JSONObject();

    }


    @Then("The API user saves the response from the user profile endpoint with invalid authorization information and verifies that the status code is '401' and the error message is Unauthorized")
    public void theAPIUserSavesTheResponseFromTheUserProfileEndpointWithInvalidAuthorizationInformationAndVerifiesThatTheStatusCodeIsAndTheErrorMessageIsUnauthorized() {
        try {
            response = given()
                    .spec(spec)
                    .contentType(ContentType.JSON)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .body(requestBody.toString())
                    .patch(fullPath);

            response.prettyPrint();
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));


    }

    @And("The API user saves the response from the api loanplans delete endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheApiLoanplansDeleteEndpointWithValidAuthorizationInformation() {

        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .delete(fullPath);

        response.prettyPrint();
    }

    @Then("The API user saves the response from the api loanplans delete endpoint with invalid authorization information and confirms that the status code is '401' and the error message is Unauthorized")
    public void theAPIUserSavesTheResponseFromTheApiLoanplansDeleteEndpointWithInvalidAuthorizationInformationAndConfirmsThatTheStatusCodeIsAndTheErrorMessageIsUnauthorized() {

        try {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .delete(fullPath);

            response.prettyPrint();
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));

    }

    @And("The API user prepares a POST request containing the correct data to send to the api withdrawal reject endpoint")
    public void theAPIUserPreparesAPOSTRequestContainingTheCorrectDataToSendToTheApiWithdrawalRejectEndpoint() {
        /*
        {
    "details":"Something went wrong."
}
         */
        requestBody = new JSONObject();
        requestBody.put("details", "Something went wrong.");


    }

    @When("The API user sends a POST request and saves the response from the api withdrawal reject endpoint with valid authorization information")
    public void theAPIUserSendsAPOSTRequestAndSavesTheResponseFromTheApiWithdrawalRejectEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .body(requestBody.toString())
                .post(fullPath);

        response.prettyPrint();

    }







































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































    @And("The API user saves the response from the user plan endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserPlanEndpointWithValidAuthorizationInformation() {

        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("user"))
                .when()
                .get(fullPath);

        response.prettyPrint();
    }

    @And("The API user verifies that the remark attribute in the response body is success")
    public void theAPIUserVerifiesThatTheRemarkAttributeInTheResponseBodyIsSuccess() {
        response.then()
                .assertThat()
                .body("remark", Matchers.equalTo("success"));
    }

    @And("The API user saves the response from the api loaans delete endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheApiLoaansDeleteEndpointWithValidAuthorizationInformation() {

        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .delete(fullPath);

        response.prettyPrint();
    }





   /* @Then("The API user verifies that the content of the data field in the response body includes {int}, {string}, {string}, {string}, {int}, {string}, {string}")
    public void the_apı_user_verifies_that_the_content_of_the_data_field_in_the_response_body_includes(Integer id, String name, String image, String description, Integer status, String created_at, String updated_at) {


        jsonPath = response.jsonPath();

        Assert.assertEquals(id, jsonPath.getString("data.id"));
        Assert.assertEquals(name, jsonPath.getString("data.name"));
        Assert.assertEquals(image, jsonPath.getString("data.image"));
        Assert.assertEquals(description, jsonPath.getString("data.description"));
        //Assert.assertEquals(status, jsonPath.getInt("data.status"));
        Assert.assertEquals(created_at, jsonPath.getString("data.created_at"));
        Assert.assertEquals(updated_at, jsonPath.getString("data.updated_at"));

    }*/

    @And("The API user prepares a POST request containing the correct data to send to the loanplans add endpoint")
    public void theAPIUserPreparesAPOSTRequestContainingTheCorrectDataToSendToTheLoanplansAddEndpoint() {

        requestBody = new JSONObject();

        requestBody.put("category_id", 1);
        requestBody.put("name", "Car Loan 9");
        requestBody.put("title", "Car Loan 9");
        requestBody.put("total_installment", 20);
        requestBody.put("installment_interval", 20);
        requestBody.put("per_installment", "4.00");
        requestBody.put("minimum_amount", "2000.00000000");
        requestBody.put("maximum_amount", "5000.00000000");
        requestBody.put("delay_value", 25);
        requestBody.put("fixed_charge", "100.00000000");
        requestBody.put("percent_charge", "1.00000000");
        requestBody.put("is_featured", 0);
        requestBody.put("application_fixed_charge", "20.00000000");
        requestBody.put("application_percent_charge", "3.00000000");
        requestBody.put("instruction", "Car Loan Plan 9");





    }

    @When("The API user sends a POST request and saves the response from the loanplans add endpoint with valid authorization information")
    public void theAPIUserSendsAPOSTRequestAndSavesTheResponseFromTheLoanplansAddEndpointWithValidAuthorizationInformation() {

        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .body(requestBody.toString())
                .post(fullPath);

        response.prettyPrint();
    }

    @And("The API user prepares a POST request containing the incomplete data to send to the loanplans add endpoint")
    public void theAPIUserPreparesAPOSTRequestContainingTheIncompleteDataToSendToTheLoanplansAddEndpoint() {

        requestBody = new JSONObject();

        requestBody.put("per_installment", "4.00");
        requestBody.put("minimum_amount", "2000.00000000");
        requestBody.put("maximum_amount", "5000.00000000");
        requestBody.put("delay_value", 25);
        requestBody.put("fixed_charge", "100.00000000");
        requestBody.put("percent_charge", "1.00000000");
        requestBody.put("is_featured", 0);
        requestBody.put("application_fixed_charge", "20.00000000");
        requestBody.put("application_percent_charge", "3.00000000");
        requestBody.put("instruction", "Car Loan Plan 9");
    }

    @And("The API user prepares a POST request containing no data to send to the loanplans add endpoint")
    public void theAPIUserPreparesAPOSTRequestContainingNoDataToSendToTheLoanplansAddEndpoint() {
        requestBody = new JSONObject();


    }

    @When("The API user sends a POST request and saves the response from the loanplans add endpoint with invalid authorization information")
    public void theAPIUserSendsAPOSTRequestAndSavesTheResponseFromTheLoanplansAddEndpointWithInvalidAuthorizationInformation() {

        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                .when()
                .body(requestBody.toString())
                .post(fullPath);

        response.prettyPrint();


    }
    @And("The API user prepares a POST request containing without including data to send to the api withdrawal reject endpoint")
    public void theAPIUserPreparesAPOSTRequestContainingWithoutIncludingDataToSendToTheApiWithdrawalRejectEndpoint() {

        requestBody = new JSONObject();

    }


        @When("The API user sends a POST request and saves the response from the api withdrawal reject endpoint with invalid authorization information")
    public void theAPIUserSendsAPOSTRequestAndSavesTheResponseFromTheApiWithdrawalRejectEndpointWithInvalidAuthorizationInformation() {

        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                .when()
                .body(requestBody.toString())
                .post(fullPath);

        response.prettyPrint();
    }

    @And("The API user saves the response from the loanplans detail endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheLoanplansDetailEndpointWithValidAuthorizationInformation() {


    }































































































































































































































    //*******************AYSE***********************
    @Given("The API user saves the response from the categories details endpoint with valid authorization information")
    public void the_apı_user_saves_the_response_from_the_categories_details_endpoint_with_valid_authorization_information() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .get(fullPath);

        response.prettyPrint();
    }




    @Then("The API user verifies that the id informations in the response body is <form_id>")
    public void theAPIUserVerifiesThatTheIdInformationsInTheResponseBodyIsForm_id(int form_id) {


    }

    @Then("The API user verifies that the id informations in the response body is {int}")
    public void the_apı_user_verifies_that_the_id_informations_in_the_response_body_is(int form_id) {
        jsonPath = response.jsonPath();

        Assert.assertEquals(form_id, jsonPath.getInt("data.form_id"));

    }

    @Then("The API user verifies that the content of the data field in the response body includes {int}, {string}, {string}, {string}, {int}, {string}, {string}")
    public void the_apı_user_verifies_that_the_content_of_the_data_field_in_the_response_body_includes(int id, String name, String image, String description, int status, String created_at, String updated_at) {
        jsonPath = response.jsonPath();

        Assert.assertEquals(id, jsonPath.getInt("data.id"));
        Assert.assertEquals(name, jsonPath.getString("data.name"));
        Assert.assertEquals(description, jsonPath.getString("data.description"));
        Assert.assertEquals(image, jsonPath.getString("data.image"));
        Assert.assertEquals(status, jsonPath.getInt("data.status"));
        Assert.assertEquals(created_at, jsonPath.getString("data.created_at"));
        Assert.assertEquals(updated_at, jsonPath.getString("data.updated_at"));
    }

    @Given("The API user saves the response from the loanplans status endpoint with valid authorization information")
    public void the_apı_user_saves_the_response_from_the_loanplans_status_endpoint_with_valid_authorization_information() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .patch(fullPath);

        response.prettyPrint();
    }

    @And("The API user verifies that the message information in the loanplans response body is {string}")
    public void theAPIUserVerifiesThatTheMessageInformationInTheLoanplansResponseBodyIs(String dataMessage) {
        response.then()
                .assertThat()
                .body("data.message",Matchers.equalTo(dataMessage));
    }

    @And("The API user saves the get response from the loanplans status endpoint with valid authorization information")
    public void theAPIUserSavesTheGetResponseFromTheLoanplansStatusEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .get(fullPath);

        response.prettyPrint();
    }

    @And("The API user Verifies that the new status information in the response body is {int}")
    public void theAPIUserVerifiesThatTheNewStatusInformationInTheResponseBodyIsNewStatus(int newStatus) {

        jsonPath = response.jsonPath();
        Assert.assertEquals(newStatus,jsonPath.getInt("data.new status"));
    }

    @And("The API user prepares a POST request containing the correct data to send to the subscriber update endpoint")
    public void theAPIUserPreparesAPOSTRequestContainingTheCorrectDataToSendToTheSubscriberUpdateEndpoint() {
        //{"email":"ayilmaz@gmail.com"}
        requestBody=new JSONObject();
        requestBody.put("email","ateloglu@gmail.com");

    }

    @When("The API user sends a POST request and saves the response from the subscriber update endpoint with valid authorization information")
    public void theAPIUserSendsAPOSTRequestAndSavesTheResponseFromTheSubscriberUpdateEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .body(requestBody.toString())
                .post(fullPath);

        response.prettyPrint();
    }

    @And("The API user verifies that the message information in the subscriber response body is {string}")
    public void theAPIUserVerifiesThatTheMessageInformationInTheSubscriberResponseBodyIs(String message) {
        response.then()
                .assertThat()
                .body("data.message",Matchers.equalTo(message));
    }

    @And("The API user prepares a POST request without data to send to the subscriber update endpoint")
    public void theAPIUserPreparesAPOSTRequestWithoutDataToSendToTheSubscriberUpdateEndpoint() {
        requestBody=new JSONObject();
    }

    @And("The API user prepares a POST request incorrect data to send to the subscriber update endpoint")
    public void theAPIUserPreparesAPOSTRequestIncorrectDataToSendToTheSubscriberUpdateEndpoint() {
        requestBody=new JSONObject();
        requestBody.put("mail","ateloglu@gmail.com");

    }

    @And("The API user verifies that the remark information in the subscriber response body is {string}")
    public void theAPIUserVerifiesThatTheRemarkInformationInTheSubscriberResponseBodyIs(String remark) {
        response.then()
                .assertThat()
                .body("remark", Matchers.equalTo(remark));
    }



    @When("The API user sends a POST request and saves the response from the subscriber update endpoint with invalid authorization information")
    public void theAPIUserSendsAPOSTRequestAndSavesTheResponseFromTheSubscriberUpdateEndpointWithInvalidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                .when()
                .body(requestBody.toString())
                .post(fullPath);

        response.prettyPrint();
    }

    @And("The API user saves the response from the subscriber update endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromTheSubscriberUpdateEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .get(fullPath);

        response.prettyPrint();
    }






















































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































    //    Bülent 3000 - 3400
    @Then("Verify the information of the one with the id {int} in the API user response body: {int}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void verify_the_information_of_the_one_with_the_id_in_the_api_user_response_body(int dataIndex, int user_id, String amount, String charge, String post_balance, String trx_type, String trx, String details, String remark, String created_at, String updated_at) {
        //  (user_id, amount, charge, post_balance, trx_type, trx, details, remark, created_at, updated_at)
        jsonPath = response.jsonPath();

        Assert.assertEquals(user_id, jsonPath.getInt("data[" + dataIndex + "].user_id"));
        Assert.assertEquals(amount, jsonPath.getString("data[" + dataIndex + "].amount"));
        Assert.assertEquals(charge, jsonPath.getString("data[" + dataIndex + "].charge"));
        Assert.assertEquals(post_balance, jsonPath.getString("data[" + dataIndex + "].post_balance"));
        Assert.assertEquals(trx_type, jsonPath.getString("data[" + dataIndex + "].trx_type"));
        Assert.assertEquals(trx, jsonPath.getString("data[" + dataIndex + "].trx"));
        Assert.assertEquals(details, jsonPath.getString("data[" + dataIndex + "].details"));
        Assert.assertEquals(remark, jsonPath.getString("data[" + dataIndex + "].remark"));
        Assert.assertEquals(created_at, jsonPath.getString("data[" + dataIndex + "].created_at"));
        Assert.assertEquals(updated_at, jsonPath.getString("data[" + dataIndex + "].updated_at"));


    }
    @And("The API admin user verifies that the remark information in the response body is {string}")
    public void theAPIAdminUserVerifiesThatTheRemarkInformationInTheResponseBodyIs(String remark) {
        response.then()
                .assertThat()
                .body("remark", Matchers.equalTo(remark));
    }

//    @And("The API user verifies that the remark information in the response body is {string}")
//    public void theAPIUserVerifiesThatTheRemarkInformationInTheResponseBodyIs(String remark) {
//        response.then()
//                .assertThat()
//                .body("remark", Matchers.equalTo(remark));
//    }
    @Then("The API user records the response with invalid authorization information, verifies that the status code is '401' and confirms that the error information is Unauthorized Message")
    public void theAPIUserRecordsTheResponseWithInvalidAuthorizationInformationVerifiesThatTheStatusCodeIsAndConfirmsThatTheErrorInformationIsUnauthorizedMessage() {

        try {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()

                    .delete(fullPath);

            response.prettyPrint();
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));
    }

    @And("The API user saves the response from get the api subscriber delete endpoint with valid authorization information")
    public void theAPIUserSavesTheResponseFromGetTheApiSubscriberDeleteEndpointWithValidAuthorizationInformation() {
        try {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()

                    .get(fullPath);

        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        response.prettyPrint();
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));



    }

//    @Then("Verify the information of the one with the id {int} in the API user response body: {int}, {string}, {string}, {string}, {string}, {int}, {int}, {string}, {string}, {string}")
//    public void verify_the_information_of_the_one_with_the_id_in_the_apı_user_response_body(int dataIndex, int user_id, String name, String email, String ticket, String subject, int status, int priority, String last_reply, String created_at, String updated_at) {
//        jsonPath = response.jsonPath();
//
//        Assert.assertEquals(user_id, jsonPath.getInt("data[" + dataIndex + "].user_id"));
//        Assert.assertEquals(name, jsonPath.getString("data[" + dataIndex + "].name"));
//        Assert.assertEquals(email, jsonPath.getString("data[" + dataIndex + "].email"));
//        Assert.assertEquals(ticket, jsonPath.getString("data[" + dataIndex + "].ticket"));
//        Assert.assertEquals(subject, jsonPath.getString("data[" + dataIndex + "].subject"));
//        Assert.assertEquals(status, jsonPath.getInt("data[" + dataIndex + "].status"));
//        Assert.assertEquals(priority, jsonPath.getInt("data[" + dataIndex + "].priority"));
//        Assert.assertEquals(last_reply, jsonPath.getString("data[" + dataIndex + "].last_reply"));
//        Assert.assertEquals(created_at, jsonPath.getString("data[" + dataIndex + "].created_at"));
//        Assert.assertEquals(updated_at, jsonPath.getString("data[" + dataIndex + "].updated_at"));
//
//    }

//    @Then("Verify the information of the one with the id {int} in the API user response body: {int}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
//    public void verify_the_information_of_the_one_with_the_id_in_the_api_user_response_body(int dataIndex, int user_id, String amount, String charge, String post_balance, String trx_type, String trx, String details, String remark, String created_at, String updated_at) {
//        //  (user_id, amount, charge, post_balance, trx_type, trx, details, remark, created_at, updated_at)
//        jsonPath = response.jsonPath();
//
//        Assert.assertEquals(user_id, jsonPath.getInt("data[" + dataIndex + "].user_id"));
//        Assert.assertEquals(amount, jsonPath.getString("data[" + dataIndex + "].amount"));
//        Assert.assertEquals(charge, jsonPath.getString("data[" + dataIndex + "].charge"));
//        Assert.assertEquals(post_balance, jsonPath.getString("data[" + dataIndex + "].post_balance"));
//        Assert.assertEquals(trx_type, jsonPath.getString("data[" + dataIndex + "].trx_type"));
//        Assert.assertEquals(trx, jsonPath.getString("data[" + dataIndex + "].trx"));
//        Assert.assertEquals(details, jsonPath.getString("data[" + dataIndex + "].details"));
//        Assert.assertEquals(remark, jsonPath.getString("data[" + dataIndex + "].remark"));
//        Assert.assertEquals(created_at, jsonPath.getString("data[" + dataIndex + "].created_at"));
//        Assert.assertEquals(updated_at, jsonPath.getString("data[" + dataIndex + "].updated_at"));
//    }
    @And("The API admin user saves the response from the user ticket detail endpoint with valid authorization information")
    public void theAPIAdminUserSavesTheResponseFromTheUserTicketDetailEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .get(fullPath);

        response.prettyPrint();
    }
    @And("The API admin user saves the response from the user loanplan detail endpoint with valid authorization information")
    public void theAPIAdminUserSavesTheResponseFromTheUserLoanplanDetailEndpointWithValidAuthorizationInformation() {


        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .get(fullPath);

        response.prettyPrint();



    }




    @And("The API user verifies that the message information in data the response body is {string}")
    public void theAPIUserVerifiesThatTheMessageInformationInDataTheResponseBodyIs(String message) {
        response.then()
                .assertThat()
                .body("remark",Matchers.equalTo("success"));

    }




    @Then("The API user saves the response from the api withdrawal approve endpoint with invalid authorization information and verifies that the status code is '401' and the error message is Unauthorized")
    public void theAPIUserSavesTheResponseFromTheApiWithdrawalApproveEndpointWithInvalidAuthorizationInformationAndVerifiesThatTheStatusCodeIsStringAndTheErrorMessageIsUnauthorized() {

//    @And("The API user verifies that the success attribute in the response body is true")
//    public void theAPIUserVerifiesThatTheSuccessAttributeInTheResponseBodyIsTrue() {
//        response.then()
//                .assertThat()
//                .body("success", Matchers.equalTo(true));
//    }

//    @And("The API admin User verifies that the message information in the response body is {string}")
//    public void theAPIUserVerifiesThatTheMessageInformationInTheResponseBodyIs(String message) {
//        response.then()
//                .assertThat()
//                .body("data.message",Matchers.equalTo(message));
//    }

//    @Then("The API user verifies that the content of the data field in the response body includes {int}, {int}, {string}, {string}, {string}, {string}, {int}, {int}, {string}, {string}, {string}")
//    public void the_apı_user_verifies_that_the_content_of_the_data_field_in_the_response_body_includes(int id, int user_id, String name, String email, String ticket, String subject, int status, int priority, String last_reply, String created_at, String updated_at) {
//        jsonPath = response.jsonPath();
//
//        Assert.assertEquals(id, jsonPath.getInt("data.id"));
//        Assert.assertEquals(user_id, jsonPath.getInt("data.user_id"));
//        Assert.assertEquals(name, jsonPath.getString("data.name"));
//        Assert.assertEquals(email, jsonPath.getString("data.email"));
//        Assert.assertEquals(ticket, jsonPath.getString("data.ticket"));
//        Assert.assertEquals(subject, jsonPath.getString("data.subject"));
//        Assert.assertEquals(status, jsonPath.getInt("data.status"));
//        Assert.assertEquals(priority, jsonPath.getInt("data.priority"));
//        Assert.assertEquals(last_reply, jsonPath.getString("data.last_reply"));
//        Assert.assertEquals(created_at, jsonPath.getString("data.created_at"));
//        Assert.assertEquals(updated_at, jsonPath.getString("data.updated_at"));
 }

    @And("The API admin user prepares a POST request containing the correct data to send to the user ticket add endpoint")
    public void theAPIadminUserPreparesAPOSTRequestContainingTheCorrectDataToSendToTheUserTicketAddEndpoint() {

        requestBody=new JSONObject();
        requestBody.put("category_id", 111);
        requestBody.put("name", "Updated Araba Loan Plan 9876");
        requestBody.put("title", "Updated Araba Loan Plan 9876");

    }

//    @When("The API user sends a POST request and saves the response from the user ticket add endpoint with valid authorization information")
//    public void theAPIUserSendsAPOSTRequestAndSavesTheResponseFromTheUserTicketAddEndpointWithValidAuthorizationInformation() {
//        response = given()
//                .spec(spec)
//                .contentType(ContentType.JSON)
//                .header("Accept", "application/json")
//                .headers("Authorization", "Bearer " + Authentication.generateToken("user"))
//                .when()
//                .body(requestBody.toString())
//                .post(fullPath);
//
//        response.prettyPrint();
//    }
    @When("The API admin user sends a POST request and saves the response from the user ticket add endpoint with valid authorization information")
    public void theAPIadminUserSendsAPOSTRequestAndSavesTheResponseFromTheUserTicketAddEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .body(requestBody.toString())
                .post(fullPath);

        response.prettyPrint();
    }
// düzelt 2
//    @Then("The API admin user verifies that the message information in the response body is {string}")
//    public void the_apı_admin_user_verifies_that_the_message_information_in_the_response_body_is(String message) {
//        response.then()
//                   .assertThat()
//                       .body("data.message",Matchers.equalTo(message));
//    }


    @And("The API admin user prepares a POST request without data to send to the user ticket add endpoint")
    public void theAPIAdminUserPreparesAPOSTRequestWithoutDataToSendToTheUserTicketAddEndpoint() {
        requestBody=new JSONObject();
    }

//    @And("The API user prepares a POST request with missing data to send to the user ticket add endpoint.")
//    public void theAPIUserPreparesAPOSTRequestWithMissingDataToSendToTheUserTicketAddEndpoint() {
//        requestBody=new JSONObject();
//        requestBody.put("subject","Test Ticket");
//    }

    @When("The API admin user sends a POST request and saves the response from the user ticket add endpoint with invalid authorization information")
    public void theAPIAdminUserSendsAPOSTRequestAndSavesTheResponseFromTheUserTicketAddEndpointWithInvalidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                .when()
                .body(requestBody.toString())
                .post(fullPath);

        response.prettyPrint();
    }

    @And("The API admin user verifies that the error information in the response body is {string}")
    public void theAPIadminUserVerifiesThatTheErrorInformationInTheResponseBodyIs(String error) {
        response.then()
                .assertThat()
                .body("message.error[0]",Matchers.equalTo(error));
    }


    @Then("The API admin user verifies that the id information in the response body is {int}")
    public void the_apı_admin_user_verifies_that_the_id_information_in_the_response_body_is(int id) {
        jsonPath = response.jsonPath();

        Assert.assertEquals(id,jsonPath.getInt("data[0].id"));


    }

//    @And("The API user saves the response from the user ticket close endpoint with valid authorization information")
//    public void theAPIUserSavesTheResponseFromTheUserTicketCloseEndpointWithValidAuthorizationInformation() {
//        response = given()
//                .spec(spec)
//                .header("Accept", "application/json")
//                .headers("Authorization", "Bearer " + Authentication.generateToken("user"))
//                .when()
//                .patch(fullPath);
//
//        response.prettyPrint();
//    }

    @And("The API admin user saves the response from the user ticket delete endpoint with valid authorization information")
    public void theAPIAdminUserSavesTheResponseFromTheUserTicketDeleteEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .delete(fullPath);

        response.prettyPrint();
    }



    @Then("The API admin user saves the response from the user ticket delete endpoint with invalid authorization information and confirms that the status code is {int} and the error message is Unauthorized")
    public void theAPIAdminUserSavesTheResponseFromTheUserTicketDeleteEndpointWithInvalidAuthorizationInformationAndConfirmsThatTheStatusCodeIsAndTheErrorMessageIsUnauthorized(int arg0) {

        try {
            response = given()
                    .spec(spec)
                    .header("Accept", "application/json")

                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                    .when()
                    .patch(fullPath);
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        System.out.println("mesaj: " + mesaj);

        Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));
        }


        @Then("Verify the information of the one with the {int} {int} in the API user response body {string}, {int}, {int}, {string}, {string}, {int}, {int}, {string}, {string}, {int}, {int}, {int}")
        public void verify_the_information_of_the_one_with_the_in_the_apı_user_response_body(int id, int dataIndex, String loanNumber, int userId, int planId, String amount, String perInstallment, int installmentInterval, int delayValue, String chargePerInstallment, String delayCharge, int givenInstallment, int totalInstallment, int status) {

        jsonPath = response.jsonPath();

        Assert.assertEquals(loanNumber, jsonPath.getString("data[" + dataIndex + "].loan_number"));
        Assert.assertEquals(userId, jsonPath.getInt("data[" + dataIndex + "].user_id"));
        Assert.assertEquals(planId, jsonPath.getInt("data[" + dataIndex + "].plan_id"));
        Assert.assertEquals(amount, jsonPath.getString("data[" + dataIndex + "].amount"));
        Assert.assertEquals(perInstallment, jsonPath.getString("data[" + dataIndex + "].per_installment"));
        Assert.assertEquals(installmentInterval, jsonPath.getInt("data[" + dataIndex + "].installment_interval"));
        Assert.assertEquals(delayValue, jsonPath.getInt("data[" + dataIndex + "].delay_value"));
        Assert.assertEquals(chargePerInstallment, jsonPath.getString("data[" + dataIndex + "].charge_per_installment"));
        Assert.assertEquals(delayCharge, jsonPath.getString("data[" + dataIndex + "].delay_charge"));
        Assert.assertEquals(givenInstallment, jsonPath.getInt("data[" + dataIndex + "].given_installment"));
        Assert.assertEquals(totalInstallment, jsonPath.getInt("data[" + dataIndex + "].total_installment"));
        Assert.assertEquals(status, jsonPath.getInt("data[" + dataIndex + "].status"));

    }
/*


                    .headers("Authorization", "Bearer " +  ConfigReader.getProperty("invalidToken"))
                    .when()
                    .delete(fullPath);
        } catch (Exception e) {
            mesaj = e.getMessage();
        }
        response.prettyPrint();
        response.then()
                .assertThat()
                .body("message.error[0]",Matchers.equalTo("Unauthorized request"));
        // System.out.println("mesaj = " + mesaj);
        // Assert.assertTrue(mesaj.contains("status code: 401, reason phrase: Unauthorized"));

    }*/


    @And("The API admin user prepares a PATCH request containing the correct data to send to the user ticket add endpoint")
    public void theAPIAdminUserPreparesAPATCHRequestContainingTheCorrectDataToSendToTheUserTicketAddEndpoint() {
        requestBody=new JSONObject();
        requestBody.put("title", ">>> Bulent Title Added <<<");
        requestBody.put("description", ">>> Bulent Title Descriptions <<<");

    }

    @When("The API admin user sends a PATCH request and saves the response from the user ticket add endpoint with valid authorization information")
    public void theAPIAdminUserSendsAPATCHRequestAndSavesTheResponseFromTheUserTicketAddEndpointWithValidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + Authentication.generateToken("admin"))
                .when()
                .body(requestBody.toString())
                .patch(fullPath);

        response.prettyPrint();
    }

    @And("The API admin user verifies that the message information in the response body is {string}")
    public void theAPIAdminUserVerifiesThatTheMessageInformationInTheResponseBodyIs(String message) {
        response.then()
                .assertThat()
                .body("data.message",Matchers.equalTo(message));
    }

    @And("The API admin user prepares a PATCH request containing the lacking data to send to the user ticket add endpoint")
    public void theAPIAdminUserPreparesAPATCHRequestContainingTheLackingDataToSendToTheUserTicketAddEndpoint() {
        requestBody=new JSONObject();
//        requestBody.put("title", ">>> Bulent Title Added <<<");
//        requestBody.put("description", ">>> Bulent Title Descriptions <<<");


    }

    @And("The API admin user prepares a PATCH request containing the incomplete data to send to the user ticket add endpoint")
    public void theAPIAdminUserPreparesAPATCHRequestContainingTheIncompleteDataToSendToTheUserTicketAddEndpoint() {
        requestBody=new JSONObject();
        requestBody.put("title", "!!! Bulent Title Added !!!");
        // requestBody.put("description", ">>> Bulent Title Descriptions <<<");

    }

    @When("The API admin user sends a PATCH request and saves the response from the user ticket add endpoint with invalid authorization information")
    public void theAPIAdminUserSendsAPATCHRequestAndSavesTheResponseFromTheUserTicketAddEndpointWithInvalidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                .when()
                .body(requestBody.toString())
                .patch(fullPath);

        response.prettyPrint();
    }

    @And("The API user saves the response from the user ticket list endpoint with invalid authorization information")
    public void theAPIUserSavesTheResponseFromTheUserTicketListEndpointWithInvalidAuthorizationInformation() {
        response = given()
                .spec(spec)
                .header("Accept", "application/json")
                .headers("Authorization", "Bearer " + ConfigReader.getProperty("invalidToken"))
                .when()
                .get(fullPath);
    }

    @And("The API admin User verifies that the information in the response body message is {string}")
    public void theAPIAdminUserVerifiesThatTheInformationInTheResponseBodyMessageIs(String message) {
        response.then()
                .assertThat()
                .body("data.message",Matchers.equalTo(message));
    }




    // Bülent 3400 . satir son








}










