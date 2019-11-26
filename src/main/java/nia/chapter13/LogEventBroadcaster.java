package nia.chapter13;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertNull;

/**
 * Listing 13.3 LogEventBroadcaster
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class LogEventBroadcaster {

    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    static private final String resourceLocation;
    private final String  fileName;

    public LogEventBroadcaster(InetSocketAddress address, String fileName) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class)
                //设置广播模式
             .option(ChannelOption.SO_BROADCAST, true)
             .handler(new LogEventEncoder(address));
        this.fileName = fileName;
//        this.fileName = "/D:/chapter13.log";
    }

    public void run() throws Exception {
        Channel ch = bootstrap.bind(0).sync().channel();
        long pointer = 0;
        for (;;) {
            File file = new File(fileName);
            long len = file.length();
            if (len < pointer) {
                // file was reset
                pointer = len;
            } else if (len > pointer) {
                // Content was added
                RandomAccessFile raf = new RandomAccessFile(file, "r");
                raf.seek(pointer);
                String line;
                while ((line = raf.readLine()) != null) {
                    System.out.println(line);
                    ch.writeAndFlush(new LogEvent(null, -1,
                    file.getAbsolutePath(), line));
                }
                pointer = raf.getFilePointer();
                raf.close();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }

     static {
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
    }

    public static void main(String[] args) throws Exception {
//        if (args.length != 2) {
//            throw new IllegalArgumentException();
//        }
        int port;
        String fileName;

        try {
            port = Integer.parseInt(args[0]);
            fileName=args[1];
        }catch (Exception e){
            port=8080;
            fileName="chapter13.log";
        }
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(
                new InetSocketAddress("255.255.255.255",
                    port), resourceLocation+fileName);
//        File file = new File(resourceLocation + fileName);
        try {
            broadcaster.run();
        }
        finally {
            broadcaster.stop();
        }
    }
}
