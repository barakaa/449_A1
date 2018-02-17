import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Parser {

	private List<String> headers = new ArrayList<String>(Arrays.asList("Name:", "forced partial assignment:",
			"forbidden machine:", "too-near tasks:", "machine penalties:", "too-near penalities"));
	private List<Character> allowedCharacters = new ArrayList<Character>(
			Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'));
	private Data data;
	private int machPenaltyLineCount = 0;
	private final int machPenaltyLineLength = Main.dimension;
	boolean flag = true, f2 = false, f3 = false;
	

	private StringBuilder sb;

	public Parser(StringBuilder stringBuilder) {
		this.sb = stringBuilder;
	}

	public Data loadData(String fileName) {
		data = new Data();
		Scanner scan;
		try {
			scan = new Scanner(new File(fileName));
			String line, header = null;
			while (scan.hasNextLine()) {
				line = scan.nextLine().trim();
				if (headers.contains(line)) {
					header = line;
				} else {
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
		line = line.replaceAll("[\\(\\)]", "").trim();
		if (header == null) {
			errorWhileParsing();
			ret = false;
		} else if (header.equals(headers.get(0))) {
			if (line.length() > 0  && data.name == null) {
				data.name = line;
				ret = true;
			} else if (line.length() > 0 && data.name != null) {
				errorWhileParsing();
				ret = false;
			} else ret = true;
		} else if (header.equals(headers.get(1))) {
			if (line.length() == 3 || line.length() == 0) ret = parseForcedPartialAssignemnt(line);
			else errorWhileParsing();
		} else if (header.equals(headers.get(2))) {
			if (line.length() == 3 || line.length() == 0) ret = parseForbiddenMachine(line);
			else errorWhileParsing();
		} else if (header.equals(headers.get(3))) {
			if (line.length() == 3 || line.length() == 0) ret = parseTooNearTask(line);
			else errorWhileParsing();
		} else if (header.equals(headers.get(4))) {
			ret = parseMachinePenalties(line);
			if (f3) {
				errorWhileParsing();
				ret = false;
			}
		} else if (header.equals(headers.get(5))) {
			ret = parseTooNearPenalties(line);
		} else {
			errorWhileParsing();
		}
		return ret;
	}

	private void errorWhileParsing() {
		sb.append("Error while parsing input file\n");
		flag = true;
	}

	private boolean parseForcedPartialAssignemnt(String line) {
		boolean ret = true;
		Pair<Integer, Character> pair = getPair(line);
		if (line.length() > 0 && pair == null) {
			sb.append("invalid machine/task\n");
			ret = false;
		} else if (line.length() == 0 && f2) {
			return true;
		} else {
			if (data.forcedPartialAssignemnt.contains(pair) || data.hasSimilarForcedPartialAssignemnt(pair)) {
				sb.append("partial assignment error\n");
				ret = false;
			} else if (!data.forcedPartialAssignemnt.contains(pair) && pair != null) {
				data.forcedPartialAssignemnt.add(pair);
			}
			f2 = true;
		}
		return ret;
	}

	private boolean parseForbiddenMachine(String line) {
		boolean ret = true;
		Pair<Integer, Character> pair = getPair(line);
		if (line.length() > 0 && pair == null) {
			sb.append("invalid machine/task\n");
			ret = false;
		} else {
			if (!data.forbiddenMachine.contains(pair) && pair != null)
				data.forbiddenMachine.add(pair);
		}
		return ret;
	}

	private boolean parseTooNearTask(String line) {
		boolean ret = true;
		Pair<Integer, Integer> pair = getIntCharPair(line);
		if (line.length() > 0 && pair == null) {
			sb.append("invalid machine/task\n");
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
		boolean ret = true, lineLength = false, f = false;
		int length = 0;
		if (line.isEmpty() && machPenaltyLineCount != machPenaltyLineLength) {
			ret = false;
			flag = false;
		} else if (machPenaltyLineCount < machPenaltyLineLength) {
			try {
				String[] splitLine = line.split(" ");
				length = splitLine.length;
				if (length != machPenaltyLineLength) {
					lineLength = true;
					ret = false;
					flag = false;
					throw new Exception();
				}
				int[] machinePenaltyVals = new int[machPenaltyLineLength];
				for (int i = 0; i < machPenaltyLineLength; i++) {
					machinePenaltyVals[i] = Integer.parseInt(splitLine[i]);
				}
				data.setPenaltyLine(machPenaltyLineCount, machinePenaltyVals);
				machPenaltyLineCount++;
				f = true;
			} catch (Exception e) {
				if (!lineLength) {
					ret = false;
					sb.append("invalid penalty\n");
					flag = true;
				}
			}
		} else if (machPenaltyLineCount == machPenaltyLineLength && line.split(" ").length == 8) {
			ret = false;
			flag = false;
		} else if (!line.isEmpty()) {
			ret = false;
		}
		if (line.split(" ").length != 8 && machPenaltyLineCount == machPenaltyLineLength && !line.isEmpty() && !f) {
			f3 = true;
			ret = false;
		}
		if (!ret && !flag) {
			sb.append("machine penalty error\n");
			flag = true;
		}
		return ret;
	}

	private boolean parseTooNearPenalties(String line) {
		String[] lineData = line.split(",");
		if (line.length() == 0) return true;
		if (lineData.length != 3) return false;
		int task1 = lineData[0].charAt(0) - 64;
		int task2 = lineData[1].charAt(0) - 64;
		if (task1 < 0 || task1 > machPenaltyLineLength || task2 < 0 || task2 > machPenaltyLineLength) {
			sb.append("invalid task\n");
			return false;
		} else {
			Triple oldTriple = data.hasTooNearPenalty(task1, task2);
			int penalty;
			try {
				penalty = Integer.parseInt(lineData[2]);
			} catch (NumberFormatException e) {
				flag = false;
				sb.append("invalid penalty\n");
				return false;
			}
			Triple newTriple = new Triple(task1, task2, penalty);
			if (oldTriple != null) {
				if (!newTriple.equals(oldTriple)) {
					data.tooNearPenalties.remove(oldTriple);
					data.tooNearPenalties.add(newTriple);
				}
			} else {
				data.tooNearPenalties.add(newTriple);
			}
			return true;
		}
	}

	private Pair<Integer, Character> getPair(String line) {
		Pair<Integer, Character> ret = null;
		String[] lineData = line.split(",");
		if (lineData.length == 2) {
			int first;
			try {
				first = Integer.parseInt(lineData[0]);
				char second = lineData[1].charAt(0);
				if ((first < 1 || first > 8) || (!allowedCharacters.contains(second)))
					ret = null;
				else
					ret = new Pair<Integer, Character>(first, second);
			} catch (NumberFormatException e) {
				ret = null;
			}
		}
		return ret;
	}

	private Pair<Integer, Integer> getIntCharPair(String line) {
		Pair<Integer, Integer> ret = null;
		String[] lineData = line.split(",");
		if (lineData.length == 2) {
			int first = lineData[0].charAt(0) - 64;
			int second = lineData[1].charAt(0) - 64;
			if ((first < 1 || first > 8) || (second < 1 || second > 8))
				ret = null;
			else
				ret = new Pair<Integer, Integer>(first, second);
		}
		return ret;
	}
}