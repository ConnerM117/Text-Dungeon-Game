package adventurers.choices.warrior;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class ToughAsNails extends Choice {

	private int baseHealValue;
	
	public ToughAsNails() {
		name = "Tough as Nails";
		baseHealValue = 1;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			String str = player.spendStamina(1);
			int healValue = baseHealValue + player.getLevel()/2;
			
			if (player.getCurrentHitPoints() + healValue > player.getMaxHitPoints()) {
				int shield = player.getCurrentHitPoints() + healValue - player.getMaxHitPoints();
				str += player.healDamage(healValue);
				str += player.setTempHP(shield);
			} else {
				str += player.healDamage(healValue);
			}
			GameScreen.setLogger(str);
			return true;
		} else {
			GameScreen.setLogger("You don't have enough Stamina!");
		}
		return false;
	}

	@Override
	public String getDescription(Player player) {
		return "Spend 1 Stamina, heal " + (baseHealValue + player.getLevel()/2) + " Hit Points. The excess converts into Shield.";
	}

}
