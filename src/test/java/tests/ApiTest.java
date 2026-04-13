package tests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
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
        String body = response.text();
        Assert.assertTrue(body.contains("\"id\":2"), "Body should contain user id 2");
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
