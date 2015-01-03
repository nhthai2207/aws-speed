package forkjoin.prime;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import forkjoin.Utils;

class WorkerThread implements Callable<Boolean> {

	private BigInteger from;
	private BigInteger to;
	private BigInteger primeNumber;

	public WorkerThread(BigInteger primeNumber, BigInteger from, BigInteger to) {
		this.primeNumber = primeNumber;
		this.from = from;
		this.to = to;
	}

	@Override
	public Boolean call() {
		boolean isPrime = true;
		for (BigInteger i = from; i.compareTo(to) <= 0; i = i.add(BigInteger.ONE)) {
			if (this.primeNumber.mod(i).equals(BigInteger.ZERO)) {
				isPrime = false;
			}
		}
		return isPrime;
	}

}

public class QueuePool {

	public static void main(String[] args) throws Exception {
		boolean isPrime = true;
		long t1 = Calendar.getInstance().getTimeInMillis();
		BigInteger lengthForThread = Utils.sqrt.divide(Utils.numberOfThread);
		int numberOfThread = Utils.numberOfThread.intValue();
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThread);
		List<Callable<Boolean>> list = new ArrayList<Callable<Boolean>>();
		for (BigInteger i = Utils.two; i.compareTo(Utils.sqrt) <= 0; i = i.add(lengthForThread)) {
			Callable<Boolean> worker = new WorkerThread(Utils.primeNumber, i, i.add(lengthForThread));
			list.add(worker);
		}
		List<Future<Boolean>> invokeAll = executor.invokeAll(list);
		executor.shutdown();
		while(executor.isTerminated()){}
		for (Future<Boolean> future : invokeAll) {
			isPrime &=  future.get();
		}
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("Time = %s ms ; result = %s", (t2 - t1), isPrime));
	}
}