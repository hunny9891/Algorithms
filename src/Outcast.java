import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Outcast {

	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		String outcast = "";
		int distance = 0;
		for (int i = 0; i < nouns.length; i++) {
			int dist = computeSum(nouns[i], nouns);
			if(dist > distance) {
				outcast = nouns[i];
				distance = dist;
			}
		}
		return outcast;
	}

	private int computeSum(String noun, String[] nouns) {
		int distance = 0;
		for (int i = 0; i < nouns.length; i++) {
			distance += wordnet.distance(noun, nouns[i]);
		}
		return distance;
	}

	public static void main(String[] args) {
		Stopwatch watch = new Stopwatch();
		WordNet wordnet = new WordNet(args[0], args[1]);
		System.out.println(watch.elapsedTime());
		Outcast outcast = new Outcast(wordnet);
		for (int t = 2; t < args.length; t++) {
			In in = new In(args[t]);
			String[] nouns = in.readAllStrings();
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
			System.out.println(watch.elapsedTime());
		}
	}

	private WordNet wordnet;

}
