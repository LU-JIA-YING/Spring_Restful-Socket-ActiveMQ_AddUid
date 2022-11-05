package com.example.Spring.controller.socket;

import com.example.Spring.config.SpringUtil;
import com.example.Spring.service.TransferService;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.System.in;
import static java.lang.System.out;

public class ClientHandler implements Runnable {

    private TransferService transferService = SpringUtil.getBean(TransferService.class);

    private final Socket clientSocket;

    // Constructor
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    //  https://www.dotblogs.com.tw/Leon-Yang/2021/01/06/155519
    //  File -> Project Structure -> Modules 加入log4j-1.2.15.jar
    private static final Logger logger = Logger.getLogger(ClientHandler.class);

    public void run() {

        try {
            //當Socket持續在連接時，就做下面的事
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            String requestStr = bufferedReader.readLine();
            out.println("requestStr: " + requestStr);
            bufferedWriter.write(transferService.getResponse(requestStr));
            out.println("================================================");

            bufferedWriter.newLine();
            bufferedWriter.flush();

            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();

            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();

            clientSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("Error Message: ", new SocketException("Socket is closed"));
            out.println("Socket啟動有問題 !");
            out.println("IOException :" + e.toString());
            e.printStackTrace();

        } catch (NullPointerException e) {
            try {
                OutputStream outputStream = clientSocket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));

                out.println(e);
                bw.write("輸入Json格式資料有誤" + e);
                bw.newLine();
                bw.flush();

                bw.close();
                outputStream.close();

                logger.error("輸入Json格式資料有誤" + e);

            } catch (IOException ex) {
                out.println("Socket啟動有問題 !");
                out.println("IOException :" + e.toString());
            }
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
//        finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }
}