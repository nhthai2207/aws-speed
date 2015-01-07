package forkjoin.file;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import forkjoin.Utils;

public class ParallelStreaming {
	static long countLineNumber;

	public static void process() {				
		
		LongStream.range(0, countLineNumber).parallel().filter(i -> i % Utils.numberLinePerThread == 0).mapToObj(from -> {
			long to = from + Utils.numberLinePerThread - 1;			
			Map<String, List<WordIndex>> result = null;
			try {
				Utils.processPart(from, to);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;

		}).count();

	}

	public static void main(String args[]) {
		countLineNumber = Utils.countLineNumber(Utils.fileLocation);
		System.out.println("line number " + countLineNumber);
		long t1 = Calendar.getInstance().getTimeInMillis();
		process();
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("Time = %s ms ", (t2 - t1)));

	}
}
