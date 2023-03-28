package adventurers.choices.rogue;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Scout extends Choice {

	public Scout() {
		name = "Scout";
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		String str = "";
		for (Mob m : mobs) {
			str += "\n" + m.removeAllBuffs();
			if (m.isHidden())
				str += "\n" + m.setHidden(false);
		}	
		GameScreen.setLogger(str);
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Remove all enemy buffs and bypass Hidden.";
	}

}
