package items.consumables;

import com.textdungeon.game.GameScreen;

import mobs.Player;

public class SketchyMushroom extends Consumable {
	
	private int buff;
	private int rounds;
	private int tempHP;
	
	public SketchyMushroom() {
		name = "Sketchy Mushroom";
		description = "Who knows what this will do when you eat it...";
		count = 1;
		cost = 3;
		isEquippable = false;
		type = Type.CONSUMABLE;
		buff = 15;
		rounds = 5;
		tempHP = 3;
		
		atlasIndex = 21;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " eats a " + name + "!\n";
		int rand = GameScreen.generateRandom(1, 6); //get a random buff
		if (rand == 1)
			str += player.buffAccuracy(buff, rounds);
		else if (rand == 2)
			str += player.buffCritRate(buff, rounds);
		else if (rand == 3)
			str += player.buffAgility(buff, rounds);
		else if (rand == 4)
			str += player.buffToughness(buff, rounds);
		else if (rand == 5)
			str += player.buffMind(buff, rounds);
		else
			str += player.setTempHP(tempHP);
		
		if (GameScreen.generateRandom(1, 3) == 1) // 1/3 chance to be poisoned
			str += player.setPoisoned(true, 1);
		
		player.removeFromInventory(this);
		return str;
	}

}
