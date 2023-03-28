package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class HexWeakness extends Choice implements ChoiceTargetsMob {

	private int toughnessDebuff;
	private int damageDebuff;
	private int rounds;
	
	public HexWeakness() {
		name = "Hex of Weakness";
		toughnessDebuff = 25;
		damageDebuff = 2;
		rounds = 2;
		requiresTarget = true;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		requiresTarget = true;
		if (player.hasDarkBargain()) {
			requiresTarget = false;
			String str = "The dark powers increase the power you channel...";
			for (Mob m : mobs) {
				str += "\n" + m.debuffToughness(toughnessDebuff, rounds);	
				str += "\n" + m.debuffDamage(damageDebuff, rounds);
				GameScreen.setLogger(str);
			}
			player.setHasDarkBargain(false);
		} 
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Debuff a target's Toughness by " + toughnessDebuff + " and Damage by " + damageDebuff + " for " + rounds + " rounds.";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		return target.debuffToughness(toughnessDebuff, rounds) + target.debuffDamage(damageDebuff, rounds);
	}

}
