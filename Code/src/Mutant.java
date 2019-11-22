
public class Mutant {
	
	public int lineOfCode;
	public int index;
	public Character originalArithmetic;
	public Character[] insertedMutant;

	protected Mutant(int lineOfCode, Character originalArithmetic, Character[] insertedMutant, int index) {
		this.lineOfCode = lineOfCode;
		this.index = index;
		this.originalArithmetic = originalArithmetic;
		this.insertedMutant = insertedMutant;	
	}
	
}
