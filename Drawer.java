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
	
	/* Variables */
	
	private static List<Integer> digitList = new ArrayList<Integer>();
	private static ArrayList <Color> colorsList = new ArrayList<Color>();	
		
	private static int imageDimension = 1000;
	private static String fileNameDigits = "pi.txt";
	private static String fileNameColors = "colors.csv";	
	private static String outputFileName = null;
	
	/* Main */
	
	public static void main(String[] args) {
			
		if(ValidArguments(args)){
			colorsList = LoadColorConfig();
			digitList = LoadDigitsFromFile();
			
			if(ValidDigits(digitList))
				CreateImage(digitList, colorsList);
		}
		else
			System.out.println("Invalid Arguments");
	}
	
	/* Argument Methods */
	
	private static boolean ValidArguments(String[] args){
		
		if(args.length == 1){
			if(args[0].toUpperCase().equals("-HELP")){
				System.out.println("e.g -dimension 500 -filein pi.txt -fileout myfilename");
				return false;
			}
		}
		
		int dimPos = -1;
		int outPos = -1;
		int inPos = -1;
		int count = -1;
		
		if(args.length != 0){
			
			for(String arg : args){
				
				count++;
				
				if(arg.toUpperCase().equals("-dimension"))
					dimPos = count + 1;
				
				if(arg.toUpperCase().equals("-fileout"))
					outPos = count + 1;
				
				if(arg.toUpperCase().equals("-filein"))
					inPos = count + 1;
				
				if(outPos == count){
					outputFileName = arg;
					System.out.println("Output File name set to " + outputFileName + ".png");
				}
				
				if(inPos == count){					
					if(arg.equals("pi.txt") == false){
						fileNameDigits = arg;
					}
				}
				
				if(dimPos == count){
					
					int dim = Integer.parseInt(arg);
					
					if(ValidDimensions(dim)){
						imageDimension = dim;
						System.out.println("Image Dimensions set to " + imageDimension + "px");
					}									
				}				
			}
			
			if(outPos != count && outPos != -1)
				System.out.println("Invalid argument for fileout");
			if(inPos != count && inPos != -1)	
				System.out.println("Invalid argument for filein");
			if(dimPos != count && dimPos != -1)
				System.out.println("Invalid argument for dimension");
			
		}
		
		return true;
	}
	
	private static boolean ValidDimensions(int dim){
		
		if(dim % 100 == 0)
			return true;
					
		System.out.println("Image Dimension must be a multiple of 100");
		
		return false;
	}

	private static boolean ValidDigits(List<Integer> digitList){
		
		if(digitList.size() < imageDimension){
			System.out.println("Number of digits is too small to fit image.");
			System.out.println("Digits In " + digitList.size() + " Dimension " + imageDimension + "px.");
			return false;
		}
		
		if(digitList.size() == 0){
			System.out.println("No Digits to use for the image. Check if your file isn't empty.");
			return false;
		}
		if(digitList.size() % 100 != 0){
			System.out.println("Amount of Digits is not a modulus of 100 " + digitList.size());
			return false;
		}
		
		return true;
	}
	
	/* Methods */
	
	private static ArrayList<Color> LoadColorConfig(){
		
		ArrayList <Color> colors = new ArrayList<Color>();	
        String line = "";
        String csvSymbol = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileNameColors))){

            while((line = br.readLine()) != null){
                String[] colorVars  = line.split(csvSymbol);
                Color col = new Color(Integer.parseInt(colorVars[0]), Integer.parseInt(colorVars[1]), Integer.parseInt(colorVars[2]));
                colors.add(col);
            }            

    		colors.add(new Color(0, 0, 0)); 

        }catch(IOException e){ 
        	return DefaultColors();
        }
        
        if(colors.size() != 11)
        	return DefaultColors();
				
		return colors;
	}
	
	private static ArrayList<Color> DefaultColors(){
				
		ArrayList <Color> colors = new ArrayList<Color>();
		
		colors = new ArrayList<Color>();        	
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
		
		return colors;
	}
	
	private static List<Integer> LoadDigitsFromFile(){
		
		List<Integer> piDigits = new ArrayList<Integer>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileNameDigits))) {
		    
			String line;
		    int x = 0;
		    
		    while ((line = br.readLine()) != null) {
		       for(Character c : line.toCharArray()){		    	   
		    	   x = Character.getNumericValue(c);
		    	   
		    	   if(x < 0 || x > 9)
		    		   x = 10;
		    	   
		    	   piDigits.add(x);
		       }
		    }
		    
		}catch (FileNotFoundException e) {
			System.out.println("File Not Found " + fileNameDigits);
		}catch (IOException e) {
			System.out.println("Error Reading File " + fileNameDigits);;
		}
				
		return piDigits;		
	}
	
	private static void CreateImage(List<Integer> digitList, List<Color> colorsList){
					
		BufferedImage img = new BufferedImage(imageDimension, imageDimension, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graph = img.createGraphics();
		
		int count = 0;
		int hei = 10;
		int wid = 10;
		int size = 10;
		
		graph.setColor(Color.BLACK);
		graph.fillRect(0, 0, imageDimension, imageDimension);
		
		for (int x = 0; x < (imageDimension / size); x++) {
			
		    for (int y = 0; y < (imageDimension / size); y++) {
		    	
		    	int digit = digitList.get(count);
		    	
		    	if(digit < 0 || digit > 9)
		    		digit = 10;
		    	
		    	Color color = colorsList.get(digit);		    	
		    	graph.setColor(color);		        
		    	graph.fillOval(wid - size, hei - size, size, size); 
	    	
		    	wid += 10;
		    	++count;	  		    	
		    }
		    
	    	wid = 10;
	    	hei += 10;	
		}
		
		graph.dispose();
		
		if(outputFileName == null){
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			outputFileName = timeStamp + ".png";
		}
		else
			outputFileName += ".png";
				
		File outputfile = new File(outputFileName);
		
		try{
			ImageIO.write(img,"png",outputfile);
		}catch(IOException e){}
		
	}
	
}// Drawer