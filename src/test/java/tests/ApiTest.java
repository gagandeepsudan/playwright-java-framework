package tests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ApiTest {
    Playwright playwright;
    APIRequestContext request;
    @BeforeClass
    public void setup(){
        playwright = Playwright.create();
        request= playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL("https://reqres.in")
                        .setExtraHTTPHeaders(java.util.Map
                                .of("x-api-key", "pub_4aa8d33fd184555f3742c028a8513e2340e6319bf0af81f309606cff89ced2f3"))
        );
    }
    @Test
    public void getUsersReturns200(){
        APIResponse response= request.get("/api/users?page=2");

        Assert.assertEquals(response.status(), 200, "Expected 200 OK");

        String body = response.text();
        Assert.assertTrue(body.contains("\"page\":2"), "Body should contain page number");
        System.out.println("Response body: " + body);
    }
    @Test
    public void getSingleUserReturns200(){
        APIResponse response = request.get("/api/users/2");
        Assert.assertEquals(response.status(), 200);
        //Parse the body as JSON
        JSONObject body = new JSONObject(response.text());
        JSONObject data = body.getJSONObject("data");
        //Schema validation-field existence and types
        Assert.assertTrue(data.has("id") ,"id field must exist ");
        Assert.assertTrue(data.has("email") ,"email field must exist");
        Assert.assertTrue(data.has("first_name"),"first_name field must exist");
        Assert.assertTrue(data.has("last_name"),"last_name field must exist");

        //Type checks
        Assert.assertTrue(data.get("id") instanceof Integer, "id must be an integer");
        Assert.assertTrue(data.get("email") instanceof String, "email must be a string");

        //No Nulls on required fields
        Assert.assertFalse(data.isNull("email"),"email field must not be null");
        Assert.assertFalse(data.isNull("first_name"),"first_name field must not be null");
        }
    @Test
    public void getMissingUsersReturns404(){
        APIResponse response = request.get("/api/users/999");
        Assert.assertEquals(response.status(), 404, "Non-existent user should return 404");
    }
    @AfterClass
    public void teardown(){
        request.dispose();
        playwright.close();
    }
}
