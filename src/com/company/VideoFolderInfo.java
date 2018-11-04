package com.company;

public class VideoFolderInfo {

    public String folderName;
    public int fileNum;
    public int videoNum;
    public int deep;

    public String getFolderName() {
        return folderName;
    }

    public int getFileNum() {
        return fileNum;
    }

    public int getVideoNum() {
        return videoNum;
    }

    public int getDeep() {
        return deep;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }

    public void setVideoNum(int videoNum) {
        this.videoNum = videoNum;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }
}
