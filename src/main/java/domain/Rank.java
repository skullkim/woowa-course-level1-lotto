package domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Rank {
	FIRST(6, 2_000_000_000),
	SECOND(5, 30_000_000),
	THIRD(5, 1_500_000),
	FOURTH(4, 50_000),
	FIFTH(3, 5_000),
	NONE(1, 0);

	private static final int SECOND_THIRD_CORRECTED_BALLS = 5;

	private final int correctedBalls;
	private final long prize;

	Rank(final int correctedBalls, final int prize) {
		this.correctedBalls = correctedBalls;
		this.prize = prize;
	}

	public int getCorrectedBalls() {
		return correctedBalls;
	}

	public long getPrize() {
		return prize;
	}

	public static Rank getRank(final int winningCount, boolean hasBonusBall) {
		if (isThirdRank(winningCount, hasBonusBall)) {
			return Rank.THIRD;
		}

		return Arrays.stream(values())
			.filter(rank -> rank.getCorrectedBalls() == winningCount)
			.findFirst()
			.orElse(Rank.NONE);
	}

	private static boolean isThirdRank(final int winningCount, final boolean hasBonusBall) {
		return winningCount == SECOND_THIRD_CORRECTED_BALLS && !hasBonusBall;
	}

	public static List<Rank> getValuesExceptNoneRank() {
		return Arrays.stream(values())
			.filter(rank -> rank != NONE)
			.collect(Collectors.toList());
	}
}
