import org.junit.Test;
import play.Application;
import play.test.Helpers;
import play.test.TestBrowser;
import play.test.WithBrowser;

import static org.junit.Assert.*;
import static play.test.Helpers.*;


class IntegrationTest extends WithBrowser {
    protected Application provideApplication() {
        return fakeApplication(inMemoryDatabase());
    }

    protected TestBrowser provideBrowser(int port){
        return Helpers.testBrowser(port);
    }

    @Test
    public void TestIndex() {
        browser.goTo("http://localhost:" + play.api.test.Helpers.testServerPort());
        assertTrue(browser.pageSource().contains("Search Form"));
        assertTrue(browser.pageSource().contains("Name"));
        assertTrue(browser.pageSource().contains("Tweet"));
        assertTrue(browser.pageSource().contains("Link"));
    }

    @Test
    public void TestUserProfile() {
        //TODO
    }

    @Test
    public void TestError() {
        //TODO
    }
}