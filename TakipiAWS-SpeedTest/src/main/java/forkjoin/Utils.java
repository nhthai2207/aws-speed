package forkjoin;

import java.math.BigInteger;
import java.util.Random;

public class Utils {
	public static BigInteger two = new BigInteger("2");
	public static BigInteger three = new BigInteger("3");

	public static BigInteger primeNumber ;
	public static BigInteger sqrt;
	public static BigInteger numberOfThread = new BigInteger("10");
	static {
		//primeNumber = new BigInteger("10969639"); // 24 bit 
		primeNumber = new BigInteger("253587964573397"); // 48 bit //1.4ms
		//primeNumber = new BigInteger("1079364038048305033"); // 60 bit // 90096ms
		
		//primeNumber = BigInteger.probablePrime(24, new Random());
		sqrt = Utils.sqrt(primeNumber);
		System.out.println("Number test : " + primeNumber);
	}
	public static BigInteger sqrt(BigInteger n) {
		BigInteger a = BigInteger.ONE;
		BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
		while (b.compareTo(a) >= 0) {
			BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
			if (mid.multiply(mid).compareTo(n) > 0)
				b = mid.subtract(BigInteger.ONE);
			else
				a = mid.add(BigInteger.ONE);
		}
		return a.subtract(BigInteger.ONE);
	}
}
