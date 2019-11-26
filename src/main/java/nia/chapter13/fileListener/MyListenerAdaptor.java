package nia.chapter13.fileListener;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class MyListenerAdaptor extends FileAlterationListenerAdaptor {

    @Override
    public void onFileChange(File file) {
        System.out.println("change.............");
        super.onFileChange(file);
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
        System.out.println("start");
        super.onStart(observer);
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        System.out.println("stop");
        super.onStop(observer);
    }

    @Override
    public void onDirectoryChange(File directory) {
        System.out.println("dir change.........");
        super.onDirectoryChange(directory);
    }

    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println("dir create.........");
        super.onDirectoryCreate(directory);
    }

    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println("dir delete.........");
        super.onDirectoryCreate(directory);
    }

}
