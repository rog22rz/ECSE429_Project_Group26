
public class MutantRunner implements Runnable {
	
	String fileName;
	String[] outputs = new String[Mutation.testSuit.size()];
	int index;
	
	public String[] getOutputs() {
		return this.outputs;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public MutantRunner(String fileName, int index) {
		this.fileName = fileName;
		this.index = index;
	}

	public void run() {
		
		try {
			Process pro1 = Runtime.getRuntime().exec("javac -cp src " + this.fileName + ".java");
			pro1.waitFor();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		for(int y = 0; y < Mutation.testSuit.size(); y++) {
			try {
				Process pro3 = Runtime.getRuntime().exec("cmd.exe /c cd generatedFiles && java " + this.fileName.replace("generatedFiles\\", "") + " " + 
						String.valueOf(Mutation.testSuit.get(y).arg1) + " " + String.valueOf(Mutation.testSuit.get(y).arg2));
				pro3.waitFor();
				
				String output = Mutation.streamToString(pro3.getInputStream());
				String error = Mutation.streamToString(pro3.getErrorStream()) ;
				
				if(error != null && !error.contentEquals("null")) {
					output = error;
				}
				
				outputs[y] = output;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
