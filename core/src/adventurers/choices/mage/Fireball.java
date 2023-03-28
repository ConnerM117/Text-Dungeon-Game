package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Fireball extends Choice {

	private int incinerateDamage;
	private int tempHP;
	
	public Fireball() {
		name = "Fireball";
		incinerateDamage = 1;
		tempHP = 2;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			String str = player.spendStamina(1);
			str += "\n" + player.getName() + " throws an explosive fireball!";
			GameScreen.setLogger(str);
			boolean getTempHP = false;
			
			if (player.hasIncinerate())
				player.setBurningAttack(true, incinerateDamage);
			
			for (Mob m : mobs) {
				player.spellAttackTarget(m, false, false, true, false);
				if (player.hasFeedTheFlames()) {
					if (m.getCurrentHitPoints() <= 0)
						getTempHP = true;
				}	
			}
			
			if (getTempHP)
				GameScreen.setLogger(player.setTempHP(tempHP));
			
			if (player.hasIncinerate())
				player.resetBurningAttack();
			
			return true;
		} else {
			GameScreen.setLogger("You don't have enough Stamina!");
		}
		return false;
	}

	@Override
	public String getDescription(Player player) {
		return "Spend 1 Stamina to engulf all foes in a fireball.";
	}

}
