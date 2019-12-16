package com.yizutiyu.brightbeacon.eventbus;

import java.io.ByteArrayOutputStream;

/**
 * 检测人脸的数据信息
 */
public class FileEventBus {
    /**
     * ByteArrayOutputStream
     */
    private ByteArrayOutputStream file;

    public FileEventBus(ByteArrayOutputStream file) {
        this.file = file;
    }

    public ByteArrayOutputStream getFile() {
        return file;
    }

    public void setFile(ByteArrayOutputStream file) {
        this.file = file;
    }
}
