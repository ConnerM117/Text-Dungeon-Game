package adventurers.choices.warrior;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class ParryRiposte extends Choice {
	
	private int dodgeBuff;
	private int armorBuff;
	
	public ParryRiposte() {
		name = "Parry-Riposte";
		dodgeBuff = 20;
		armorBuff = 1;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			String str = player.spendStamina(1);
			str += "\n" + player.buffAgility(dodgeBuff, 1);
			if (player.hasBladeWall())
				str += "\n" + player.buffArmor(armorBuff, 1);
			GameScreen.setLogger(str);
			player.useRiposte();
			return true;
		} else {
			GameScreen.setLogger("You don't have enough Stamina!");
		}
		return false;
	}

	@Override
	public String getDescription(Player player) {
		String str = "Spend 1 Stamina, gain +" + dodgeBuff + " Dodge";
		if (player.hasBladeWall())
			str += " and +" + armorBuff + " Armor";
		if (player.hasBladeStorm()) {
			str += ", attack each foe to attack you this round.";
		} else {
			str += ", attack the first foe to attack you this round.";
		}
		return str;
	}

}
