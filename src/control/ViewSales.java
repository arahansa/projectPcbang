package control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.People;

public class ViewSales {
	@SuppressWarnings("unchecked")
	public static ArrayList<People> load(String fileName){
		ArrayList<People> p = new ArrayList<>();
		ObjectInputStream ina;
		try{
			System.out.println(fileName+"에 대한 조회");
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ina = new ObjectInputStream(bis);

			// 객체를 읽을 때는 출력한 순서와 일치해야한다.
			// 그러므로 순서를 가진 ArrayList를 추천한다.
			p = (ArrayList<People>) ina.readObject();
			for (int i = 0; i < p.size(); i++) {
				System.out.println(p.get(i));
			}
			
			ina.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return p;
		
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			Date today = new Date();
			SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
			String fileName = sdf1.format(today)+".dat"; //2014-05-09.dat 식으로 파일명이 생긴다.
			File f = new File(fileName);
			System.out.println(f.isFile());
			System.out.println("in 진입");
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			// 객체를 읽을 때는 출력한 순서와 일치해야한다.
			// 그러므로 순서를 가진 ArrayList를 추천한다.
			ArrayList<People> peoples = new ArrayList<People>();
			peoples = (ArrayList<People>) in.readObject();
			in.close();
			System.out.println("inclose");
			for (int i = 0; i < peoples.size(); i++) {
				System.out.println(peoples.get(i));
			}
			
			load(fileName);
			

		} catch (Exception e) {
		}
	}
}
