
public class Parle_Algorithm {
	public int[] Activeparle;
	public int[] Sendend = { -1, -1, -1, -1 };
	public int[] Maxparle = { -1, -1, -1, -1 }; // current best solution
	public int[] Active_x;
	public int[] Active_y;
	public int[] send_Y = new int[Main.dimension];
	public int[] checkpath = { -1, -1, -1, -1 };
	public int[] validList = { -1, -1, -1, -1 };
	public int[] reset = { -1, -1, -1, -1 };
	public int parse = 0; // index of current max parle
	public int[] sumkey = new int[Main.dimension]; // stop position for Parle_kid
	public int[] numberkey = new int[Main.dimension];
	public int[][] tooNearPenalties;
	public int[][] tooNearTask;
	public int[][] MustCon;
	private boolean setFlag1 = false;

	public Parle_Algorithm(int[] list, int[] x, int[] y, int[] count, Data data) {
		Activeparle = list;
		Active_x = x;
		Active_y = y;
		numberkey = count;
		tooNearTask = data.tooNearTaskAsArray();
		tooNearPenalties = data.tooNearPenaltiesAsArray();
		if (data.forcedPartialAssignemntAsArray().length > 0) {
			MustCon = data.forcedPartialAssignemntAsArray();
		} else {
			setFlag1 = true;
		}
		getPosition();
	}

	public void Parle_kid(int stop, int start) {
		boolean flagset = false;
		for (int i = start; i < stop; i++) {
			flagset = false;
			if (Checkcontains(validList, Active_y[i]) == false && Checkcontains(checkpath, Active_x[i]) == false) {
				checkpath[parse] = Active_x[i];
				validList[parse] = Active_y[i];
				Maxparle[parse] = Activeparle[i];
				parse++;
				if (parse != Main.dimension) {
					Parle_kid(sumkey[parse], sumkey[parse - 1]);
				}
			}
			if (parse == Main.dimension) {
				parse = setindex(i);
				flagset = true; // if last index of level change previous level index as well
				if (i == sumkey[setindex(i)] - 1) {
					parse = parse - 1;
				}
				boolean checkcompare = search(validList, tooNearTask);
				boolean Must_Contain;
				if (setFlag1 == true) {
					Must_Contain = true;
				} else {
					Must_Contain = containSearch(checkpath, validList, MustCon);
				}
				if (Checkcontains(validList, -1) == false && Checkcontains(checkpath, -1) == false
						&& Checkcontains(Maxparle, -1) == false && checkcompare == false && Must_Contain == true) {
					if (Checkcontains(Sendend, -1)) {

						Sendend = build(Maxparle);
						send_Y = build(validList);
						Maxparle = reset;
					} else if (setValue(Sendend) > setValue(Maxparle) + softsearchNear(validList)) {
						Sendend = build(Maxparle);
						send_Y = build(validList);
						Maxparle = reset;
					}
				}
				reset(parse);
			}
			if (i == sumkey[setindex(i)] - 1 && flagset == false) {
				parse--;
				reset(parse);
			}
		}

	}

	public void reset(int val) {
		if (val <= -1) {
			val = 0;
		}
		for (; val < sumkey.length; val++) {
			checkpath[val] = -1;
			validList[val] = -1;
		}
	}

	public int setValue(int[] useA) {
		int max = 0;
		for (int i = 0; i < Main.dimension; ++i) {
			max = useA[i] + max;
		}
		return max;
	}

	public int setindex(int val) {
		int parseval = 0;
		for (int i = 0; i < sumkey.length; i++) {
			if (val < sumkey[i]) {
				parseval = i;
				val = val + sumkey[sumkey.length - 1];
			}
		}
		return parseval;
	}

	public void getPosition() {
		for (int i = 0; i < numberkey.length; i++) {
			if (i == 0) {
				sumkey[i] = numberkey[i];
				continue;
			}
			sumkey[i] = sumkey[i - 1] + numberkey[i];
		}
	}

	public boolean Checkcontains(int[] list, int target) {
		boolean send_r = false;
		for (int i = 0; i < list.length; i++) {
			if (list[i] == target) {
				send_r = true;
			}
		}
		return send_r;
	}

	public int[] build(int[] passed) {
		int[] newL = new int[passed.length];
		for (int i = 0; i < passed.length; i++) {
			newL[i] = passed[i];
		}
		return newL;
	}

	public boolean search(int[] checking, int[][] cons) {
		int stop = cons.length;
		boolean result = false;
		for (int i = 0; i < checking.length; i++) {
			for (int j = 0; j < stop; j++) {
				if (i == checking.length - 1) {
					if (checking[i] == tooNearTask[j][0] && checking[0] == tooNearTask[j][1]) {
						result = true;
					}
				} else if (checking[i] == tooNearTask[j][0] && checking[i + 1] == tooNearTask[j][1]) {
					result = true;
				}
			}
		}
		return result;
	}

	public boolean containSearch(int[] xchecking, int[] ychecking, int[][] cons) {
		int stop = cons.length;
		int count = 0;
		boolean result = false;
		for (int i = 0; i < xchecking.length; i++) {
			for (int j = 0; j < stop; j++) {
				if (xchecking[i] == cons[j][0] && ychecking[i] == cons[j][1]) {
					count += 1;
				}
			}
		}
		if (count == stop) {
			result = true;
		}
		return result;
	}

	public int softsearchNear(int[] checking) {
		int stop = tooNearPenalties.length;
		int result = 0;
		for (int i = 0; i < checking.length; i++) {
			for (int j = 0; j < stop; j++) {
				if (i == checking.length - 1) {
					continue;
				}
				if (checking[i] == tooNearTask[j][0] && checking[i + 1] == tooNearTask[j][1]) {
					result += tooNearPenalties[j][2];
				}
			}
		}
		return result;
	}
}
