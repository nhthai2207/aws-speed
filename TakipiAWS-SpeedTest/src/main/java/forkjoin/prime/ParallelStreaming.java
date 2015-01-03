package forkjoin.prime;

import java.math.BigInteger;
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
		System.out.println(collect.size());
		return false;
	}

	private static void createList(BigInteger start, BigInteger end) {
		for (BigInteger i = Utils.two; i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
			list.add(i);
		}
	}

	public static void main(String args[]) {

		BigInteger sqrt = Utils.sqrt(prime);
		createList(Utils.two, sqrt);
		long startTime = System.currentTimeMillis();
		isPrime();
		long timeTaken = System.currentTimeMillis() - startTime;
		System.out.println("Time taken: " + timeTaken);
	}
}
