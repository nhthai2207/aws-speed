package forkjoin.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import forkjoin.Utils;

public class ParallelStreaming {	
	static long numberLinePerThread = 3;

	public static Map<String, List<WordIndex>> process() {
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(Utils.numberOfThread.intValue()));
		long countLineNumber = Utils.countLineNumber(Utils.fileLocation);
		List<Map<String, List<WordIndex>>> resultsList = LongStream.range(0, countLineNumber).parallel().filter(i -> i % numberLinePerThread == 0).mapToObj(from -> {
			Map<String, List<WordIndex>> result = new HashMap<String, List<WordIndex>>();
			try {
				long to = from + numberLinePerThread - 1;
				InputStream is = new FileInputStream(Utils.fileLocation);
				is.skip(from * 1024);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String s = "";
				long line = from;
				while ((s = reader.readLine()) != null && line <= to) {
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
				is.close();
			} catch (Exception e) {

			}
			return result;
		}).collect(Collectors.toList());
		
		Map<String, List<WordIndex>> results = new HashMap<String, List<WordIndex>>();
		for (Map<String, List<WordIndex>> map : resultsList){			
			for (String key : map.keySet()) {
				if (results.get(key) == null) {
					results.put(key, map.get(key));
				}else{
					results.get(key).addAll(map.get(key));
				}
			}
		}
		return results;
	}

	public static void main(String args[]) {
		long t1 = Calendar.getInstance().getTimeInMillis();
		Map<String, List<WordIndex>> process = process();
		long t2 = Calendar.getInstance().getTimeInMillis();		
		System.out.println(String.format("Time = %s ms ; number of words = %s, number of 'it' = %s", (t2 - t1), process.size(), process.get("it").size()));
	}
}
