package main;
//
//159.235, 2019 S2
//Startup code for Assignment 2
//
//
//By Mathew Lawrence 17354272
import static java.lang.Math.PI;

//Put in some package name if you like
//eg nz.ac.massey.graphics.wire

import java.awt.*;      
import java.awt.geom.*; 
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;   
import javax.swing.event.*;
import java.util.*;
import java.io.*;

//Here is a skeleton class that sets up a JFrame, some sliders and
//buttons

//You may add whatever features you see fit, in order to complete the
//assignment.

//Suggest you put your event listeners in the main program file
//- this would be the "controller" part of the MVC design

//You can also change the class names too
@SuppressWarnings("serial")
public class mainFrame extends JFrame 
 implements ActionListener, ChangeListener {
	
	//RotationMatrix
	DisplayPanel disp = null;
	//	scanner
	private VectorScanner scanner = null;
	// Menu items
	JMenuItem openItem, quitItem, helpItem;
	//Create a rotation scale, so that things rotate slower if requested or faster
	double RotationScale;
	// Buttons to change the size of the figure
	JButton biggerButton, smallerButton, angleUP, angleDown;
	//angle control doubles
	double angleX, angleY, angleZ;
	//boolean for angle direction
	boolean directionX = true, directionY = true, directionZ =true;
	// Sliders for rotation angles
	JSlider sliderXY, sliderXZ, sliderYZ;
	static final int SLIDER_MIN  = 0;
	static final int SLIDER_MAX  = 360;
	static final int SLIDER_INIT = 0;
	
	// Dimensions of JFrame
	static final int FRAME_WIDTH  = 1300;
	static final int FRAME_HEIGHT = 1000;
	static final double HALF_SIZE = 400.0;

	// Need this for the meu items and buttons
	 public void actionPerformed(ActionEvent event) {
	
		JComponent source = (JComponent)event.getSource();
		
		if (source == openItem) {
	         JFileChooser chooser = new JFileChooser("./");
	         int retVal = chooser.showOpenDialog(this);
	         if (retVal == JFileChooser.APPROVE_OPTION) {
	             File myFile = chooser.getSelectedFile();
	             try {
					scanner.setFile(myFile);
					disp.setNodes(scanner.getNodes());
					
					disp.setTriangles(scanner.getTriangles());
					repaint();
					revalidate();
				} catch (FileNotFoundException e) {
					System.out.println("File not found");
				} catch(Exception e) {
					System.out.println("Error in the copying data.");
					e.printStackTrace();
				}
	            
	             
	         }
	         chooser = null;
	         repaint();
	         
		}
	
		else if (source == quitItem) {
		    System.out.println("Quitting ...");
		    System.exit(0);
		}
	
		else if (source == helpItem) {
		    System.out.println("Help me!");
		}
		
		else if (source == biggerButton) {
			disp.scaleUP();
		}
		else if (source == smallerButton) {
			disp.scaleDown();
		} else if (source == angleUP) {
			RotationScale += 5;
		} else if (source == angleDown) {
			if(RotationScale-5 > 0)
				RotationScale -= 5;
		}
	
	 }
	private double setAngleDirection(double OriginAngle, double finalAngle){
		double result = OriginAngle;
		if(finalAngle<OriginAngle)
			result = -result;
		return result;
	}
	 // Need this for the sliders
	 public void stateChanged(ChangeEvent e) {
		
		JSlider source = (JSlider)e.getSource();
		double angle;
		
		if (source.getValueIsAdjusting()) {
		    angle = Math.toRadians((double)source.getValue())/RotationScale;
		    System.out.println("Angle is: "+angle);
		    if(e.getSource() == sliderXY) {
		    	//sliderXY, sliderXZ, sliderYZ
		    	disp.rotateCubeX(setAngleDirection(angle, angleX));
		    	disp.repaint();
		    	angleX = angle;
		    } else if (e.getSource() == sliderXZ) {
		    	disp.rotateCubeZ(setAngleDirection(angle, angleZ));
		    	disp.repaint();
		    	angleZ = angle;
		    } else if (e.getSource() == sliderYZ) {
	    		disp.rotateCubeY(setAngleDirection(angle, angleY));
		    	disp.repaint();
		    	angleY = angle;
		    }
		}
	 }
	
	 public void makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
	
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
	
		openItem = new JMenuItem("Open");
		quitItem = new JMenuItem("Quit");
		fileMenu.add(openItem);
		fileMenu.add(quitItem);
	
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
	
		helpItem = new JMenuItem("Help");
		helpMenu.add(helpItem);
	
		openItem.addActionListener(this);
		quitItem.addActionListener(this);
		helpItem.addActionListener(this);
	 }
	
	 JSlider makeSlider(JPanel panel, String heading) {
		JSlider slider = new JSlider(SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
	     slider.setBorder(BorderFactory.createTitledBorder(heading));
		slider.addChangeListener(this);
		slider.setMajorTickSpacing(90);
		slider.setMinorTickSpacing(30);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		panel.add(slider);
		return slider;
	 }
	 
	 public mainFrame() {
		
		super("159235 Assignment 2");
		angleX = 0.0;
		angleY = 0.0;
		angleZ = 0.0;
		RotationScale = 6;
		//we are working in 3D
		int dimens = 3;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		makeMenu();
		scanner = new VectorScanner(dimens);
		// Get a reference to the JFrames content pane to which
		// JPanels will be added
		Container content = this.getContentPane();
		content.setLayout(null);
	
		// Make a control panel for the sliders and buttons using a JPanel
		JPanel controlP = new JPanel();
		disp = new DisplayPanel(null, null);
		disp.setEye(null);
		controlP.setBounds(new Rectangle(0, 0, this.getWidth(), 100));
		
		disp.setSize(this.getWidth(), this.getHeight()-100);
		disp.setLocation(0, 100);
		content.add(controlP);
		content.add(disp);
		sliderXY = makeSlider(controlP, "XY Plane");
		sliderYZ = makeSlider(controlP, "YZ Plane");
		sliderXZ = makeSlider(controlP, "XZ Plane");
	
		biggerButton = new JButton("Bigger Picture");
		smallerButton = new JButton("Smaller Picture");
		
		
		
		angleUP = new JButton("Less Rotation");
		angleDown = new JButton("More Rotation");
		controlP.add(biggerButton);
		controlP.add(smallerButton);
		controlP.add(angleUP);
		controlP.add(angleDown);
		

		
		angleDown.addActionListener(this);
		angleUP.addActionListener(this);
		biggerButton.addActionListener(this);
		smallerButton.addActionListener(this);
	
		this.setVisible(true);
	
	 }
	
	
	
	 // Program entry point
	 public static void main(String[] args) {
		new mainFrame();
	 }

 
}
