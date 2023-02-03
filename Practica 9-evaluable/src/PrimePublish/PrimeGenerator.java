package PrimePublish;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


public class PrimeGenerator {
	private static List<Integer> list = new ArrayList<>();
	private static Semaphore mutex = new Semaphore(1, true);
	static {
		list.add(2);
		list.add(3);
	}
	public static int getPrime(int pos) throws Exception {
		if (pos < 0) { throw new Exception(Panel.messages.getString("getPrime_exception"));
		}else {
		mutex.acquire();
		while (pos >= list.size())
			computeNextPrime();
		int ret = list.get(pos);
		mutex.release();
		return ret;
		}
		}
		
	
	private static int computeNextPrime() {
		int number= list.get(list.size() - 1);
		do {
			number += 2;
		} while (! isPrime(number));
		list.add(number);
		return number;
	}

	private static boolean isPrime(int number) {
		for(int prime: list)
			if (number % prime == 0) return false;
		return true;
	}

}
