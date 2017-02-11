
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
	private static final int R = 256;

	public static void encode() {
		char[] r = getASCIISequence();
		char ch;
		while (!BinaryStdIn.isEmpty()) {
			ch = BinaryStdIn.readChar();
			int i;
			for (i = 0; i < R; i++) {
				if (ch == r[i]) {
					BinaryStdOut.write((char) i);
					swap(r, 0, i);
					break;
				}
			}

			insert(r, ch, i);
		}
		BinaryStdOut.close();
	}

	

	public static void decode() {
		char[] sequence = getASCIISequence();
		char ch;
		while (!BinaryStdIn.isEmpty()) {
			ch = BinaryStdIn.readChar();
			BinaryStdOut.write(sequence[(int) ch]);
			swap(sequence, 0, (int) ch);
			insert(sequence, ch, (int) ch);
		}
		BinaryStdOut.flush();
	}
	private static void swap(char[] r2, int i, int j) {
		char temp = r2[i];
		r2[i] = r2[j];
		r2[j] = temp;
	}
	
	private static void insert(char[] r2, char ch, int pos) {
		for (int i = pos; i > 1; i--) {
			swap(r2, i, i - 1);
		}
	}
	
	private static char[] getASCIISequence() {
		char[] ch = new char[R];
		for (char i = 0; i < R; i++)
			ch[i] = i;
		return ch;
	}

	public static void main(String[] args)  {
		if (args[0].equals("-"))
			encode();
		else if (args[0].equals("+"))
			decode();

	}

}
