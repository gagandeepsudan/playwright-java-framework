package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;
    public LoginPage(Page page){
        super(page);
        this.usernameField= page.locator("#user-name");
        this.passwordField= page.locator("#password");
        this.loginButton= page.locator("#login-button");
    }
    public void navigate(){
        navigateTo("https://www.saucedemo.com/");
    }
//    public void enterUsername(String username){
//        page.getByPlaceholder("Username").fill(username);
//    }
//    public void enterPassword(String password){
//        page.getByPlaceholder("Password").fill(password);
//    }
//    public void clickLogin(){
//     page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
//             .setName("Login")).click();
//    }
    public void loginAs(String username, String password){
        usernameField.fill(username);
        passwordField.fill(password);
        loginButton.click();
    }


}
