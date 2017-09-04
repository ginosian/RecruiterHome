package com.recruiting.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Martha on 5/20/2017.
 */
public class FileModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private byte[] fileContent;
    private transient MultipartFile multipartFileUpload;

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFile(MultipartFile file) {
        try {
            this.fileContent = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MultipartFile getMultipartFileUpload() {
        return multipartFileUpload;
    }

    public void setMultipartFileUpload(final MultipartFile multipartFileUpload) {
        this.multipartFileUpload = multipartFileUpload;
    }
}
