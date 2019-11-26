package nia.chapter13.fileListener;


import nia.chapter13.LogEventBroadcaster;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ListenerTest {
    /***
     * 使用的是common-io包完成的
     * @param args
     */
    public static void main(String[] args) {

        String resourceLocation;
        URL location = LogEventBroadcaster.class
                .getProtectionDomain()
                .getCodeSource().getLocation();
        try {
            String resource=location.toURI().toString();
            resourceLocation = !resource.contains("file:") ? resource : resource.substring(5);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(
                    "Unable to locate resources", e);
        }

//        File file = new File(resourceLocation + "chapter13.log");

//        FileAlterationObserver observer = new FileAlterationObserver(resourceLocation,
//                FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
//                        FileFilterUtils.nameFileFilter(file.getName())));

        MyFileAlterationMonitor monitor = new MyFileAlterationMonitor(
                resourceLocation,
                ".log",
                new MyListenerAdaptor());
        monitor.start();
    }
}
