package forkjoin.file;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import forkjoin.Utils;

public class SerializeThread {
	static Map<String, List<WordIndex>> result = new HashMap<String, List<WordIndex>>();

	public static void main(String[] args) {
		try {
			long t1 = Calendar.getInstance().getTimeInMillis();
			process();
			long t2 = Calendar.getInstance().getTimeInMillis();
			System.out.println(String.format("Time = %s ms ; number of words = %s, number of 'it' = %s", (t2 - t1), result.size(), result.get("it").size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void process() throws Exception {
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(Utils.fileLocation);
			sc = new Scanner(inputStream, "UTF-8");
			int line = 0;
			while (sc.hasNextLine()) {
				String s = sc.nextLine();
				String[] words = s.trim().split("\\s+");
				for (int i = 0; i < words.length; i++) {
					if (!Utils.isEmptyString(words[i])) {
						if (result.get(words[i]) == null) {
							result.put(words[i], new LinkedList<WordIndex>());
						}
						result.get(words[i]).add(new WordIndex(line, i));
					}
				}
				line++;
			}
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}
	}

}
