package com.shendu.qtopic.lda;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadPhi {
	BufferedReader br = null;

	public void getWorldn() throws IOException {
		try {
			int topsize = ReadTheta.getTopic().size();
			String str = null;
			int indexOne = 0;
			for (int i = 0; i < topsize; i++) {
				indexOne = Integer.parseInt(ReadTheta.getTopic().get(i)
						.toString());
				int count = 1;
				br = new BufferedReader(new FileReader(
						"models/casestudy-cn/model-final.phi"));
				while ((str = br.readLine()) != null) {
					if (indexOne == count) {
						String[] worldid = str.split(" ");
						double[] d = new double[worldid.length];
						for (int n = 0; n < worldid.length; n++) {
							d[n] = Double.parseDouble(worldid[n]);

						}
						// System.out.println(d[0]);
						System.out.println(sort(d, 4));// 贡献率最大的前四个worldID
						break;
					}
					count++;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] rags) {
		ReadPhi rp = new ReadPhi();
		try {
			rp.getWorldn();

		} catch (IOException e) {
			e.printStackTrace();
		}
		// double[] list = {1.001,2.002,1.82,3.23,0.09,1.87};
		// System.out.println(sort(list, 3));

	}

	/**
	 * 去一个数组中最大的几个数的下标
	 * 
	 * @param nums
	 *            数组
	 * @param y
	 *            取几个数
	 * @return
	 */
	public static List<Integer> sort(double[] nums, int y) {
		Double[] sortArray = new Double[nums.length];
		for (int i = 0; i < nums.length; i++) {
			sortArray[i] = nums[i];
		}
		Arrays.sort(sortArray);
		int length = sortArray.length - 1;
		List<Integer> idxList = new ArrayList<Integer>();
		int index = 0;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < nums.length; j++) {
				if (nums[j] == sortArray[length - i]) {
					index = j;
				}
			}
			idxList.add(index + 1);
		}
		return idxList;
	}

}
