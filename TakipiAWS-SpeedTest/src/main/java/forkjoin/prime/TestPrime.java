package forkjoin.prime;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import forkjoin.Utils;

public class TestPrime {
	public static BigInteger primeNumber ;
	public static BigInteger sqrt;
	public static BigInteger lengthForThread;
	
	public static void testPrime(int request, String sPrime) throws Exception{
		primeNumber = new BigInteger(sPrime);
		sqrt = Utils.sqrt(primeNumber);
		lengthForThread = TestPrime.sqrt.divide(Utils.numberOfThread);
		for (int i = 0; i < 10; i++) {
			switch (request) {
			case 1:
				testForkJoin();
				Thread.sleep(2000);
				break;
			case 2:
				testParalleStreaming();
				Thread.sleep(2000);
				break;
			case 3:
				testLocalQueue();
				Thread.sleep(2000);
				break;
			case 4:
				testSingleThread();
				Thread.sleep(2000);
				break;

			}
		}
		
	}

	private static void testSingleThread() throws Exception {
		long t1 = Calendar.getInstance().getTimeInMillis();
		SerializeThread mc = new SerializeThread();
		boolean isprime = mc.isprime(primeNumber);		
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println( t2 - t1);
	}

	private static void testLocalQueue() throws Exception {
		long t1 = Calendar.getInstance().getTimeInMillis();
		boolean isPrime = true;
		BigInteger lengthForThread = sqrt.divide(Utils.numberOfThread);
		int numberOfThread = Utils.numberOfThread.intValue();
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThread);
		List<Callable<Boolean>> list = new ArrayList<Callable<Boolean>>();
		for (BigInteger i = Utils.two; i.compareTo(sqrt) <= 0; i = i.add(lengthForThread)) {
			Callable<Boolean> worker = new QueuePool(primeNumber, i, i.add(lengthForThread));
			list.add(worker);
		}
		List<Future<Boolean>> invokeAll = executor.invokeAll(list);
		executor.shutdown();
		while(executor.isTerminated()){}
		for (Future<Boolean> future : invokeAll) {
			isPrime &=  future.get();
		}
		long t2 = Calendar.getInstance().getTimeInMillis();
			
		System.out.println(t2 - t1);		
	}

	private static void testParalleStreaming() {
		ParallelStreaming mc = new ParallelStreaming();
		mc.createList(Utils.two, sqrt);		
		long t1 = Calendar.getInstance().getTimeInMillis();		
		boolean isPrime = mc.isPrime();
		long t2 = Calendar.getInstance().getTimeInMillis();		

		System.out.println(t2 - t1);
	}

	private static void testForkJoin() {
		PureForkJoin fb = new PureForkJoin(primeNumber, Utils.two, sqrt);
		ForkJoinPool pool = new ForkJoinPool();
		long t1 = Calendar.getInstance().getTimeInMillis();
		Boolean isPrime = pool.invoke(fb);
		long t2 = Calendar.getInstance().getTimeInMillis();		
		System.out.println(t2 - t1);
	}

}
