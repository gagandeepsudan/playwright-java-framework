package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    private final Page page;
    public LoginPage(Page page){
        this.page= page;
    }
    public void navigate(){
        page.navigate("https://www.saucedemo.com/");
    }
    public void enterUsername(String username){
        page.getByPlaceholder("Username").fill(username);
    }
    public void enterPassword(String password){
        page.getByPlaceholder("Password").fill(password);
    }
    public void clickLogin(){
     page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
             .setName("Login")).click();
    }
    public void loginAs(String username, String password){
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

}
