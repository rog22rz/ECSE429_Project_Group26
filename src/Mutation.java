import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/*
 * To faciliate operations, the integer values 0,1,2,3 respectively represent
 * the arithmetic operations +,-,*,/ thorughout the program
 * 
 * This programs both read the SUT file to create a library of mutant and then
 * generate the respective files with the mutants injected into them
 * 
 * List of generated mutant injected files is under the folder "/generatedFiles"
 */

public class Mutation {

	private static Map<Integer,Character> arithMap = new HashMap<Integer,Character>();
	private static ArrayList<Mutant> mutants = new ArrayList<Mutant>();
	private static ArrayList<String> fileNames = new ArrayList<String>();
	private static ArrayList<String[]> mutantOutputs = new ArrayList<String[]>();
	private static ArrayList<Input> testSuit = new ArrayList<Input>();
	private static String[] faultFreeOutputs;
	private static int[] mutantCount = new int[4];  	
	private static int readIndex = 1;
	
	private static String inputFile = "SUT"; 

	public static void main(String[] args) {
		
		//Check for cmd input
		if(args.length > 0) {
			inputFile = args[0];
		}
		
		initMap();
		initTestSuit();
		
		readFile(inputFile + ".java");
		createList("mutant_librairy.txt");
		createMutantFiles(inputFile + ".java");
		
		try {
			getFaultFreeOutput();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			runAllMutantsFiles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < faultFreeOutputs.length; i++) {
			System.out.println("Expected: " + faultFreeOutputs[i]);
		}
		
		for(int i = 0; i < mutantOutputs.size(); i++) {
			for(int y = 0; y < testSuit.size(); y ++) {
				System.out.println("Mutant " + i + " test " + y + " " + mutantOutputs.get(i)[y]);
			}
		}
	}

	public static void initMap() {
		arithMap.put(0, '+');
		arithMap.put(1, '-');
		arithMap.put(2, '*');
		arithMap.put(3, '/');
	}
	
	public static void initTestSuit() {
		testSuit.add(new Input(26, 72));
		testSuit.add(new Input(1, 1));
		testSuit.add(new Input(1, 0));
		testSuit.add(new Input(0, 1));
		testSuit.add(new Input(0, 0));
		
		faultFreeOutputs = new String[testSuit.size()];
	}
	
	public static void readFile(String fileName) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String line;
			// Reads source code line by line until EOF
			while ((line = br.readLine()) != null) {
				readline(line);
				readIndex++;
			}
			
			readIndex = 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void readline(String line) {

		for (int i = 0; i < line.length(); i++) {

			int foundArithmetic = -1;
			
			//Check for arithmetic operations
			switch (line.charAt(i)) {
				case '+':
					foundArithmetic = 0;
					break;
				case '-':
					foundArithmetic = 1;
					break;
				case '*':
					foundArithmetic = 2;
					break;
				case '/':
					foundArithmetic = 3;
					break;
				default:
					continue;
			}
			
			//Create new mutant
			Mutant newMutant = new Mutant(readIndex, arithMap.get(foundArithmetic), generateMutants(foundArithmetic), i);
			mutants.add(newMutant);
		}
	}

	public static Character[] generateMutants(int original) {
		Character[] mutants = new Character[3];
		int counter = 0;
		
		//Add all operators except the original
		for(int i = 0; i < 4; i++) {
			if(i != original) {
				mutants[counter] = arithMap.get(i);
				mutantCount[i]++;
				counter++;
			}
		}
		return mutants;
	}
	
	public static void createList(String fileName) {
		File outputFile = new File(fileName);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
			
			//Write all mutants to the output file
			for (int i = 0; i < mutants.size(); i++) {
				writer.write(
					"Mutant #" + (i+1) + ":\n"
					+ "\tInserted at line: " + mutants.get(i).lineOfCode + "\n"
					+ "\tOriginal operation: " + mutants.get(i).originalArithmetic + "\n"
					+ "\tMutant operations: " + mutants.get(i).insertedMutant[0] + "," 
					+ mutants.get(i).insertedMutant[1] + "," + mutants.get(i).insertedMutant[2] + "\n\n"
				);
			}

			//Write mutants count to the output file
			writer.write(
				"------- Count of inserted mutants ----------"
				+ "\n'+': " + mutantCount[0]
				+ "\n'-': " + mutantCount[1]
				+ "\n'*': " + mutantCount[2]
				+ "\n'/': " + mutantCount[3]
			);
			
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void createMutantFiles(String inputFileName) {
		
		String baseName;
		File folder = new File("generatedFiles");
		folder.mkdir();
		int fileNumber = 1;
		
		for(int i = 0; i < mutants.size(); i++) {
			
			baseName = "generatedFiles\\injectedMutant";
			
			Mutant current = mutants.get(i);
			
			Character[] mutants = current.insertedMutant;
			
			for(int y = 0; y < mutants.length; y++) {
				
				//Format file name
				String outputFileName = baseName + String.valueOf(fileNumber) + "Line" + 
							String.valueOf(current.lineOfCode) + getStringOfArithmetic(mutants[y]);
				
				File outputFile = new File(outputFileName + ".java");
				
				//Read SUT file and write mutant files
				try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
						
						String line;
						while ((line = reader.readLine()) != null) {
							
							if (line.contains(inputFile)) {
								String newName = outputFileName.replace("generatedFiles\\", "");
								line = line.replace(inputFile, newName);
							}
							
							//Changes original arithmetic for mutant at proper line and index
							if(readIndex == current.lineOfCode) {
								line = line.substring(0,current.index) + mutants[y] + line.substring(current.index + 1);;
							}

							writer.write(line + "\n");
							readIndex++;
						}					
						readIndex = 1;
					}
					
					fileNames.add(outputFileName);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				fileNumber++;
			}		
		}
	}
	
	//Format generated files name
	public static String getStringOfArithmetic(Character operation) {
		
		switch (operation) {
			case '+':
				return "add";
			case '-':
				return "sub";
			case '*':
				return "mul";
			case '/':
				return "div";
			default:
				return "und";
		}	
	}
	
	public static void getFaultFreeOutput() throws Exception {
		
		Process pro1 = Runtime.getRuntime().exec("javac -cp src " + inputFile + ".java");
		pro1.waitFor();
		
		for(int y = 0; y < testSuit.size(); y++) {
			try {
				Process pro2 = Runtime.getRuntime().exec("cmd.exe /c java " + inputFile + " " + 
										String.valueOf(testSuit.get(y).arg1) + " " + String.valueOf(testSuit.get(y).arg2));
				pro2.waitFor();
				
				faultFreeOutputs[y] = streamToString(pro2.getInputStream());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	public static void runAllMutantsFiles() throws Exception {
		
		for(int i = 0; i < fileNames.size(); i++) {
			
			String[] outputs = new String[testSuit.size()]; 
			
			Process pro1 = Runtime.getRuntime().exec("javac -cp src " + fileNames.get(i) + ".java");
			pro1.waitFor();
			
			for(int y = 0; y < testSuit.size(); y++) {
				try {
					Process pro3 = Runtime.getRuntime().exec("cmd.exe /c cd generatedFiles && java " + fileNames.get(i).replace("generatedFiles\\", "") + " " + 
							String.valueOf(testSuit.get(y).arg1) + " " + String.valueOf(testSuit.get(y).arg2));
					pro3.waitFor();
					
					String output = streamToString(pro3.getInputStream());
					String error = streamToString(pro3.getErrorStream()) ;
					
					if(error != null && !error.contentEquals("null")) {
						output = error;
					}
					
					outputs[y] = output;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
			System.out.println("this worked");
			mutantOutputs.add(outputs);
		}		
	}
	
	public static String streamToString(InputStream in) {
		String line = null;
        BufferedReader buff = new BufferedReader(new InputStreamReader(in));
        try {
			line = buff.readLine();
		} catch (IOException e) {
			System.out.println("Error stream to string");
		}
        return line;
	}
}
