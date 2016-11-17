package com.shendu.qtopic.lda;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadTheta {
	
	
	/**
	 * Doc-topic  获取每个topicID
	 * @return  topicID
	 * @throws IOException
	 */
	

	public static List getTopic() throws IOException {
		BufferedReader br = null;
		String filename = "models/casestudy-cn/model-final.theta";
		ArrayList list=new ArrayList();
		try {
			br = new BufferedReader(new FileReader(filename));
			while (br.ready()) {
				
				String[] line = br.readLine().split(" ");
				Double max = 0.00000;
				int index=0;
				for (int i = 0; i < line.length; i++) {
					if (Double.parseDouble(line[i]) > max) {
						max = Double.parseDouble(line[i]);
						index=i+1;
					}
				}
//				System.out.println(index);//数组下标
				
				list.add(index);
			
			}
//			System.out.println(list);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return list;

	}
	public static void main(String[] args){
		try {
//			ReadTheta.getTopic();
			System.out.println(ReadTheta.getTopic());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
