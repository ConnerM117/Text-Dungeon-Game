package adventurers.choices.rogue;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class ApplyPoison extends Choice {

	private int damage;
	private int weaponDuration;
	
	public ApplyPoison() {
		name = "Apply Poison";
		damage = 1;
		weaponDuration = 5;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		GameScreen.setLogger(player.poisonWeapon(damage, weaponDuration, false));
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Poison your weapons for " + weaponDuration + " rounds. On hit, the poison deals "
				+ damage + " damage per round.";
	}

}
