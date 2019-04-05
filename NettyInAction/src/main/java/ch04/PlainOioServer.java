package ch04;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * copy at 2019年04月05日17:08:57，
 */
public class PlainOioServer {
    public void serve(int port) throws IOException {
        // 将服务器绑定到指定端口
        final ServerSocket socket = new ServerSocket(port);
        for (;;) {
            // 接受连接
            final Socket clientSocket = socket.accept();
            System.out.println("Accepted connection from " + clientSocket);
            // 创建一个新的线程来处理该连接
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OutputStream out;

                    try {
                        out = clientSocket.getOutputStream();
                        // 写消息给已连接的客户端
                        out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                        out.flush();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            clientSocket.close(); // 关闭连接
                        } catch (IOException e) {
                            // ignore on close
                        }
                    }
                }
            }).start();  // 启动线程
        }
    }
}
