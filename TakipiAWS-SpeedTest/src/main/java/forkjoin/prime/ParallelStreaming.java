package forkjoin.prime;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import forkjoin.Utils;

public class ParallelStreaming {
	static List<BigInteger> list = new LinkedList<>();
	private static BigInteger prime = Utils.primeNumber;

	public static boolean isPrime() {
		Set<BigInteger> collect = list.parallelStream().filter(j -> prime.mod(j).equals(0)).collect(Collectors.toSet());		
		return collect.size() == 0;
	}

	private static void createList(BigInteger start, BigInteger end) {
		for (BigInteger i = Utils.two; i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
			list.add(i);
		}
	}

	public static void main(String args[]) {
		createList(Utils.two, Utils.sqrt);
		long t1 = Calendar.getInstance().getTimeInMillis();
		boolean isPrime = isPrime();
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("Time = %s ms ; result = %s", (t2 - t1), isPrime));
	}
}
