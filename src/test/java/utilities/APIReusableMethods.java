package utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Arrays;

import static hooks.HooksAPI.spec;
import static io.restassured.RestAssured.given;
import static stepdefinitions.API_Stepdefinitions.fullPath;

public class APIReusableMethods {


    static Response response;
    public static String pathParameters(String rawPaths) {

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

        return fullPath;
    }
    public static Response getRequestUser(){

        response = given()
                .spec(spec)
                .headers("Authorization","Bearer " + Authentication.generateToken("user"))
                .when()
                .get(fullPath);

        return response;
    }

    public static Response postRequestUser(Object reqBody){

        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer " +Authentication.generateToken("user"))
                .when()
                .body(reqBody)
                .post(fullPath);

        return response;
    }

}
