import java.util.ArrayList;

public class Mutant {
	
	public int lineOfCode;
	public int index;
	public int[] outputIndexes;
	public Character originalArithmetic;
	public Character[] insertedMutant;
	public boolean[] killed = new boolean[3];
	public ArrayList<Input>[] vectors = new ArrayList[3];

	protected Mutant(int lineOfCode, Character originalArithmetic, Character[] insertedMutant, int index) {
		this.lineOfCode = lineOfCode;
		this.index = index;
		this.originalArithmetic = originalArithmetic;
		this.insertedMutant = insertedMutant;	
	}
	
}
