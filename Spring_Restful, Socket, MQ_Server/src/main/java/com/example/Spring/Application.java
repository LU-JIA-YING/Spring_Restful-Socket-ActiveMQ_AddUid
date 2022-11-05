package com.example.Spring;

import com.example.Spring.config.OpenExe;
import com.example.Spring.controller.socket.ClientHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//  Multithreaded Servers in Java
//  https://www.geeksforgeeks.org/multithreaded-servers-in-java/
//  原MultiThreadServer.class
@EnableJpaAuditing    //  相應的字段上添加對應的時間註解 @LastModifiedDate 和 @CreatedDate
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class Application {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(Application.class, args);

        // 開ActiveMQ的執行檔
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new OpenExe());

        //  建立ServerSocket物件，並設定埠號5000
        ServerSocket server = new ServerSocket(5000);
        try {

            System.out.println("等待client");
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {

                //  收到Client請求 如果Socket匹配 兩Socket可互相通信
                Socket client = server.accept();
                System.out.println("已連線Client");
                // Displaying that new client is connected to server
                System.out.println("New client connected " + client.getInetAddress().getHostAddress());
                System.out.println("--------------------------------Server--------------------------------");

                // create a new thread object
                ClientHandler clientSock = new ClientHandler(client);

                // This thread will handle the client separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
