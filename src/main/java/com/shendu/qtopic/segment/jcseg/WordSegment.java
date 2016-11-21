package com.shendu.qtopic.segment.jcseg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.lionsoul.jcseg.tokenizer.core.ADictionary;
import org.lionsoul.jcseg.tokenizer.core.DictionaryFactory;
import org.lionsoul.jcseg.tokenizer.core.ISegment;
import org.lionsoul.jcseg.tokenizer.core.IWord;
import org.lionsoul.jcseg.tokenizer.core.JcsegException;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;
import org.lionsoul.jcseg.tokenizer.core.SegmentFactory;

public class WordSegment {
	private static ArrayList<String> FileList = new ArrayList<String>();

	/**
	 * @param filepath
	 *            输入路径
	 * @return 返回路径下的每一个文件
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public static List<String> readDirs(String filepath)
			throws FileNotFoundException, IOException {
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("输入的[]");
				System.out.println("filepath:" + file.getAbsolutePath());
			} else {
				String[] flist = file.list();
				for (int i = 0; i < flist.length; i++) {
					File newfile = new File(filepath + "/" + flist[i]);
					if (!newfile.isDirectory()) {
						FileList.add(newfile.getAbsolutePath());
					} else if (newfile.isDirectory()) // if file is a directory,
														// call ReadDirs
					{
						readDirs(filepath + "/" + flist[i]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return FileList;
	}
	
	/**
	 * segment an whole text.
	 * @param text
	 * @throws IOException
	 */
	public static void segment(String text,String out) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(out));

		// 创建JcsegTaskConfig分词任务实例
		// 即从jcseg.properties配置文件中初始化的配置
		JcsegTaskConfig config = new JcsegTaskConfig(
				"/home/zkpk/workspace/qtopic/src/main/resources/jcseg.properties");
		// 创建默认词库(即: com.webssky.jcseg.Dictionary对象)
		ADictionary dic = DictionaryFactory.createDefaultDictionary(config);
		// 依据JcsegTaskConfig配置中的信息加载全部jcseg词库.
		// dic.loadFromLexiconDirectory(config, config.getLexiconPath());
		// 要被分词的文本
		// str = TestAnalyzer.ReadLocal();
		// 依据给定的分词流对象, ADictionary和JcsegTaskConfig来创建ISegment
		// 通常使用SegmentFactory来创建ISegment对象
		// 将config和dic组成一个Object数组给SegmentFactory.createJcseg方法
		// JcsegTaskConfig.COMPLEX_MODE表示创建ComplexSeg复杂ISegment分词对象
		// JcsegTaskConfig.SIMPLE_MODE表示创建SimpleSeg简易Isegmengt分词对象.
		ISegment seg = null;
		try {
			seg = SegmentFactory.createJcseg(JcsegTaskConfig.COMPLEX_MODE,
					new Object[] { new StringReader(text), config, dic });
		} catch (JcsegException e) {
			e.printStackTrace();
		}
		// 获取分词结果
		IWord word = null;
		while ((word = seg.next()) != null) {
			if (word.getValue().length() > 1) {
				System.out.println(word.getValue()+"    "+word.getValue().length());
				bw.write(word.getValue() + " ");
				
			}
		}
		bw.newLine();
		bw.flush();
		bw.close();
	}


	/**
	 * segment a whole file line by line.
	 * 
	 * @param path
	 *            输入文件的路径
	 * @throws IOException
	 */

	public static void segment(File in, File out) throws IOException {
		BufferedReader br = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader(in));
			while (br.ready()) {
				line = br.readLine();
				segment(line,out.getPath() + File.separator + in.getName() + ".seg");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
		}

	}
	
	/**
	 * segment a whole file line by line.
	 * 
	 * @param ins
	 *            输入文件的路径(multiple)
	 * @throws IOException
	 */

	public static void segment(File[] ins, File out) throws IOException {
		for (int i = 0; i < ins.length; i++) {
			segment(ins[i],out);
		}
	}


	
	public static void main(String[] args) throws IOException{
		
		String in = "/home/zkpk/workspace/qtopic/dataset/";
		String out = "/home/zkpk/workspace/qtopic/seg-result/";
		
		File inFile = new File(in);
		if (!inFile.isDirectory()) {
			segment(inFile,new File(out));
		} else {
			segment(inFile.listFiles(),new File(out));
		}
		
	}
}
