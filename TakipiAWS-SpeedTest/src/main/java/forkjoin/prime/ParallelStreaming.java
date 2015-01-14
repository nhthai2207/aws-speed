package forkjoin.prime;

import java.math.BigInteger;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import forkjoin.Utils;

public class ParallelStreaming {
	
	

	public boolean isPrime() {

		// http://stackoverflow.com/questions/23442183/using-a-semaphore-inside-a-nested-java-8-parallel-stream-action-may-deadlock-is
		// System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(Utils.numberOfThread.intValue()));
		//Set<BigInteger> collect = list.parallelStream().filter(i -> TestPrime.primeNumber.mod(i).equals(BigInteger.ZERO)).collect(Collectors.toSet());
		
		//Stream<BigInteger> bigIntStream = Stream.iterate(Utils.two, n -> n.add(BigInteger.ONE)).limit(TestPrime.sqrt.longValue());
		//Set<BigInteger> collect = Stream.iterate(Utils.two, n -> n.add(BigInteger.ONE)).limit(TestPrime.sqrt.longValue()).parallel().filter(i -> TestPrime.primeNumber.mod(i).equals(BigInteger.ZERO)).collect(Collectors.toSet());
		
		Set<BigInteger> collect = Stream.iterate(Utils.two, n -> n.add(TestPrime.lengthForThread)).limit(TestPrime.numberOfThread.longValue()).parallel().filter(i -> Utils.primeProcessPart(i)).collect(Collectors.toSet());
		return collect.size() == 0;
	}

	/*public void createList(BigInteger start, BigInteger end) {
	 * private List<BigInteger> list = new LinkedList<>();
		for (BigInteger i = Utils.two; i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
			list.add(i);
		}
	}*/
	
}
