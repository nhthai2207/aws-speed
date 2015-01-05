package forkjoin.file;

import java.util.Calendar;
import java.util.HashMap;
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
			long to = from + numberLinePerThread - 1;
			Map<String, List<WordIndex>> result = null;
			try {
				result = Utils.processPart(from, to);
			} catch (Exception e) {		
				e.printStackTrace();
			}
			return result;

		}).collect(Collectors.toList());

		Map<String, List<WordIndex>> results = new HashMap<String, List<WordIndex>>();
		for (Map<String, List<WordIndex>> map : resultsList) {
			for (String key : map.keySet()) {
				if (results.get(key) == null) {
					results.put(key, map.get(key));
				} else {
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
