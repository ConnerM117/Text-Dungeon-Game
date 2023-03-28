package items;

import com.textdungeon.game.GameScreen;

public class Coins extends Item {
	
	public Coins(int min, int max) {
		name = "Coins";
		count = GameScreen.generateRandom(min, max);
		description = "" + count;
		cost = 0;
		isEquippable = false;
		isEquipped = false;
		type = Type.COINS;
	}

	@Override
	public String getStatistics() {
		return null;
	}
	
}
