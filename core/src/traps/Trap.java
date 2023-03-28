package traps;

import com.textdungeon.game.GameScreen;

import mobs.Player;

public abstract class Trap {
	
	protected boolean isArmed;
	protected boolean isDiscovered;
	protected int debuff;
	protected int debuffRounds;
	protected int damage;
	protected int attackAccuracy;
	protected String description;
	
	public abstract String triggerTrap(Player player);
	
	public boolean isArmed() {
		return isArmed;
	}
	
	public void disarmTrap() {
		isArmed = false;
	}
	
	// include PoisonNeedle, exclude Collapsing and Spike
	public static Trap getRandChestTrap() {
		switch (GameScreen.generateRandom(1, 7)) {
		case 1: return new AlarmTrap();
		case 2: return new ArrowTrap();
		case 3: return new AxeTrap();
		case 4: return new FlameTrap();
		case 5: return new GasTrap();
		case 6: return new PoisonDartTrap();
		case 7: return new PoisonNeedleTrap();
		default: return new ArrowTrap();
		}
	}
	
	// exclude PoisonNeedle
	public static Trap getRandRoomTrap() {
		switch (GameScreen.generateRandom(1, 8)) {
		case 1: return new AlarmTrap();
		case 2: return new ArrowTrap();
		case 3: return new AxeTrap();
		case 4: return new CollapsingTrap();
		case 5: return new FlameTrap();
		case 6: return new GasTrap();
		case 7: return new PoisonDartTrap();
		case 8: return new SpikeTrap();
		default: return new ArrowTrap();
		}
	}
	
	protected boolean trapDoesHit() {
		if (GameScreen.generateRandom(1, 100) <= attackAccuracy)
			return true;
		return false;
	}
	
	public String getDescription() {
		return "This trap looks like it will " + description + " when triggered.";
	}
	
	public boolean isDiscovered() {
		return isDiscovered;
	}
	
	public void setDiscovered(boolean isDiscovered) {
		this.isDiscovered = isDiscovered;
	}
}
