package forkjoin.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import forkjoin.Utils;
// http://stackoverflow.com/questions/11867348/concurrent-reading-of-a-file-java-preffered/11868557#11868557
public class QueuePool {
	private File file;

	public QueuePool(File file) {
		this.file = file;
	}

	public Callable<Map<String, List<WordIndex>>> processPartTask(final long start, final long end) {
		return new Callable<Map<String, List<WordIndex>>>() {
			public Map<String, List<WordIndex>> call() throws Exception {
				return Utils.processPart(start, end);
			}
		};
	}

	public Map<String, List<WordIndex>> processAll(int noOfThreads, int chunkSize) throws Exception {	
		int count = (int) ((file.length() + chunkSize - 1) / chunkSize);	
		java.util.List<Callable<Map<String, List<WordIndex>>>> tasks = new ArrayList<Callable<Map<String, List<WordIndex>>>>(count);
		for (int i = 0; i < count; i++)
			tasks.add(processPartTask(i * chunkSize, Math.min(file.length(), (i + 1) * chunkSize)));	
		ExecutorService es = Executors.newFixedThreadPool(noOfThreads);
		java.util.List<Future<Map<String, List<WordIndex>>>> invokes = es.invokeAll(tasks);
		es.shutdown();
		Map<String, List<WordIndex>> results = new HashMap<String, List<WordIndex>>();
		for (Future<Map<String, List<WordIndex>>> result : invokes){
			Map<String, List<WordIndex>> map = result.get();
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

	public static void main(String argv[]) throws Exception {
		long t1 = Calendar.getInstance().getTimeInMillis();
		QueuePool s = new QueuePool(new File(Utils.fileLocation));
		Map<String, List<WordIndex>> processAll = s.processAll(8, 1024);
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("Time = %s ms ; number of words = %s, number of 'it' = %s", (t2 - t1), processAll.size(), processAll.get("it").size()));
	}
}