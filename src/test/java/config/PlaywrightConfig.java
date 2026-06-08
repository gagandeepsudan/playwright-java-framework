package config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class PlaywrightConfig {
    public static Browser createBrowser(Playwright playwright){
        boolean isHeadless = Boolean.parseBoolean(
                System.getProperty("headless", "true")
        );
        int slowMo = Integer.parseInt(
                System.getProperty("slowMo", "0")
        );
        return playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(isHeadless)
                        .setSlowMo(slowMo)
        );
    }
    public static Browser.NewContextOptions contextOptions(){
        return new Browser.NewContextOptions()
                .setViewportSize(1280,720);
    }
}
