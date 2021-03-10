package main;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

// An inner class to handle the final rendering of the figure by Mathew Lawrence 17354272
class DisplayPanel extends JPanel {
	double	[][] 	nodes 		= null;
    int		[][] 	triangles 	= null;
    double 	[] 		eye;
    boolean 		scaled 		= false;
    double			XAngle		= 0.0;
    double			YAngle		= 0.0;
    double			ZAngle		= 0.0;
	

	
	public void setEye(double [] eye ) {
		if(eye == null) {
			double[] temp = {0, 0, -1};
			this.eye = temp ;
		} else {
			this.eye = eye;
		}
	
	}
	boolean checkNull() {
		if(triangles == null || nodes == null) {
			return true;
		}
		return false;
	}
	public void setTriangles(int[][] triangles) {
		System.out.println("Triangles set");
		this.triangles = triangles;
		repaint();
	}
	public void setNodes ( double[][] nodes) {
		System.out.println("Nodes set");
		this.nodes = nodes;
		repaint();
	}

    public DisplayPanel(int[][] triangles, double [][] nodes) {
    	setEye(null);
    	this.triangles = triangles;
    	this.nodes = nodes;
        setPreferredSize(new Dimension(640, 640));
        setBackground(Color.white);
        rotateOnTime() ;
        
    }
    public void scaleUP() {
    	if(!checkNull())
    		scale(2);
    }
    public void scaleDown() {
    	if(!checkNull())
    		scale(0.5);
    }
    
    
    
    private void rotateOnTime() {
    	
        new Timer(17, (ActionEvent e) -> {
        	if(!checkNull()) {
        		if(!scaled) {
        			scale(100);
        	        rotateCubeZ(PI / 4);
        	        scaled = true;
        		}
	        	
	        	
	            repaint();
        	}
        }).start();
        
    }
 
    final void scale(double s) {
        for (double[] node : nodes) {
            node[0] *= s;
            node[1] *= s;
            node[2] *= s;
        }
    }
   public final void rotateCubeY(double angleX) {
	   if(!checkNull()) {
		   double sinX = sin(angleX);
	       double cosX = cos(angleX);
	        for (double[] node : nodes) {
	            double x = node[0];
	            double y = node[1];
	            double z = node[2];
	            node[1] = y * cosX - z * sinX;
	            node[2] = y * sinX + z * cosX;
	        }
	   }
    }
   public final void rotateCubeX(double angleX) {
	   if(!checkNull()) {
	        double sinX = sin(angleX);
	        double cosX = cos(angleX);
	 
	        for (double[] node : nodes) {
	            double x = node[0];
	            double y = node[1];
	            double z = node[2];
	 
	            node[0] = 	x * cosX  + z * sinX;
	            node[2] = -(x * sinX) + z * cosX ;
	        }
	   }
    }
   public final void rotateCubeZ(double angleX) {
	   if(!checkNull()) {
	        double sinX = sin(angleX);
	        double cosX = cos(angleX);
	 
	        for (double[] node : nodes) {
	            double x = node[0];
	            double y = node[1];
	            double z = node[2];
	 
	            node[0] = 	x * cosX  - y * sinX;
	            node[1] = 	x * sinX  + y * cosX ;
	        }
	   }
    }
    double [] getVector (double [] origin, double [] end) {
    	double [] vec = new double [origin.length];
    	for(int i = 0; i < origin.length; ++i) {
    		vec[i] = end[i] - origin[i];
    	}
    	return vec;
    }
    double [] getCross(double [] vec1, double [] vec2) {
    	double [] result = new double[vec1.length];
    	result [0] = vec1[1]*vec2[2]-vec1[2]*vec2[1];
    	result [1] = -(vec1[0]*vec2[2]-vec1[2]*vec2[0]);
    	result [2] = vec1[0]*vec2[1]-vec1[1]*vec2[0];
    	return result;
    }
    double getDotProduct(double[] vec1, double [] vec2) {
    	int sum = 0;
    	for(int i =0; i< vec1.length; ++i) {
    		sum += vec1[i]*vec2[i];
    	}
    	
    	return sum;
    }
    double[] subtractEye(double[] vec1) {
    	double[] result = new double[vec1.length];
    	for(int i = 0; i < vec1.length; ++i )
    		result[i] = vec1[i] - eye[i];
    	
		return result;
    	
    }
    double averageZdepth(double[][] vectors) {
    	double depth = 0;
    	for(double[] vector: vectors) {
    		depth += vector[2];
    	}
    	depth /= vectors.length;
    	
    	
		return depth;
    	
    }
    
    
    Vector<indexNum> findVisible() {
    	double [][][] triangleVectors 	= new double [triangles.length][3][3];
    	double [][]   triangleCross 	= new double [triangles.length][3];
    	double [] 	  triangleDot 		= new double [triangles.length];
    	Vector<indexNum> index 			= new Vector<indexNum>();
    	int k = 0;
    	
    	
    	
    	
    	
    	for( int[] triangle : triangles) {
    		//method to find the N*V>0 vector
    		double [] p1 = nodes[triangle[0]-1];
    		double [] p2 = nodes[triangle[1]-1];
    		double [] p3 = nodes[triangle[2]-1];
    		
			triangleVectors[k][0] 	= getVector(p1,p2);
			triangleVectors[k][1] 	= getVector(p2,p3);
			triangleVectors[k][2]	= getVector(p3,p1);
			
			triangleCross[k] 		= getCross(triangleVectors[k][0],triangleVectors[k][1]);
			triangleDot[k] 			= getDotProduct(triangleCross[k], eye);
			
			double[][] points = {p1,p2,p3};
			double depth = averageZdepth(points);
			
			index.add(new indexNum(k, triangleDot[k++],depth));
    	}
    	k = 0;
    	
		Collections.sort(index);
    	
    	
    	return index;
    }
    void drawCube(Graphics2D g) {
        g.translate(getWidth() / 2, getHeight() / 2);
        Vector<indexNum> visibleVec = findVisible();
        Vector<Polygon> allTriangles = new Vector<Polygon>();
        for (int i = 0; i < triangles.length; ++i) {
        	if(visibleVec.get(i).visible) {
        		int[] xPoints = new int[3];
        		xPoints[0] = (int) round(nodes[triangles[visibleVec.get(i).index][0]-1][0]);
        		xPoints[1] = (int) round(nodes[triangles[visibleVec.get(i).index][1]-1][0]);
        		xPoints[2] = (int) round(nodes[triangles[visibleVec.get(i).index][2]-1][0]);
        		
        		int[] yPoints = new int[3];
        		yPoints[0] = (int) round(nodes[triangles[visibleVec.get(i).index][0]-1][1]);
        		yPoints[1] = (int) round(nodes[triangles[visibleVec.get(i).index][1]-1][1]);
        		yPoints[2] = (int) round(nodes[triangles[visibleVec.get(i).index][2]-1][1]);
	            
        		
        		
        		g.setColor(Color.gray);
	            Polygon newShape = new Polygon(xPoints, yPoints, 3);
	            g.fill(newShape);
        		g.drawPolygon(newShape);
        		g.setColor(Color.black);
        		for(int j = 0; j < 3; j++) {
        			if(j+1<3)
        				g.drawLine(xPoints[j], yPoints[j], xPoints[j+1], yPoints[j+1]);
        			else
        				g.drawLine(xPoints[j], yPoints[j], xPoints[0], yPoints[0]);
        		}
	            allTriangles.add(newShape);
	            
        	//draw nodes
	            g.setColor(Color.black);
	            //this will  muck up bAD
	            for(int j = 0; j < 3; j++) {
        				g.fillOval(xPoints[j]-3, yPoints[j]-3, 6, 6);
        		}
        	
        	}
        }
                
    }
 
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        if(!checkNull()) {
        	drawCube(g);
        }
    }
}