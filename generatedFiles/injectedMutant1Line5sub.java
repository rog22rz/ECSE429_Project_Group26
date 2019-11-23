
public class injectedMutant1Line5sub {
	
	public static int addition(int a, int b) {
		return a-b;
	}
	
	public static int subtraction(int a, int b) {
		return a-b;
	}
	
	public static void main(String[] args) {
		
		int arg1 = Integer.parseInt(args[0]);
		int arg2 = Integer.parseInt(args[1]);
		
		System.out.println(addition(arg1, arg2) + subtraction(arg1*1, arg2/1));
		
	}
	
}
