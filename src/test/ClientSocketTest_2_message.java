package test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocketTest_2_message {
	public static void main(String[] args) {
		Socket socket;
		String serverIp = "127.0.0.1";
		DataOutputStream dos=null;
		
		try {
			socket = new Socket(serverIp, 7777);
			System.out.println("접속 성공");
			dos=new DataOutputStream(socket.getOutputStream());
			while(true){
				System.out.print("할말 : ");
				
				Scanner input = new Scanner(System.in);
				String message = input.nextLine();
				dos.writeUTF("클라이언트의 메시지 : "+message);
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
