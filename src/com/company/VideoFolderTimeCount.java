package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoFolderTimeCount {



    void deepLoop(int deep){
        for (int i = 0; i < deep; i++){
            System.out.print("          ");
        }
    }
    void deepLoopL(int deep){
        for (int i = 0; i < deep-1; i++){
            System.out.print("          ");
        }
        System.out.print("  |_______");
    }
    public int getVideoTime(String video_path, String ffmpeg_path,int thisDeep) {
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(ffmpeg_path);
        commands.add("-i");
        commands.add(video_path);
        int time = 0;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());

            if (m.find()) {
                time = getTimelen(m.group(1));
            } else {
                time = 0;

            }
            File tempFile = new File(video_path.trim());
            String fileName = tempFile.getName();
            System.out.printf("%-12s","时长：" + time/3600 + ":" + time%3600/60 + ":" + time%3600%60);
            System.out.println("  文件名："+ fileName );

        } catch (Exception e) {
        }
        return time;
    }

    //格式:"00:00:10.68"
    private static int getTimelen(String timelen) {
        int min = 0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min += Integer.valueOf(strs[0]) * 60 * 60;//秒
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }


    //int allTime = 0;
    public int getVideoDuration(String path,int deep) {
        // get all files in specified "path"
        File[] files = new File(path).listFiles();
        int thisDeep = deep;

        deepLoopL(thisDeep);
        System.out.println(" 文件和文件夹数量：" + files.length);

        int totalTime = 0;
        int folderTime = 0;
        int duration ;
        int count = 0;

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory() ) {
                deepLoop(thisDeep);
                System.out.println("【"+(i+1)+"】文件夹：" +files[i].toString());
                int harddeep = thisDeep +1;
                totalTime += getVideoDuration(files[i].toString(),harddeep);

            }else {
                deepLoop(thisDeep);
                System.out.printf("%-5s","【" + (i+1) + "】");
                duration = getVideoTime(files[i].toString(),"res/mac/ffmpeg",thisDeep);
                if (duration > 0) {
                    count++;
                    totalTime += duration;
                    folderTime += duration;

                }
            }

        }

        deepLoop(thisDeep);
        System.out.println( " 本层数量：" + count);
        deepLoop(thisDeep);
        System.out.println(" 本层时长：" + folderTime/3600 + ":" + folderTime%3600/60 + ":" + folderTime%3600%60);
        deepLoop(thisDeep);
        System.out.println(" 全部时长：" + totalTime/3600 + ":" + totalTime%3600/60 + ":" + totalTime%3600%60);
        return totalTime;

    }


}