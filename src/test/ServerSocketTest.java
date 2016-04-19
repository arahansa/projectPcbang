package test;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try{
			serverSocket = new ServerSocket(7777);
			System.out.println("새로운 서버 시작 ");
			socket = serverSocket.accept();
			System.out.println(socket.getInetAddress()+"에서 접속함.");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
