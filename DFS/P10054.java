
/**
 * @author Jacob G. Perin
 * 
 * Broken attempt for trying to complete P10054
 * --> First time using DFS --> Modified to better suit problem
 * --> First time using Adjacency list
 * 
 * Problem(s):
 *  1) Adjacency list creation is extremely slow
 *  2) Last sample case broken w/ current implementation
 *  	--> Test case 3 w/ 55 samples looks correct but comes short
 */

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;


/*
========
2
5
1 2
2 3
3 4
4 5
5 6
5
2 1
2 2
3 4
3 1
2 4

1
6
1 2
2 3
3 4
4 5 
5 1
3 3

1
55
1 40
20 15
27 16
1 22
28 24
6 49
16 9
14 10
46 25
40 12
25 37
40 24
36 1
18 24
20 5
9 12
24 35
10 19
46 15
23 8
16 20
18 1
12 32
44 30
9 35
19 24
16 12
22 27
40 23
1 20
15 1
31 22
40 47
44 40
30 46
18 10
32 37
30 25
37 40
1 7
31 22
27 27
8 27
18 1
46 6
40 24
40 37
47 5
10 8
9 49
27 15
40 14
36 25
7 30
8 28

*/

class P10054 {

	/* ############## TRY 1 ############## */

//	static Map<Integer, Integer> colors = new HashMap<Integer, Integer>();
//	static ArrayList<Integer> keys = new ArrayList<Integer>();
//
//	static ArrayList<bead> beads = new ArrayList<bead>();
//
//	static String readLn(int maxLg) // utility function to read from stdin
//	{
//		byte lin[] = new byte[maxLg];
//		int lg = 0, car = -1;
//
//		try {
//			while (lg < maxLg) {
//				car = System.in.read();
//				if ((car < 0) || (car == '\n'))
//					break;
//				lin[lg++] += car;
//			}
//		} catch (IOException e) {
//			return (null);
//		}
//
//		if ((car < 0) && (lg == 0))
//			return (null); // eof
//		return (new String(lin, 0, lg));
//	}
//
//	public static void main(String[] args) {
//		Main myWork = new Main(); // create a dynamic instance
//		myWork.begin(); // the true entry point
//	}
//
//	void begin() {
//		Scanner sc;
//
//		sc = new Scanner(Main.readLn(255));
//		int C = sc.nextInt();
//
//		for (int i = 0; i < C;) {
//			sc = new Scanner(Main.readLn(255));
//			int N = sc.nextInt();
//
//			for (int j = 0; j < N; j++) {
//				sc = new Scanner(Main.readLn(255));
//
//				insert(sc.nextInt(), sc.nextInt());
//			}
//
//			System.out.println("Case #" + ++i);
//
//			if (beadsLost()) {
//				System.out.println("some beads may be lost\n");
//			} else {
//				printInOrder();
//				System.out.print('\n');
//			}
//
//			colors.clear();
//			keys.clear();
//			beads.clear();
//		}
//
//		sc.close();
//
//		System.exit(0);
//	}
//
//	void insert(int a, int b) {
//
//		if (colors.containsKey(a)) {
//			/* Increment map val */
//			colors.put(a, colors.get(a) + 1);
//		} else {
//			/* Initialize map val */
//			colors.put(a, 1);
//
//			keys.add(a);
//		}
//
//		if (colors.containsKey(b)) {
//			colors.put(b, colors.get(b) + 1);
//		} else {
//			colors.put(b, 1);
//
//			keys.add(b);
//		}
//
//		beads.add(new bead(a, b));
//	}
//
//	boolean beadsLost() {
//		for (int i = 0; i < keys.size(); i++) {
//			int beadCount = colors.get(keys.get(i));
//
//			if (beadCount % 2 > 0) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	void printInOrder() {
//		int size = beads.size();
//		bead curr = beads.remove(0);
//
//		for (int i = 0; i < size; i++) {
//			System.out.println(curr.a + " " + curr.b);
//
//			for (int j = 0; j < beads.size(); j++) {
//
//				if (curr.b == beads.get(j).a) {
//					curr = beads.get(j);
//					beads.remove(j);
//					break;
//
//				} else if (curr.b == beads.get(j).b) {
//					curr = beads.get(j);
//					beads.remove(j);
//
//					int temp;
//					temp = curr.a;
//					curr.a = curr.b;
//					curr.b = temp;
//					break;
//				}
//			}
//		}
//	}
//
//	class bead {
//		int a, b;
//
//		bead(int a, int b) {
//			this.a = a;
//			this.b = b;
//		}
//	}
	
	/* ############## TRY 2 -- DFS Implementation ############## */

	Map<Integer, Integer> colors = new HashMap<Integer, Integer>();
	ArrayList<Integer> keys = new ArrayList<Integer>();

	ArrayList<LinkedList<bead>> adjlist = new ArrayList<LinkedList<bead>>();

	static String readLn(int maxLg) // utility function to read from stdin
	{
		byte lin[] = new byte[maxLg];
		int lg = 0, car = -1;

		try {
			while (lg < maxLg) {
				car = System.in.read();
				if ((car < 0) || (car == '\n'))
					break;
				lin[lg++] += car;
			}
		} catch (IOException e) {
			return (null);
		}

		if ((car < 0) && (lg == 0))
			return (null); // eof
		return (new String(lin, 0, lg));
	}

	public static void main(String[] args) {
		P10054 myWork = new P10054(); // create a dynamic instance
		myWork.begin(); // the true entry point

		System.exit(0);
	}

	void begin() {
		Scanner sc;

		sc = new Scanner(P10054.readLn(255));
		int C = sc.nextInt();

		for (int i = 0; i < C;) {
			sc = new Scanner(P10054.readLn(255));
			int N = sc.nextInt();

			for (int j = 0; j < N; j++) {
				sc = new Scanner(P10054.readLn(255));

				insert(sc.nextInt(), sc.nextInt());
			}
			
			System.out.println("Case #" + ++i);
			
			if(lost())
				System.out.print("some beads may be lost\n\n");
			else{
				pair();
				dfs();
			}

			adjlist.clear();
			colors.clear();
			keys.clear();
		}

		sc.close();
	}

	void insert(int a, int b) {

		/* Add slot for linked list node in adjacency list */
		LinkedList<bead> list = new LinkedList<bead>();
		
		bead root = new bead(a, b);
		root.indexOf(adjlist.size());
		list.add(root);
		
		adjlist.add(list);

		/* Add/Increment color count */
		if (colors.containsKey(a))
			colors.put(a, colors.get(a) + 1);
		else{
			colors.put(a, 1);
			keys.add(a);
		}

		if (colors.containsKey(b))
			colors.put(b, colors.get(b) + 1);
		else{
			colors.put(b, 1);
			keys.add(b);
		}
	}

	void pair() {
		
		/* Duplicates are a special case, and will mess w/ dfs if they aren't placed first */
		ArrayList<Integer> dup = new ArrayList<Integer>();
		
		for(int j = 0; j < adjlist.size(); j++){
			bead root = adjlist.get(j).getFirst();
			for(int i = 0; i < adjlist.size(); i++){
				bead chk = adjlist.get(i).getFirst();
				
				if(chk.a != chk.b) continue;
				else dup.add(i);
				
				pairHelper(root, i, j);
			}
		}
		
		
		for (int j = 0; j < adjlist.size(); j++) {
			bead root = adjlist.get(j).getFirst();
			for (int i = 0; i < adjlist.size(); i++) {
				if(dup.contains(i)) continue;
				
				pairHelper(root, i, j);
			}
		}
	}
	
	void pairHelper(bead root, int inner, int outer){
		
		if(inner == outer) return;
		
		bead cmp = adjlist.get(inner).getFirst();

		if (root.a == cmp.a || root.a == cmp.b || root.b == cmp.b || root.b == cmp.a) {
			adjlist.get(outer).add(cmp);
		}
	}
	
	void dfs(){
		
		boolean[] V = new boolean[adjlist.size()];
		
		/* Queue to hold 'static' middle and left beads --> 
		 * for determining whether to connect the next bead 
		 * of the necklace */
		ArrayDeque<bead> Q = new ArrayDeque<bead>(2);
		

		dfsR(Q, V, dfsHelper(Q, V));
		
		System.out.print('\n');
	}
	
	int dfsHelper(ArrayDeque<bead> Q, boolean[] V){
		
		bead start = adjlist.get(0).getFirst();
		V[0] = true;
		
		bead second  = adjlist.get(0).get(1);
		
		if(start.b != second.a){
			int temp = start.b;
			start.b = start.a;
			start.a = temp;
		}
		
		System.out.println(start.a + " " + start.b);
		
		Q.add(start);
		Q.add(second);
		
		return second.i;
	}
	
	void dfsR(ArrayDeque<bead> Q, boolean[] V, int v){
		V[v] = true;
		
		display(Q.peekFirst(),adjlist.get(v).getFirst());
		
		/* Create list iterator --> Skip the root */
		ListIterator<bead> iter = adjlist.get(v).listIterator(1);

		while(iter.hasNext()){
			bead b = iter.next();
			if(!V[b.i] && valid(Q, b)){
				dfsR(Q ,V, b.i);
			}
		}
	}

	boolean valid(ArrayDeque<bead> Q, bead vC){
		
		if(Q.size() < 2);
		else{
			/* Middle bead is static. Meaning that only the new bead is 
			 * capable of changing orientation */
			bead cmp = Q.peekLast();
			if(cmp.b != vC.a && cmp.b != vC.b){
				return false;
			}
		}
		
		if(Q.size() >= 2) Q.remove();
		Q.add(vC);
		
		return true;
	}
	
	void display(bead beadA, bead beadB){
		if(beadA.b == beadB.a){
			System.out.println(beadB.a + " " + beadB.b);
		}
		else{
			int temp = beadB.b;
			beadB.b = beadB.a;
			beadB.a = temp;
			
			System.out.println(beadB.a + " " + beadB.b);
		}
	}
	
	class bead {
		int a, b, i;

		bead(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		void indexOf(int i){
			this.i = i;
		}
	}

	boolean lost() {
		for (int i = 0; i < keys.size(); i++) {
			int beadCount = colors.get(keys.get(i));

			if (beadCount % 2 > 0) {
				return true;
			}
		}
		return false;
	}
}