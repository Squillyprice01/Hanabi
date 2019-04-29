package util;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

public class GenerateGeneration {
	static int numberOfGenesInGenome = 8;
	static int numberOfIndividuals = 2;
	
	public static void main(String[] args) {
		Random random = new Random();
        PrintStream f = null;
     	try {
     		f = new PrintStream("./generation1.txt");
     		System.setOut(f);
     		for(int j = 0 ; j < numberOfIndividuals; j++) {
	     		for(int i = 0; i < numberOfGenesInGenome; i++) {
	     			if(i == numberOfGenesInGenome - 1) {
	     				System.out.println(random.nextDouble()+",0.0,0.0,0.0");
	     			} else {
	     				System.out.print(random.nextDouble() + ",");
	     			}
	     		}
     		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
