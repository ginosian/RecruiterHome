package com.recruiting.domain;

import com.recruiting.utils.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Martha on 5/22/2017.
 */
@Entity
@Table(name = "file")
public class File extends AbstractEntity implements Serializable {

    // region Instance Fields
    @Lob
    @Column(name = "file")
    private byte[] file;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_content_type")
    private String fileContent;
    // endregion

    // region Constructors
    public File() {
    }

    public File(String ssn) {
        super(ssn);
    }

    // endregion
    public void correctStrings() {
        this.fileName = StringUtils.correctWhiteSpaces(fileName);
        this.fileContent = StringUtils.correctWhiteSpaces(fileContent);
    }
    // region Transient methods

    // endregion

    // region Getters and Setters

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    // endregion
}
