
public class Main {

	public static void main(String[] args) {
		Agency a = new Agency("agents.txt","vehicles.txt");
		a.openOperation(4, "stealing");
		a.endOperation("stealing");

	}

}
