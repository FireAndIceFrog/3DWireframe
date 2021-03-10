package main;
//By Mathew Lawrence 17354272
import java.util.Scanner;
import java.util.Vector;
/*
 * Vector:
 * 		Vector : (1 , 2 , 3)
 * 		Vector : (1 , 4 , 2)
 * 		Vector : (2 , 3 , 1)
 * 
 * 
 * 
 * 
 */
public class VectorReference{
	private int dimens = 0;
	private int FinalSize = 0;
	private int [][] triangles;
	public VectorReference(Scanner scan, int dimens) throws Exception{
		this.dimens = dimens;
		//for each point
		
		//capture the size of the PRef
		FinalSize = scan.nextInt();
		triangles = new int[FinalSize][dimens];
		int k = 0;
		while(scan.hasNext()) {
			//create a smaller vector - the row of the matrix
			int[] row = new int[dimens];
			//for each column in the row
			for(int i = 0; i < dimens; ++i  ) {
				if(scan.hasNext()) {
					try {
						//remove white space and turn it into a double
						int item = Integer.parseInt(RemoveWhiteSpace(scan.next()));
						//add the double into the row
						row[i] = item;
					} catch (Exception e ) {
						System.out.println("Error, cannot convert to double");
						e.printStackTrace();
					}
					//add the scan's next double
					
				} else {
					//if there is not enough items, this is an error
					throw new Exception("Column count doesnt match items. Insefficient Double Items.");
				}
				
			}	
			triangles[k++] = (row);
		}
		
		
		
		
	}
	//function to remove tabs and spaces
	private String RemoveWhiteSpace(String item) {
		if(item.contains(" ")) {
			item.replace(" ", "");
		}
		if(item.contains("\t")) {
			item.replace("\t", "");
		}		
		final String result = item;
		return result;
	}
	public void printReference(){
		for(int[] rows : triangles) {
			int i = 0;
			for(int item : rows) {
				System.out.printf("\t\t%7d",item);
				++i;
			}
			if(i >= dimens) {
				i = 0;
				System.out.print("\n");
			}
				
		}
	}
	public boolean printSize( Scanner sourceDocument ) {
		boolean correct = true;
		//delete later
		return correct;
	}
	public int[][] getArray() {
		// TODO Auto-generated method stub
		return triangles;
	}
	
	
	
}
