package com.example.Spring.controller.socket;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

//  Socket連線Server-Client
//  => http://hsingjungchen.blogspot.com/2017/07/javaserver-clientsokcet.html
//  OR https://www.it145.com/9/72695.html
public class SocketClient {

    public static void sendRequest(String request) throws Exception {

        try {
            //  創建 Socket 類對象並初始化 Socket
            Socket socket = new Socket("localhost", 5000);
            System.out.println("已連線Server");


            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//利用sk來取得輸出串流
            System.out.println("------------------------------Client------------------------------");

            bw.write(request);
            bw.newLine();
            bw.flush(); //  立即傳送

            //  查詢結果印出
            BufferedReader response = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = "";
            System.out.println("查詢結果：");
            while ((str = response.readLine()) != null) {
                System.out.println(str);
            }
            System.out.println("==============================================");
            socket.close();
        } catch (SocketException e) {
            throw new SocketException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();    //  TODO Auto-generated catch block
        }
    }
}
