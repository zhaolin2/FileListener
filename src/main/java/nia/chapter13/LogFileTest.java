package nia.chapter13;

import io.netty.channel.Channel;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class LogFileTest {

    /**
     * @Classname LogFileTest
     * @Description //单机测试方法 默认会监视classes下的chapter13.log文件
     * @Other  ZL 2019/11/26
     **/
    public static void main(String[] args) throws IOException {
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

        File file = new File(resourceLocation + "chapter13.log");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        //构造一个BufferedReader类来读取文件
        String line;
        while ((line=br.readLine())!=null){
            System.out.println(line);
        }


        long pointer = 0;
//        for (;;) {
//            File file = new File(resourceLocation + "chapter13.log");
//            RandomAccessFile r = new RandomAccessFile(file, "r");
//            //当前文件指针所在位置
//            long len = file.length();
//            System.out.println(len);
//            if (len < pointer) {
//                // file was reset
//                pointer = len;
//            } else if (len > pointer) {
//                // Content was added
//                RandomAccessFile raf = new RandomAccessFile(file, "r");
//                raf.seek(pointer);
//                String line;
//                while ((line = raf.readLine()) != null) {
//                    System.out.println("新增加一行"+line);
//                }
//                pointer = raf.getFilePointer();
//                raf.close();
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                Thread.interrupted();
//                break;
//            }
//        }

    }
}
