import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

public class Drawer{
	
	private static List<Integer> piDigits = new ArrayList<Integer>();
	private static ArrayList <Color> colors = new ArrayList<Color>();
	
	public static void main(String[] args) {
		
		colors.add(new Color(34,32,43));
		colors.add(new Color(125,105,98));				
		colors.add(new Color(249,174,116));		
		colors.add(new Color(211,206,216));		
		colors.add(new Color(103,83,122));		
		colors.add(new Color(239,247,224));		
		colors.add(new Color(150,212,191));		
		colors.add(new Color(41,69,67));		
		colors.add(new Color(61,48,32));
		colors.add(new Color(56,46,27));
		colors.add(new Color(0, 0, 0)); 
				
		piDigits = loadDigitsFromFile();
		
		createImage(piDigits, colors);		
	}
	
	private static List<Integer> loadDigitsFromFile(){
		
		List<Integer> piDigits = new ArrayList<Integer>();
		String fileName = "pi.txt";
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
		    
			String line;
		    int x = 0;
		    
		    while ((line = br.readLine()) != null) {
		       for(Character c : line.toCharArray()){		    	   
		    	   x = Character.getNumericValue(c);		    	   
		    	   piDigits.add(x);
		       }
		    }
		    
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
				
		return piDigits;		
	}
	
	private static void createImage(List<Integer> piDigits, List<Color> scheme){
		
		int imageWidth = 800;
		int imageHeight = 800;
		
		BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graph = img.createGraphics();
		
		int count = 0;
		int hei = 0;
		int wid = 0;
		int size = 10;
		
		graph.setColor(Color.BLACK);
		graph.fillRect(0, 0, imageHeight, imageHeight);
		
		for (int x = 0; x < (imageHeight / size); x++) {
			
		    for (int y = 0; y < (imageWidth / size); y++) {
		    	
		    	int digit = piDigits.get(count);
		    	
		    	if(digit < 0 || digit > 10)
		    		digit = 10;
		    	
		    	Color color = scheme.get(digit);		    	
		    	graph.setColor(color);		        
		    	graph.fillOval(wid - size, hei - size, size, size); 
	    	
		    	wid += 10;
		    	
		    	++count;	  		    	
		    }
		    
	    	wid = 0;
	    	hei += 10;	
		}
		
		graph.dispose();
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		File outputfile = new File(timeStamp + ".png");
		
		try{
			ImageIO.write(img, "png", outputfile);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
