package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class FlameCloak extends Choice {

	private int flameCloakDuration;
	
	public FlameCloak() {
		name = "Flame Cloak";
		flameCloakDuration = 2;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		GameScreen.setLogger("Flames surround " + player.getName());
		player.setHasFlameCloak(true, flameCloakDuration);
		return false;
	}

	@Override
	public String getDescription(Player player) {
		return "Activate a Flame Cloak, which has a chance to Burn any foe to attack you for " + flameCloakDuration + " rounds.";
	}

}
