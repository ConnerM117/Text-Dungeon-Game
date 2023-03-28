package adventurers.choices.rogue;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Flourish extends Choice {

	private int agilityBuff;
	private int rounds;
	
	public Flourish() {
		name = "Flourish";
		agilityBuff = 20;
		rounds = 3;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		String str = player.buffAgility(agilityBuff, rounds);
		str += "\n" + player.removeAllDebuffs();
		GameScreen.setLogger(str);
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Gain +" + agilityBuff + " to Agility and remove all debuffs.";
	}

}
