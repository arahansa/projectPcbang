package backup;
/**
 * 자바의 정석에서 나온 멀티채팅에  GUI를 입힘.
 * GUI제작 : 아둔한사 (http://adunhansa.tistory.com)
 * 추가로 할일 또는 궁금증 : 쓰레드가 왜 계속 생기는지.. 궁금하고..
 * 또 해볼일은 현재 접속자 명단을 클라이언트 창에서 보이게 하고 싶다.
 * 2014/04/23
 */



import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.rmi.ConnectException;
import java.util.Scanner;

import javax.swing.*;

public class GuiMultiChat_TcpClient {
	//닉네임은 main과 GUI 클래스에서 쓰이며 
	// ta, tf 는 쓰레드와 GUI
	//socket 은 
	//static 
	static String nick=null;
	static JTextArea ta;
	static JTextField tf;
	static DataOutputStream out;
	
	public static void main(String args[]){
		nick = JOptionPane.showInputDialog("당신의 대화명은?");
		@SuppressWarnings("unused")
		FrClient f = new FrClient(nick);
		Socket socket;
		try{
			String serverIp = "127.0.0.1";
			//소켓을 생성하여 연결을 요청
			socket = new Socket(serverIp, 7777);
			System.out.println("서버에 연결되었습니다");
			
			Thread receiver = new ClientReceiver(socket);
			receiver.start();
			Thread sender = new ClientSender(socket, nick);
			sender.start();
		
		}catch(ConnectException e){
			e.printStackTrace();
			System.out.println("접속에러");
		}catch(Exception e){
			System.out.println(" 에러는 에러");
		}
		
	}//main
	
	static class ClientSender extends Thread{
		Socket socket;
		
		String name;
		
		ClientSender(Socket socket, String name){
			this.socket = socket;
			try{
				out = new DataOutputStream(socket.getOutputStream());
				this.name = name;
			}catch(Exception e){
				System.out.println("클라이언트 생성자 중 에러");
			}
		}
		
		public void run(){
			Scanner scanner = new Scanner(System.in);
			try{
				//이것은 접속하면서 대화명 날리는 if문이다.
				if(out!=null){
					out.writeUTF(name);
				}
				while(out!=null){
					out.writeUTF("["+name+"]"+scanner.nextLine());	
					
				}
			}catch(IOException e){
				System.out.println(" 클라이언트 센더 실행중 io 에러");
			}
		}//run
	}//clientSender
	
	//리시버 시작
	static class ClientReceiver extends Thread{
		Socket socket;
		DataInputStream in;
		
		ClientReceiver(Socket socket){
			this.socket = socket;
			
			try{
				in = new DataInputStream(socket.getInputStream());
			}catch(IOException e){ 
				System.out.println("클라이언트 리시버 실행중 입출력에러");
			}
		}
		
		public void run(){
			while(in!=null){
				try{
					String s=in.readUTF();
					System.out.println(s);
					ta.append(s+"\n");
				}catch(IOException e){
					System.out.println("클라이언트 리시버 메소드 실행중 입출력 에러");
				}
			}
		}
		
	}// 클라이언트 리시버 끝
	
	
	//gui 클래스
	@SuppressWarnings("serial")
	static class FrClient extends JFrame implements ActionListener{
		
		
		public FrClient(String nick){
			setSize(600, 400);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle(nick+"의 채팅방 클라이언트!"); 
			setLayout(new BorderLayout());
			
			JLabel label = new JLabel("Test Client Chatting room");
			ta= new JTextArea(25, 40);
			tf = new JTextField(25);
			tf.addActionListener(this);
			
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			//(궁금) 여기서 왼쪽에 붙여서 정렬할려면.. 어떻게..
			panel.setComponentOrientation(
	                ComponentOrientation.LEFT_TO_RIGHT);
			JLabel label_name = new JLabel(nick+"님 ");
			panel.add(label_name,BorderLayout.WEST);
			panel.add(tf, BorderLayout.CENTER);
			
			
			
			add(label, BorderLayout.NORTH);
			add(ta, BorderLayout.CENTER);
			add(panel, BorderLayout.SOUTH);
			
			setVisible(true);
			
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==tf){
				//이거는 그냥 gui 에 나오게 하기 위한 것이다~ 
				//ta.append(nick + " : "+tf.getText()+"\n");
				try {
					out.writeUTF("["+nick+"]"+tf.getText());
					tf.setText("");
				} catch (IOException e1) {
					System.out.println("gui  상에서 메시지 보내는게 에러");
				}
			}
		}
	} 
}//class
