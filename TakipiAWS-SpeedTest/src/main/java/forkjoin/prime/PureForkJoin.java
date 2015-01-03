package forkjoin.prime;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import forkjoin.Utils;

public class PureForkJoin extends RecursiveAction {
	private static final long serialVersionUID = -1883115459616562727L;
	private BigInteger primeNumber;
	private BigInteger from;
	private BigInteger to;
	static AtomicBoolean isPrime = new AtomicBoolean();
	protected static BigInteger lengthForThread = new BigInteger("10000");
	static {
		isPrime.set(true);
	}

	public PureForkJoin(BigInteger src, BigInteger start, BigInteger length) {
		this.primeNumber = src;
		this.from = start;
		this.to = length;
	}

	
	protected void computeDirectly() {
		for (BigInteger i = from; i.compareTo(to) <= 0; i = i.add(BigInteger.ONE)) {
			if (isPrime.get()) {
				if (this.primeNumber.mod(i).equals(BigInteger.ZERO)) {
					isPrime.set(false);
					return;
				}
			}
		}
	}
	
	@Override
	protected void compute() {
		BigInteger tmp = to.subtract(from);
		if (tmp.compareTo(lengthForThread) <= 0) {
			computeDirectly();
			return;
		}
		BigInteger middle = to.divide(Utils.two);
		invokeAll(new PureForkJoin(primeNumber, from, middle), new PureForkJoin(primeNumber, middle.add(BigInteger.ONE), to));
	}

	public static void main(String[] args) {
		BigInteger prime = Utils.primeNumber;
		
		BigInteger sqrt = Utils.sqrt(prime);
		PureForkJoin fb = new PureForkJoin(prime, Utils.two, sqrt);
		ForkJoinPool pool = new ForkJoinPool();
		long t1 = Calendar.getInstance().getTimeInMillis();
		pool.invoke(fb);
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("Time = %s ms ; result = %s", (t2 - t1), isPrime));

	}
}