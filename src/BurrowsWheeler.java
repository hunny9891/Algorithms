import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
	public static void encode() {
		String s = BinaryStdIn.readString();
		CircularSuffixArray csa = new CircularSuffixArray(s);

		for (int i = 0; i < s.length(); i++) {
			if (csa.index(i) == 0) {
				BinaryStdOut.write(i);
				break;
			}
		}

		for (int i = 0; i < s.length(); i++) {
			if (csa.index(i) == 0)
				BinaryStdOut.write(s.charAt(s.length() - 1));
			else
				BinaryStdOut.write(s.charAt(csa.index(i) - 1));
		}
		BinaryStdOut.close();
	}

	public static void decode() {
		int first = BinaryStdIn.readInt();
		String s = BinaryStdIn.readString();

		char[] t = s.toCharArray();
		int[] next = new int[t.length];
		char[] firstCol = new char[s.length()];

		System.arraycopy(t, 0, firstCol, 0, s.length());
		Arrays.sort(firstCol);

		HashMap<Character, ArrayList<Integer> > map = new HashMap<Character, ArrayList<Integer> >();
		for (int i = 0; i < t.length; i++) {
			if(!map.containsKey(t[i])) {
				map.put(t[i], new ArrayList<Integer>());
			}
			map.get(t[i]).add(i);
		}
		// Build next
		for (int i = 0; i < t.length; i++) {
			char ch = firstCol[i];
			if (map.containsKey(ch)) {
				next[i] = map.get(ch).get(0);
				map.get(ch).remove(0);
			}
		}

		// Reconstruct
		int curr = first;
		for (int i = 0; i < next.length; i++) {
			BinaryStdOut.write(firstCol[curr]);
			curr = next[curr];

		}
		BinaryStdOut.close();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args[0].equals("+")) {
			decode();
		} else if (args[0].equals("-")) {
			encode();
		}

	}

}
