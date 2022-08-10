package testCases.gui.web.video;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.testng.VideoListener;

import org.testng.annotations.Test;

import static org.junit.Assert.assertTrue;


public class recordVideo {

    @Test
    @Video
    public void shouldFailAndCreateRecordWithTestName() throws InterruptedException {
        Thread.sleep(1000);
        assert false;
    }

    @Test
    @Video(name = "second_test")
    public void videoShouldHaveNameSecondTest() throws InterruptedException {
        Thread.sleep(1000);
        assertTrue(false);
    }
}
