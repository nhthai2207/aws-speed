package forkjoin.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
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

	private void computeDirectly() throws Exception {		
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
	}

	@Override
	protected Object compute() {
		try {
			if (to - from <= numberLinePerThread) {
				computeDirectly();
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
	}
}