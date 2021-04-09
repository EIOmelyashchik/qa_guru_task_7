package helpers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static helpers.AttachmentHelper.*;

public class TestResultHelper implements TestWatcher {
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        attachVideo();
        closeWebDriver();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        closeWebDriver();
    }
}
