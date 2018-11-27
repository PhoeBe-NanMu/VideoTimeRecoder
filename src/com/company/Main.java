package com.company;


public class Main {



    public static void main(String[] args) {


        String fileFolderPath = "/Volumes/Materials/百度网盘/计算机网络【完】";



        VideoFolderTimeCount videoFolderTimeCount = new VideoFolderTimeCount();
        videoFolderTimeCount.getVideoDuration(fileFolderPath,1);
    }
}



