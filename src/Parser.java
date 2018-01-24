import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;;

public class Parser {

	private List<String> headers = new ArrayList<String>(Arrays
			.asList("Name:", "forced partial assignment:", "forbidden machine:",
					"too-near tasks:", "machine penalties:", "too-near penalities"));

	private Data data;
	private String fileName;
	private int machPenaltyLineCount = 0;
	private final int machPenaltyLineLenght = Main.penalties_length;

	public Parser(String fileName) {
		this.fileName = fileName;
	}

	public Data loadData() throws FileNotFoundException {
		data = new Data();
		Scanner scan = new Scanner(new File(fileName));
		String line, header = null;
		while (scan.hasNextLine()) {
			line = scan.nextLine().trim();
			if (headers.contains(line)) header = line;
			else parseLine(line, header);
		}
		scan.close();
		return data;
	}

	private void parseLine(String line, String header) {
		line = line.replaceAll("[\\(\\)]", "");
		if (header.equals(headers.get(0))) {
			if (line.length() > 0) data.name = line;
		} else if (header.equals(headers.get(1))) {
			data.forcedPartialAssignemnt.add(getPair(line));
		} else if (header.equals(headers.get(2))) {
			data.forbiddenMachine.add(getPair(line));
		} else if (header.equals(headers.get(3))) {
			data.tooNearTask.add(getIntPair(line));
		} else if (header.equals(headers.get(4))) {
			parseMachinePenalties(line);
		} else if (header.equals(headers.get(5))) {
			parseTooNearPenalties(line);
		}
	}

	private void parseMachinePenalties(String line) {
		if (machPenaltyLineCount < machPenaltyLineLenght) {
			String[] splitLine = line.split(" ");
			int[] machinePenaltyVals = new int[machPenaltyLineLenght];
			for (int i = 0; i < machPenaltyLineLenght; i++) {
				machinePenaltyVals[i] = Integer.parseInt(splitLine[i]);
			}
			data.machinePenalties[machPenaltyLineCount] = machinePenaltyVals;
			machPenaltyLineCount++;
		}
	}

	private void parseTooNearPenalties(String line) {
		String[] lineData = line.split(",");
		char task1 = lineData[0].charAt(0);
		char task2 = lineData[1].charAt(0);
		int penalty = Integer.parseInt(lineData[2]);
		data.tooNearPenalties.add(new Triple(task1, task2, penalty));
	}

	private Pair<Integer, Character> getPair(String line) {
		Pair<Integer, Character> ret = null;
		String[] lineData = line.split(",");
		if (lineData.length == 2) {
			int first = Integer.parseInt(lineData[0]);
			char second = lineData[1].charAt(0);
			ret = new Pair<Integer, Character>(first, second);
		}
		return ret;
	}

	private Pair<Integer, Integer> getIntPair(String line) {
		Pair<Integer, Integer> ret = null;
		String[] lineData = line.split(",");
		if (lineData.length == 2) {
			int first = Integer.parseInt(lineData[0]);
			int second = Integer.parseInt(lineData[1]);
			ret = new Pair<Integer, Integer>(first, second);
		}
		return ret;
	}
}