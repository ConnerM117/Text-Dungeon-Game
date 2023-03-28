package core;

public class Condition {
	
	protected int buff;
	protected int remainingRounds;
	
	public Condition(int buff, int remainingRounds) {
		this.buff = buff;
		this.remainingRounds = remainingRounds;
	}
	
	public void decrementRounds() {
		remainingRounds--;
	}
	
	public int getBuff() {
		return buff;
	}
	
	public int getRemainingRounds() {
		return remainingRounds;
	}

}
