package com.shendu.qtopic.segment.jcseg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

public class StopWordRemover {

	public static void main(String[] args) throws IOException {

		WordSegment.getPath();
		File stopWordTable = new File("/root/resource/data/StopWordTable.txt");
		File srcFile = new File("/mnt/hgfs/D/WebServiceData/data/Analyzer.txt");
		File destFile = new File(
				"/mnt/hgfs/D/WebServiceData/ldadata/analyzerstopword.txt");
		fileExcludeStopWord(stopWordTable, srcFile, destFile);

	}

	public static int count(File srcFile) throws IOException {
		int lines = 0;
		long fileLength = srcFile.length();
		LineNumberReader rf = null;
		rf = new LineNumberReader(new FileReader(srcFile));
		if (rf != null) {
			// int lines=0;
			rf.skip(fileLength);
			lines = rf.getLineNumber();
			rf.close();
		}

		return lines;
	}

	private static void fileExcludeStopWord(File stopWordTable, File srcFile,
			File destFile) {

		// TODO Auto-generated method stub
		BufferedReader srcFileBr = null;
		BufferedReader StopWordFileBr = null;
		BufferedWriter destFileBw = null;
		try {
			srcFileBr = new BufferedReader(new InputStreamReader(
					new FileInputStream(srcFile)));
			StopWordFileBr = new BufferedReader(new InputStreamReader(
					new FileInputStream(stopWordTable)));
			destFileBw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(destFile)));

			Set<String> stopWordSet = new HashSet<String>();
			String stopWord = null;
			destFileBw.write(String.valueOf(StopWordRemover.count(srcFile)));
			System.out.println(StopWordRemover.count(srcFile));
			destFileBw.newLine();
			for (; (stopWord = StopWordFileBr.readLine()) != null;) {
				stopWordSet.add(stopWord);
			}
			// System.out.println("111111"+stopWordSet);

			String paragraph = null;

			for (; (paragraph = srcFileBr.readLine()) != null;) {
				// System.out.println("%%%  %%%%"+count);
				// System.out.println(paragraph);
				String[] resultArray = paragraph.split(" ");
				StringBuffer finalStr = new StringBuffer();

				for (int i = 0; i < resultArray.length; i++) {

					if (stopWordSet.contains(resultArray[i])) {
						resultArray[i] = null;

					} else {
						finalStr = finalStr.append(resultArray[i]).append(" ");
					}
				}
				System.out.println(finalStr.toString());
				destFileBw.write(finalStr.toString());
				// System.out.println(finalStr);
				destFileBw.newLine();

			}

			StopWordFileBr.close();
			srcFileBr.close();
			destFileBw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
