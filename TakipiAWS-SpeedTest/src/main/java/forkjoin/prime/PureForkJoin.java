package forkjoin.prime;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import forkjoin.Utils;

public class PureForkJoin extends RecursiveTask<Boolean> {
	private static final long serialVersionUID = -1883115459616562727L;
	private BigInteger primeNumber;
	private BigInteger from;
	private BigInteger to;
	
	static BigInteger lengthForThread = Utils.sqrt.divide(Utils.numberOfThread);

	public PureForkJoin(BigInteger src, BigInteger start, BigInteger length) {
		this.primeNumber = src;
		this.from = start;
		this.to = length;
	}

	private boolean computeDirectly() {
		boolean isPrime = true;
		for (BigInteger i = from; i.compareTo(to) <= 0; i = i.add(BigInteger.ONE)) {
			if (this.primeNumber.mod(i).equals(BigInteger.ZERO)) {
				isPrime = false;
			}
		}
		return isPrime;
	}

	@Override
	protected Boolean compute() {
		BigInteger tmp = to.subtract(from);
		if (tmp.compareTo(lengthForThread) <= 0) {
			return computeDirectly();			
		}
		BigInteger middle = to.add(from).divide(Utils.two);
		PureForkJoin leftJoin = new PureForkJoin(primeNumber, from, middle);
		PureForkJoin rightJoin = new PureForkJoin(primeNumber, middle.add(BigInteger.ONE), to);
		return leftJoin.compute() && rightJoin.compute();
		
	}

	public static void main(String[] args) {
		PureForkJoin fb = new PureForkJoin(Utils.primeNumber, Utils.two, Utils.sqrt);
		ForkJoinPool pool = new ForkJoinPool();
		long t1 = Calendar.getInstance().getTimeInMillis();
		Boolean isPrime = pool.invoke(fb);
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("Time = %s ms ; result = %s", (t2 - t1), isPrime));

	}
}