package web.practice.video;

import com.automation.remarks.video.annotations.Video;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;


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
