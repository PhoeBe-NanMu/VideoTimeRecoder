package com.company;


import java.io.File;

public class Main {



    public static void main(String[] args) {

        String ffmpegPath = "/Users/leiyang/Downloads/ffmpeg";

        String fileFolderPath = "/Volumes/Learning Materials/2019【朱伟】英语/基础班/05【朱伟】【新版恋练有词】";
        String fileFolderPath2 = "/Users/leiyang/Downloads/temp";


        VideoFolderTimeCount videoFolderTimeCount = new VideoFolderTimeCount();
        videoFolderTimeCount.getVideoDuration(fileFolderPath2,1);
    }
}



