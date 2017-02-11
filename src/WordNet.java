import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {
	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null)
			throw new NullPointerException();

		synsetList = new ArrayList<SynsetNode>();
		addSynset(synsets);

		graph = new Digraph(synsetList.size());
		constructHypernymGraph(hypernyms);

		if (testCycle())
			throw new IllegalArgumentException();

		nounMap = new HashMap<>();
		sap = new SAP(graph);

		for (SynsetNode node : synsetList) {
			String[] nounSet = node.synset.split(" ");
			for (String noun : nounSet)
				if (!nounMap.containsKey(noun)) {
					ArrayList<Integer> idList = new ArrayList<Integer>();
					idList.add(node.id);
					nounMap.put(noun, idList);
				} else {
					nounMap.get(noun).add(node.id);
				}
		}
		
		nounList = new ArrayList<>();
		TreeSet<String> setOfNouns = new TreeSet<String>();
		for (SynsetNode node : synsetList) {
			String[] nounSet = node.synset.split(" ");
			for (String noun : nounSet) {
				setOfNouns.add(noun);
			}
		}

		
		for (String noun : setOfNouns) {
			nounList.add(noun);
		}
		
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return nounList;
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if (word == null)
			throw new NullPointerException();

		return isNoun(nounList, word, 0, nounList.size());
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new NullPointerException();
		
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException();

		Iterable<Integer> v = nounMap.get(nounA);
		Iterable<Integer> w = nounMap.get(nounB);

		return sap.length(v, w);
	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new NullPointerException();
		
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException();

		Iterable<Integer> v = nounMap.get(nounA);
		Iterable<Integer> w = nounMap.get(nounB);

		int ancestorId = sap.ancestor(v, w);
		String ancestor = "";
		for (SynsetNode node : synsetList) {
			if (node.id == ancestorId) {
				ancestor = node.synset;
			}
			
		}
		return ancestor;
	}

	// make appropriate data structures to store the synset.
	private void addSynset(String filename) {
		if (filename == null)
			throw new NullPointerException();
		BufferedReader br = openFileReader(filename);
		try {
			while (true) {
				String input = br.readLine();
				String[] temp;
				if (input == null)
					break;
				temp = input.split(",");
				SynsetNode node = new SynsetNode(Integer.parseInt(temp[0]),
						temp[1], temp[2]);
				synsetList.add(node);
			}
		} catch (IOException e) {
			System.out.println("OOPs something messed up!");
		}
	}

	private BufferedReader openFileReader(String filename) {
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(new FileReader(filename));
		} catch (IOException ex) {
			System.out.println("Please enter a valid filename");
			ex.printStackTrace();
		}
		return rd;
	}

	private void constructHypernymGraph(String filename) {
		if (filename == null)
			throw new NullPointerException();
		BufferedReader br = openFileReader(filename);
		try {
			while (true) {
				String input = br.readLine();
				String[] temp;
				if (input == null)
					break;
				temp = input.split(",");
				int v = Integer.parseInt(temp[0]);
				for (int i = 1; i < temp.length; i++) {
					int w = Integer.parseInt(temp[i]);
					graph.addEdge(v, w);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private boolean isNoun(ArrayList<String> list, String key, int lo, int hi) {
		if (hi < lo)
			return false;
		else {
			int mid = lo + (hi - lo) / 2;
			if (key.compareTo(list.get(mid)) == 0)
				return true;
			else if (key.compareTo(list.get(mid)) < 0)
				return isNoun(list, key, lo, mid - 1);
			else
				return isNoun(list, key, mid + 1, hi);

		}

	}

	private boolean testCycle() {
		DirectedCycle finder = new DirectedCycle(graph);
		
		
		return finder.hasCycle();
	}

	/*private int dfs(Digraph graph, int v, int rootCount) {
		boolean marked[] = new boolean[graph.V()];
		for(int vertex:graph.adj(v)){
			if(!marked[v]) {
				dfs(graph, vertex, rootCount);
				marked[vertex] = true;
			}
			if(graph.indegree(vertex) == 0) {
				rootCount++;
			}
		}
		
		return rootCount;
	}*/

	// Unit Testing
	public static void main(String[] args) {
		edu.princeton.cs.algs4.Stopwatch watch = new edu.princeton.cs.algs4.Stopwatch();
		String synset = args[0];
		String hypernym = args[1];
		WordNet wordnet = new WordNet(synset, hypernym);
		//WordNet wordnet = new WordNet("wordnet/synsets15.txt", "wordnet/hypernyms15Path.txt");
		System.out.println(watch.elapsedTime());
		System.out.printf("edges = %d, vertices %d\n", wordnet.graph.E(),
				wordnet.graph.V());
		/*//System.out.println(wordnet.distance("a", "c"));
		System.out.println(wordnet.sap("dark", "light"));
		System.out.println(wordnet.sap("cat", "dog"));
		System.out.println(wordnet.sap("coffee", "tea"));
		System.out.println(wordnet.sap("dark", "light"));
		System.out.println(wordnet.sap("dark", "light"));
		System.out.println(wordnet.sap("dark", "light"));
		System.out.println(wordnet.sap("dark", "light"));
		System.out.println(watch.elapsedTime());*/

	}

	private class SynsetNode {
		public SynsetNode(int id, String synset, String gloss) {
			this.id = id;
			this.synset = synset;
			this.gloss = gloss;
		}

		int id;
		String synset;
		String gloss;
	}

	private ArrayList<SynsetNode> synsetList;
	private Digraph graph;
	private HashMap<String, ArrayList<Integer>> nounMap;
	private SAP sap;
	private ArrayList<String> nounList;

}
