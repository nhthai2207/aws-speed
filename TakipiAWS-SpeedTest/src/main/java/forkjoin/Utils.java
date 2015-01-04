package forkjoin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigInteger;
import java.util.Random;

public class Utils {
	public static BigInteger two = new BigInteger("2");
	public static BigInteger three = new BigInteger("3");

	public static BigInteger primeNumber ;
	public static BigInteger sqrt;
	public static BigInteger numberOfThread = new BigInteger("10");
	public static String fileLocation = "test.txt";
	static {
		//primeNumber = new BigInteger("10969639"); // 24 bit 
		primeNumber = new BigInteger("253587964573397"); // 48 bit //1.4ms
		//primeNumber = new BigInteger("253587964573341"); // Not prime
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
	
	public static boolean isEmptyString(String s){
		return s == null || "".equals(s.trim());
	}	
	
	public static long countLineNumber(String fileLocation) {
		long lines = 0;
		try {
			File file = new File(fileLocation);
			LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
			lineNumberReader.skip(Long.MAX_VALUE);
			lines = lineNumberReader.getLineNumber();
			lineNumberReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException Occured" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException Occured" + e.getMessage());
		}
		return lines;
	}
}
