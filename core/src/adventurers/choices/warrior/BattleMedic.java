package adventurers.choices.warrior;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class BattleMedic extends Choice {
	
	private int healValue;
	
	public BattleMedic() {
		healValue = 3;
		name = "Battle Medic";
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		GameScreen.setLogger(player.healDamage(healValue));
		player.setWounded(false);
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Heal " + healValue + " Hit Points, and end Wounded";
	}

}
