package com.vinhnq.beans;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;

public class FileCustom extends File {
    Long size = 0L;
    public FileCustom(String pathname) {
        super(pathname);
    }

    public FileCustom(String parent, String child) {
        super(parent, child);
    }

    public FileCustom(File parent, String child) {
        super(parent, child);
    }

    public FileCustom(URI uri) {
        super(uri);
    }

    public long getSize() {
        if(null == size || size == 0){
            try {
                long bytes = Files.size(super.toPath());
                size = bytes;
            } catch (Exception e) {
                e.printStackTrace();
                size = -1L;
            }
        }
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public File getFile() {
        return super.toPath().toFile();
    }
}
