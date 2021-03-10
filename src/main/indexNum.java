package main;
//By Mathew Lawrence 17354272
public class indexNum implements Comparable<indexNum> {

    // Some additional properties of objects of this class
    public int index;
    
    // Arrays of objects of this class will be sorted according to
    // this property
    public double dotProduct;
    public boolean visible;
    public double depth;

    public indexNum(int n, double z, double y) 
    {
	this.index = n;
	this.dotProduct = z;
	this.depth = y;
	setVisible();
    }
    void setVisible() {
    	if(dotProduct > 0 )
			visible = true;
		else 
			visible = false;
    }

    // Implementing the interface requires that we provide this
    // compare function
	@Override
	public int compareTo(indexNum obj) {
		indexNum t= (indexNum)obj;
		if (t.depth == depth)
		    return 0;
		else if (t.depth < depth)
		    return -1;
		else
		    return 1;
	}
	

}
