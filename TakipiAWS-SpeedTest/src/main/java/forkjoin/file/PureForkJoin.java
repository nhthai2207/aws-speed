package forkjoin.file;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import forkjoin.Utils;

public class PureForkJoin extends RecursiveTask {

	private long from;
	private long to;
	private Map<String, List<WordIndex>> result = new HashMap<String, List<WordIndex>>();
	static long numberLinePerThread = 3;

	public PureForkJoin(long from, long to) {
		this.from = from;
		this.to = to;
	}

	

	@Override
	protected Object compute() {
		try {
			if (to - from <= numberLinePerThread) {
				this.result = Utils.processPart(from, to);
				return null;
			}
			long middle = (to + from) / 2;
			PureForkJoin leftJoin = new PureForkJoin(from, middle);
			PureForkJoin rightJoin = new PureForkJoin(middle + 1L, to);
			invokeAll(leftJoin, rightJoin);			
			for (String key : leftJoin.result.keySet()) {
				this.result.put(key, leftJoin.result.get(key));
			}
			for (String key : rightJoin.result.keySet()) {
				if (this.result.get(key) == null) {
					this.result.put(key, rightJoin.result.get(key));
				}else{
					this.result.get(key).addAll(rightJoin.result.get(key));
				}
			}

		} catch (Exception e) {

		}
		return null;
	}

	public static void main(String[] args) {
		long countLineNumber = Utils.countLineNumber(Utils.fileLocation);
		PureForkJoin fb = new PureForkJoin(0L, countLineNumber-1);
		ForkJoinPool pool = new ForkJoinPool();
		long t1 = Calendar.getInstance().getTimeInMillis();
		pool.invoke(fb);
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("Time = %s ms ; number of words = %s, number of 'it' = %s", (t2 - t1), fb.result.size(), fb.result.get("it").size()));
		List<WordIndex> list = fb.result.get("it");
		System.out.println("Data for words 'it'" );
		for (WordIndex wordIndex : list) {
			System.out.println(wordIndex.toString());
		}

	}
}