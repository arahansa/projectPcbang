package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest_2_message {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		DataInputStream dis=null;
		DataOutputStream dos=null;
		
		try{
			serverSocket = new ServerSocket(7777);
			System.out.println("새로운 서버 시작 ");
			
			while(true){
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress()+"에서 접속함.");
				
				dis=new DataInputStream(socket.getInputStream());
				while (dis!=null) {
					System.out.println(dis.readUTF());
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
