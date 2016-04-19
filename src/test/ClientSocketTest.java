package test;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketTest {
	public static void main(String[] args) {
		Socket socket;
		String serverIp = "127.0.0.1";
		
		try {
			socket = new Socket(serverIp, 7777);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
