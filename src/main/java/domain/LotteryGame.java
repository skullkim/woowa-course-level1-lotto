package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import domain.generatestrategy.LotteryGenerateFamily;

public class LotteryGame {

	private final PurchaseAmount purchaseAmount;
	private final LotteryGenerateFamily lotteryGenerator;
	private Lotteries lotteries;
	private WinningLottery winningLottery;

	public LotteryGame(final PurchaseAmount purchaseAmount, final LotteryGenerateFamily lotteryGenerator) {
		this.purchaseAmount = purchaseAmount;
		this.lotteryGenerator = lotteryGenerator;
		createAutoLottery();
	}

	private void createAutoLottery() {
		final List<List<LotteryNumber>> lotteriesNumber = new ArrayList<>();
		for (int i = 0; i < purchaseAmount.getPurchasedLotteries(); i++) {
			lotteriesNumber.add(lotteryGenerator.getNumbers());
		}
		lotteries = new Lotteries(lotteriesNumber);
	}

	public void createWinningLottery(final List<Integer> winningNumbers, final Integer bonusBall) {
		final List<LotteryNumber> winningLotteryNumbers = winningNumbers.stream()
			.map(LotteryNumber::new)
			.collect(Collectors.toList());
		winningLottery = new WinningLottery(winningLotteryNumbers, new LotteryNumber(bonusBall));
	}

	public Map<Rank, Integer> makeWinner() {
		return lotteries.getTheNumberOfWinners(winningLottery);
	}

	public double makeRankingPercent(final Map<Rank, Integer> rankResult) {
		int earningAmount = 0;
		for (Rank rank : rankResult.keySet()) {
			earningAmount += rankResult.get(rank) * rank.getPrize();
		}
		return purchaseAmount.calculateEarningRate(earningAmount);
	}

	public List<Lottery> getLotteries() {
		return lotteries.getLotteries();
	}
}
