package com.company;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.FFMPEGLocator;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoDuration {



    String videoTimeStr = "/Volumes/Learning Materials/2019【汤家凤面授】专项/考研数学疑难点【暑期系列】/暑期线代系列/01.暑期线代（一）矩阵秩性质证明.mp4";
    String ffmpegPath = "/Users/leiyang/Downloads/ffmpeg";
    //getVideoTime(videoTimeStr,ffmpegPath);

    public static int getVideoTime(String video_path, String ffmpeg_path) {
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
                System.out.println("视频路径：" +video_path);
                System.out.println("视频时长：" + time/3600 + ":" + time%3600/60 + ":" + time%3600%60 + ", 开始时间：" + m.group(2) + ",比特率：" + m.group(3) + "kb/s");
                return time;
            } else {
                time = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
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


    public void getVideoDuration(String path) {
        // get all files in specified "path"
        File[] files = new File(path).listFiles();
        System.out.println("文件数量：" + files.length);

        int totalTime = 0;
        int duration ;
        int count = 0;

        for (int i = 0; i < files.length; i++) {
            // here, the format of video can be changed, JAVE upports dozens of formats
            // && files[i].toString().endsWith(".mp4")
            if (!files[i].isDirectory() ) {
                duration = getVideoTime(files[i].toString(),"/Users/leiyang/Downloads/ffmpeg");
                if (duration > 0) {
                    count++;
                    totalTime += duration;
                }else{
                    System.out.println("读取的文件不是视频文件，或者文件损坏");
                }

            }
        }
        System.out.println("总计数量：" + count);
        System.out.println("总计时长：" + totalTime/3600 + ":" + totalTime%3600/60 + ":" + totalTime%3600%60);

    }


}