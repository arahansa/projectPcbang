package control;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.People;

public class SaveSales {
	public static void Save(ArrayList<People> people) {
		ArrayList<People> firstPeople = new ArrayList<People>();
		ArrayList<People> newList = new ArrayList<People>();
		try {
			Date today = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			String fileName = sdf1.format(today) + ".dat"; // 2014-05-09.dat 식으로
															// 파일명이 생긴다.

			File f = new File(fileName);
			if (f.isFile()) {
				firstPeople = ViewSales.load(fileName);
				newList.addAll(firstPeople);
				System.out.println("파일이 이미 존재하여서  불러와서 덮어 씁니다~");
			} else {
				System.out.println("파일이 존재하지않아서 새로 씁니다.");

			}
			
			
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream out = new ObjectOutputStream(bos);
			newList.addAll(people);
			out.writeObject(newList);
			out.close();
			System.out.println("직렬화 종료");
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {

	}

}
