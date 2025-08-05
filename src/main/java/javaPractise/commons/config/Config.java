
package javaPractise.commons.config;


import javaPractise.commons.annotation.DataFile;
import lombok.Data;

import java.util.Map;

@Data
@DataFile(fileName = "selcukes.yaml", streamLoader = true)
public class Config {
    private String projectName;
    private String env;
    private String baseUrl;
    private Map<String, String> excel;
    private Map<String, String> cucumber;
    private WebConfig web;
    private WindowsConfig windows;
    private MobileConfig mobile;
    private Map<String, String> reports;
    private VideoConfig video;
    private NotifierConfig notifier;

    @Data
    public static class MobileConfig {
        boolean remote;
        String cloud;
        String platform;
        String browser;
        boolean headLess;
        String serviceUrl;
        String app;
    }

    @Data
    public static class WebConfig {
        boolean remote;
        String cloud;
        String browser;
        boolean headLess;
        String serviceUrl;
        String serverJar;
        String app;
    }

    @Data
    public static class WindowsConfig {
        boolean remote;
        String serviceUrl;
        String app;
    }

    @Data
    public static class NotifierConfig {
        boolean notification;
        String type;
        String webhookToken;
        String apiToken;
        String channel;
        String authorIcon;
        MailConfig mail;
    }

    @Data
    public static class MailConfig {
        String host;
        int port;
        String username;
        String password;
        String from;
        String to;
        String cc;
        String bcc;
    }

    @Data
    public static class VideoConfig {
        boolean recording;
        String type;
        String ffmpegPath;
        boolean watermark;
    }
}
