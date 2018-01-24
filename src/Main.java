public class Main {

	static final int penalties_length = 4;

	public static void main(String[] args) {
		String fileName = args[0];
		Parser p = new Parser(fileName);
		try {
			Data d = p.loadData();
			System.out.println(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}