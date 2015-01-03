package forkjoin.prime;

import java.math.BigInteger;
import java.util.Calendar;

import forkjoin.Utils;

public class SerializeThread {
	
	public static void main(String[] args) {
		long t1 = Calendar.getInstance().getTimeInMillis();
		boolean isprime = isprime(Utils.primeNumber);
		System.out.println();
		long t2 = Calendar.getInstance().getTimeInMillis();
		System.out.println(String.format("Time = %s ms ; result = %s", (t2 - t1), isprime));
	}

	public static boolean isprime(BigInteger n) {
		if (n.compareTo(BigInteger.ONE) == 0 || n.compareTo(Utils.two) == 0) {
			return true;
		}
		BigInteger root = Utils.sqrt(n);
		for (BigInteger i = Utils.three; i.compareTo(root) <= 0; i = i.add(BigInteger.ONE)) {
			if (n.mod(i).equals(BigInteger.ZERO)) {
				return false;
			}
		}
		return true;
	}

}
