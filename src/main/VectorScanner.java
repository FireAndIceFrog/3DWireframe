package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;  // Import the Scanner class
import java.util.Vector;
//By Mathew Lawrence 17354272
public class VectorScanner {
	private int dimens = 0;
	private PointReference PRef= null;
	private VectorReference VRef = null;
	private Scanner sourceDocument = null;
	
	public VectorScanner(int dimens){
		this.dimens = dimens;
		
	}
	
	public PointReference getPointReference() {
		return PRef;
	}
	public VectorReference getTriangleReference() {
		return VRef;
	}
	
	
	//get the data from the file
	public void setFile(final File sourceFile) throws Exception, FileNotFoundException {
		sourceDocument = new Scanner(sourceFile);
		// take out the first set of points, leaving only the last set of instructions to create the vector
		PRef = new PointReference(sourceDocument, dimens);
		
		
		//create the second vector containing the directions of the vectors
		VRef = new VectorReference(sourceDocument, dimens);
			
		//VRef.printReference();
		System.out.println("Data is in the system");
		
		

		
		
	}
	public double[][] getNodes(){
		PRef.printReference();
		return PRef.getArray();
	}
	public int[][] getTriangles(){
		VRef.printReference();
		return VRef.getArray();
	}
	
	

	
	
}
