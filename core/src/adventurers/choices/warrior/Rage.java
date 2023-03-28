package adventurers.choices.warrior;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Rage extends Choice {
	
	private int critRateBonus;
	private int damageBonus;
	private int toughnessBonus;
	private int tempHP;
	private int rounds;
	
	public Rage() {
		critRateBonus = 20;
		damageBonus = 2;
		toughnessBonus = 30;
		rounds = 5;
		tempHP = 2;
		name = "Rage";
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			String str = player.spendStamina(1);
			str += "\n" + player.buffCritRate(critRateBonus, rounds);
			str += "\n" + player.buffDamage(damageBonus, rounds);
			if (player.hasToughRage()) {
				str += "\n" + player.buffToughness(toughnessBonus, rounds);
			}
			if (player.hasIronhide()) {
				str += "\n" + player.setTempHP(tempHP);
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
		StringBuilder str = new StringBuilder("Spend 1 Stamina, gain +" + critRateBonus + " Crit Rate, +" + damageBonus + " Damage");
		if (player.hasToughRage())
			str.append(", +" + toughnessBonus + " Toughness");
		if (player.hasIronhide())
			str.append(", and " + tempHP + " Temp HP");
		str.append(" for 5 rounds");
		return str.toString();
	}

}
