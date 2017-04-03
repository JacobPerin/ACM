/**
 * @author Jacob G. Perin
 * Note : Approved Implementation. First Dynamic Programming attempt. Messy :(
 * Link : https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1072
 * Problem 10131 in UVa Online Judge Archive -- Need to rename class to "Main" for submission
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
========
6008 1300
6000 2100
500 2000
1000 4000
1100 3000
6000 2000
8000 1400
6000 1200
2000 1900

*/
class P10131 {
	
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
	
	public static void main(String args[]) // entry point from OS
	{
		P10131 myWork = new P10131(); // create a dynamic instance
		myWork.begin(); // the true entry point
	}
	
	void begin(){
		Scanner sc;
		String input;

		ArrayList<elephant> arr = new ArrayList<elephant>();
		int initPos = 1;

		while ((input = P10131.readLn(255)) != null) {
			sc = new Scanner(input);

			arr.add(new elephant(sc.nextInt(), sc.nextInt(), initPos));

			initPos++;

		}
		
		solve(arr);
		
		System.exit(0);
	}

	private static void solve(ArrayList<elephant> arr) {

		/* Sort elephant by weight */
		Collections.sort(arr, new elephantComparator());

		/* Cut out any possibilities of cycles */
		removeDuplicates(arr);

		/* Push initial elephant to stack */
		recursiveSearch(arr, 0);
		
		int maxIndex = -1, maxSize = -1;
		for(int j = 0; j < arr.size();j++){
			if(arr.get(j).children.size() > maxSize){
				maxSize = arr.get(j).children.size();
				maxIndex = j;
			}
		}
		
		/* Print out the solution */
		System.out.println(maxSize + 1);
		System.out.println(arr.get(maxIndex).initPos);
		for(int j = 0; j < arr.get(maxIndex).children.size(); j++){
			System.out.println(arr.get(maxIndex).children.get(j));
		}
	}
	
	
	
	private static Map<Integer, Integer> memo = new HashMap<Integer, Integer>();
	

	
	private static int recursiveSearch(ArrayList<elephant> arr, int index) {

		if (index >= arr.size()) {
			
			memo.put(arr.get(--index).initPos, --index);
			return 0;
		}

		elephant e1 = arr.get(index);
		elephant e2;
		
		if (memo.containsKey(e1.initPos)) {
			e2 = arr.get(index - 1);
			if(e1.weight < e2.weight && e1.IQ > e2.IQ)
				return e1.children.size();
			else
				return 0;
			
		} else {
			int edges = -1, storedIndex = -1;

			for (int j = index; j < arr.size(); j++) {
				int temp = 0;
				e2 = arr.get(j);
				
				/* Continue growing off of root */
				if (e1.weight < e2.weight && e1.IQ > e2.IQ) {
					
					/* Find the current nodes children */
					temp += recursiveSearch(arr, j);

					temp += e2.children.size();
					
					if(temp > edges){
						edges = temp;
						storedIndex = j;
					}
				}
			}
			/* No more nodes left to search for */
			if(storedIndex == -1){
				edges = 0;
			}else {
				e1.children.add(arr.get(storedIndex).initPos);
				e1.children.addAll(arr.get(storedIndex).children);
			}

			/* Find number of children branching of this edge */
			memo.put(e1.initPos, edges);

			return recursiveSearch(arr, ++index);
		}
	}

	private static class elephant {
		public int weight, IQ, initPos;

		public ArrayList<Integer> children;

		public elephant(int weight, int IQ, int initPos) {
			this.weight = weight;
			this.IQ = IQ;
			this.initPos = initPos;

			this.children = new ArrayList<Integer>();
		}
	}

	private static class elephantComparator implements Comparator<elephant> {
		@Override
		public int compare(elephant e1, elephant e2) {
			if (e1.weight - e2.weight == 0) {
				return e2.IQ - e1.IQ;
			} else {
				return e1.weight - e2.weight;
			}
		}
	}
	
	private static void removeDuplicates(ArrayList<elephant> arr) {
		elephantComparator c = new elephantComparator();
		for (int i = 0; i < arr.size() - 1; i++) {
			if (c.compare(arr.get(i), arr.get(i + 1)) == 0) {
				arr.remove(i + 1);
			}
		}
	}
}