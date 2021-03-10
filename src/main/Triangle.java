package main;
//By Mathew Lawrence 17354272
public class Triangle implements Comparable {

	public int [] points;
	public double dotProduct;
	
	
	
	
	
	
	@Override
	public int compareTo(Object obj) {

	    // Implementing the interface requires that we provide this
	    // compare function
	    
		Triangle t= (Triangle)obj;
		if (t.dotProduct == dotProduct)
		    return 0;
		else if (t.dotProduct > dotProduct)
		    return -1;
		else
		    return 1;
	    
	}

}
