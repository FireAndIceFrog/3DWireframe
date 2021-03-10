package main;
//By Mathew Lawrence 17354272
import java.util.Scanner;
import java.util.Vector;
//pointReference represents a matrix of all the points
/*	PointReference:
 * 			VECTOR: (0.0,-1.0,2.0)
 * 			VECTOR: (1.0,0.0,2.0)
 * 			VECTOR: (0.0,1.0,-2.0)
 * 			VECTOR: (0.0,-1.0,-2.0)
 */
public class PointReference	{
	private int dimens = 0;
	private int FinalSize = 0;
	private double [][] points = null;
	public PointReference(Scanner scan, int dimens) throws Exception{
		this.dimens = dimens;
		//for each point
		
		//capture the size of the PRef
		FinalSize = scan.nextInt();
		points = new double[FinalSize][dimens];
		int k = 0;
		while(!scan.hasNextInt()) {
			//create a smaller vector - the row of the matrix
			double[] row = new double[dimens];
			//for each column in the row
			for(int i = 0; i < dimens; ++i  ) {
				if(scan.hasNext()) {
					try {
						//remove white space and turn it into a double
						double item = Double.parseDouble(RemoveWhiteSpace(scan.next()));
						//add the double into the row
						row[i] = (item);
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
			points[k++]=(row);
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
		for(double[] rows : points) {
			int i = 0;
			for(double item : rows) {
				System.out.printf("\t\t%2.7f",item);
				++i;
			}
			if(i >= dimens) {
				i = 0;
				System.out.print("\n");
			}
				
		}
	}
	
	public double[][] getArray() {
		return points;
	}
	
	

}
