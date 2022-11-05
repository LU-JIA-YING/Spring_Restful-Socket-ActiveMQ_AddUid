package com.example.Spring.config;

import java.util.Scanner;

// 開ActiveMQ的執行檔
public class OpenExe extends Thread {

    @Override
    public void run() {

        Runtime rt = Runtime.getRuntime();
        try {
            //執行的檔案的位置
            rt.exec("C:\\Users\\joyce\\Desktop\\Data\\公司 學習資料\\培訓班最後一個月衝刺_Spring、socket、MQ\\培訓班參考文件_20220926\\sprint4\\Spring4 Project\\apache-activemq-5.17.2\\bin\\win64\\activemq.bat");
            System.out.println("Open MQ！");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String s = scanner.nextLine().toLowerCase();
                if (s.equals("close")) {
                    rt.exec("taskkill /f /im wrapper.exe");
                    System.exit(0);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("MQ close！");
            e.printStackTrace();
        }
    }
}

