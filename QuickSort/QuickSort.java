
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/*
 * Compute the total number of comparisons used 
 * to sort the given input file by QuickSort
 */
class QuickSort{

	private int[] array;
	private long count = 0; // Total number of comparisons
	private int option;
	
	/**
	 * Set how to choose pivot
	 * @param option
	 * 1: using first element 
	 * 2: using last element 
	 * 3: using median element
	 */
	public void setOption(int option) {
		this.option = option;
	}

	/**
	 * Set array
	 * @param array
	 */
	public void setArray(int[] array) {		
		this.array = new int[array.length];
		for(int i = 0; i < array.length; i ++) {
			this.array[i] = array[i]; 
		}
	}

	/**
	 * @return the array
	 */
	public int[] getArray() {
		return array;
	}

	/**
	 * Choose pivot
	 * @param start
	 * @param end
	 * */
	private int choosePivot(int start, int end) {
		int pivotIndex = start;
		// Use first element
		if (option == 1) {
			return start;
		}
		// Use final element
		if (option == 2) {
			pivotIndex = end;
		} else {
			// Use "median-of-three" pivot rule
			int middle = (end - start) / 2 + start;
			Map<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();
			treeMap.put(array[start], start);
			treeMap.put(array[middle], middle);
			treeMap.put(array[end], end);

			int x = 0;
			for (Integer i : treeMap.values()) {
				if (x == 1) {
					pivotIndex = i;
					break;
				}
				x++;
			}
		}
		int temp = array[start];
		array[start] = array[pivotIndex];
		array[pivotIndex] = temp;
		return start;
	}

	/**
	 * Partition array around pivot
	 * @param start
	 * @param end
	 * @param pivotIndex
	 * */
	private int partition(int start, int end, int pivotIndex) {
		count += (end - start);

		int p = array[pivotIndex];
		int i = start, j;
		for (j = i; j <= end; j++) {
			if (array[j] < p) {
				int temp = array[i + 1];
				array[i + 1] = array[j];
				array[j] = temp;
				i++;
			}
		}
		// Put pivot into correct position
		array[start] = array[i];
		array[i] = p;

		// Return boundary: 1st, 2nd part
		return i;
	}

	/**
	 * Implement quick-sort
	 * @param start
	 * @param end
	 * */
	public void quickSort(int start, int end) {
		if (end <= start)
			return;

		// Choose pivot
		int pivotIndex = choosePivot(start, end);

		// Partition array around pivot
		int partitionIndex  = partition(start, end, pivotIndex);

		// Recursively sort 1st and 2nd part
		quickSort(start, partitionIndex  - 1);
		quickSort(partitionIndex + 1, end);
	}
	
	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}
	
	public static void main(String[] args) {

		// Read numbers from file into array
		String filename = "QuickSort.txt";
		int length = 0;
		int[] array = null; 
		File file = new File(filename);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				length++;
			}
			array = new int[length];
			int i = 0;
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				array[i] = Integer.parseInt(line);
				i++;
			}
		} catch (IOException e) {
			System.out.println("File Not Found ... ");
		}
		
		// Implement QuickSort
		System.out.println("Compute the total number of comparisons used by QuickSort ...");

		for(int option = 1; option <= 3; option ++){
			QuickSort Sort = new QuickSort();
			Sort.setOption(option);
			Sort.setArray(array);		
			Sort.quickSort(0, array.length - 1);
			if(option == 1){
				System.out.println("--- Option 1: Use first element as pivot --- ");
			}else if(option == 2){
				System.out.println("--- Option 2: Use last element as pivot --- ");
			}else{
				System.out.println("--- Option 3: Use median element as pivot --- ");
			}
			System.out.println(Sort.getCount());
		}
	}
}
