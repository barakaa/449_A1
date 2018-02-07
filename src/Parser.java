import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Parser {

	private List<String> headers = new ArrayList<String>(Arrays
			.asList("Name:", "forced partial assignment:", "forbidden machine:",
					"too-near tasks:", "machine penalties:", "too-near penalities"));
	private List<Character> allowedCharacters = new ArrayList<Character>(
			Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G'));
	private Data data;
	private int machPenaltyLineCount = 0;
	private final int machPenaltyLineLength = Main.dimension;

	public Data loadData(String fileName) {
		data = new Data();
		Scanner scan;
		try {
			scan = new Scanner(new File(fileName));
			String line, header = null;
			while (scan.hasNextLine()) {
				line = scan.nextLine().trim();
				if (headers.contains(line)) header = line;
				else {
					if (!parseLine(line, header)) {
						data = null;
						break;
					}
				}
			}
			scan.close();
		} catch (FileNotFoundException fnfe) {
			data = null;
		}
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
		} else {
			System.out.println("parser error");
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
			if (!data.forcedPartialAssignemnt.contains(pair) && pair != null)
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
			if (!data.forbiddenMachine.contains(pair) && pair != null) 
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
				Pair<Integer, Integer> reversedPair = new Pair<Integer, Integer>(
						pair.second, pair.first);
				if (!data.tooNearTask.contains(pair)
						&& !data.tooNearTask.contains(reversedPair))
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
		int task1 = lineData[0].charAt(0) - 48;
		int task2 = lineData[1].charAt(0) - 48;
		if (task1 < 0 || task1 > machPenaltyLineLength || task2 < 0
				|| task2 > machPenaltyLineLength) {
			System.out.println("invalid task");
			ret = false;
		} else {
			Triple oldTriple = data.hasTooNearPenalty(task1, task2);
			int penalty = Integer.parseInt(lineData[2]);
			Triple newTriple = new Triple(task1, task2, penalty);
			if (oldTriple != null) {
				if (!newTriple.equals(oldTriple)) {
					data.tooNearPenalties.remove(oldTriple);
					data.tooNearPenalties.add(newTriple);
				}
			} else {
				data.tooNearPenalties.add(newTriple);
			}
		}
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
			else ret = new Pair<Integer, Character>(first, second);
		}
		return ret;
	}

	private Pair<Integer, Integer> getIntPair(String line) {
		Pair<Integer, Integer> ret = null;
		String[] lineData = line.split(",");
		if (lineData.length == 2) {
			int first = Integer.parseInt(lineData[0]);
			int second = Integer.parseInt(lineData[1]);
			if ((first < 1 || first > 8) || (second < 1 || second > 8)) ret = null;
			else ret = new Pair<Integer, Integer>(first, second);
		}
		return ret;
	}
}
