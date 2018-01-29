import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Parser {

	private List<String> headers = new ArrayList<String>(Arrays.asList("Name:", "forced partial assignment:",
			"forbidden machine:", "too-near tasks:", "machine penalties:", "too-near penalities"));
	private List<Character> allowedCharacters = new ArrayList<Character>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G'));
	private Data data;
	private String fileName;
	private int machPenaltyLineCount = 0;
	private final int machPenaltyLineLength = 8;

	public Parser(String fileName) {
		this.fileName = fileName;
	}

	public Data loadData() throws FileNotFoundException {
		data = new Data();
		Scanner scan = new Scanner(new File(fileName));
		String line, header = null;
		while (scan.hasNextLine()) {
			line = scan.nextLine().trim();
			if (headers.contains(line))
				header = line;
			else {
				if (!parseLine(line, header)) {
					data = null;
					break;
				}
			}
		}
		scan.close();
		return data;
	}

	private boolean parseLine(String line, String header) {
		boolean ret = false;
		line = line.replaceAll("[\\(\\)]", "");
		if (header.equals(headers.get(0))) {
			if (line.length() > 0) {
				data.name = line;
			}
			ret = true;
		} else if (header.equals(headers.get(1))) {
			ret = parseForcedPartialAssignemnt(line);
		} else if (header.equals(headers.get(2))) {
			ret = parseForbiddenMachine(line);
		} else if (header.equals(headers.get(3))) {
			ret = parseTooNearTask(line);
		} else if (header.equals(headers.get(4))) {
			ret = parseMachinePenalties(line);
		} else if (header.equals(headers.get(5))) {
			ret = parseTooNearPenalties(line);
		}
		return ret;
	}

	private boolean parseForcedPartialAssignemnt(String line) {
		boolean ret = true;
		Pair<Integer, Character> pair = getPair(line);
		if (line.length() > 0 && pair == null) {
			System.out.println("invalid machine/task");
			ret = false;
		} else {
			if (!data.forcedPartialAssignemnt.contains(pair))
				data.forcedPartialAssignemnt.add(pair);
		}
		return ret;
	}

	private boolean parseForbiddenMachine(String line) {
		boolean ret = true;
		Pair<Integer, Character> pair = getPair(line);
		if (line.length() > 0 && pair == null) {
			System.out.println("invalid machine/task");
			ret = false;
		} else {
			if (!data.forbiddenMachine.contains(pair))
				data.forbiddenMachine.add(pair);
		}
		return ret;
	}

	// TODO: check with Si Zhang that (1,2) and (2,1) in the same file is valid/invalid
	private boolean parseTooNearTask(String line) {
		boolean ret = true;
		Pair<Integer, Integer> pair = getIntPair(line);
		if (line.length() > 0 && pair == null) {
			System.out.println("invalid machine/task");
			ret = false;
		} else {
			if (pair != null) {
				Pair<Integer, Integer> reversedPair = new Pair<Integer, Integer>(pair.second, pair.first);
				if (!data.tooNearTask.contains(pair) && !data.tooNearTask.contains(reversedPair))
					data.tooNearTask.add(pair);
			}
		}
		return ret;
	}

	private boolean parseMachinePenalties(String line) {
		boolean ret = true;
		if (machPenaltyLineCount < machPenaltyLineLength) {
			try {
				String[] splitLine = line.split(" ");
				if (splitLine.length != machPenaltyLineLength) throw new Exception();
				int[] machinePenaltyVals = new int[machPenaltyLineLength];
				for (int i = 0; i < machPenaltyLineLength; i++) {
					machinePenaltyVals[i] = Integer.parseInt(splitLine[i]);
				}
				data.setPenaltyLine(machPenaltyLineCount, machinePenaltyVals);
				machPenaltyLineCount++;
			} catch (Exception e) {
				ret = false;
			}
		} else if (!line.isEmpty()) {
			ret = false;
		} else if (line.isEmpty() && machPenaltyLineCount != machPenaltyLineLength) {
			ret = false;
		}
		if (!ret) System.out.println("machine penalty error");
		return ret;
	}

	private boolean parseTooNearPenalties(String line) {
		boolean ret = true;
		String[] lineData = line.split(",");
		char task1 = lineData[0].charAt(0);
		char task2 = lineData[1].charAt(0);
		if (!allowedCharacters.contains(task1) || !allowedCharacters.contains(task2)) {
			System.out.println("invalid task");
			ret = false;
		}
		int penalty = Integer.parseInt(lineData[2]);
		data.tooNearPenalties.add(new Triple(task1, task2, penalty));
		return ret;
	}

	private Pair<Integer, Character> getPair(String line) {
		Pair<Integer, Character> ret = null;
		String[] lineData = line.split(",");
		if (lineData.length == 2) {
			int first = Integer.parseInt(lineData[0]);
			char second = lineData[1].charAt(0);
			if ((first < 1 || first > 8) || (!allowedCharacters.contains(second)))
				ret = null;
			else
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
			if ((first < 1 || first > 8) || (second < 1 || second > 8))
				ret = null;
			else
				ret = new Pair<Integer, Integer>(first, second);
		}
		return ret;
	}
}