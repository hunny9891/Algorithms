import java.util.Arrays;
import java.util.Scanner;

public class Temp {

	public static void main(String[] args) {
		System.out.println("Enter N");
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		String[] array = new String[n];
		for (int i = 0; i < n; i++) {
			array[i] = scanner.next();
		}
		System.out.println(Arrays.toString(array));
		partition(array, 0, n - 1);
		System.out.println("-------------------------");
		System.out.println(Arrays.toString(array));
		scanner.close();
	}

	private static void partition(String[] a, int lo, int hi) {
		int i = lo, j = hi + 1;
		while (true) {
			while (less(a[++i], a[lo]))
				if (i == hi)
					break;
			while (less(a[lo], a[--j]))
				if (j == lo)
					break;

			if (i >= j)
				break;
			exch(a, i, j);
		}
		exch(a, lo, j);
	}

	private static void exch(String[] a, int i, int j) {
		String temp = a[i];
		a[i] = a[j];
		a[j] = temp;

	}

	private static boolean less(String comparable, String comparable2) {
		if (comparable.charAt(0) >= comparable2.charAt(0))
			return false;
		else
			return true;

	}
}
