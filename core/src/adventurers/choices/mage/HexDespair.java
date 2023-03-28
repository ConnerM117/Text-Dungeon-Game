package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class HexDespair extends Choice implements ChoiceTargetsMob {

	private int debuff;
	private int rounds;
	
	public HexDespair() {
		name = "Hex of Despair";
		debuff = 20;
		rounds = 4;
		requiresTarget = true;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		requiresTarget = true;
		if (player.hasDarkBargain()) {
			requiresTarget = false;
			String str = "The dark powers increase the power you channel...";
			for (Mob m : mobs) {
				str += "\n" + m.debuffAccuracy(debuff, rounds);	
				str += "\n" + m.debuffMind(debuff, rounds);
			}
			player.setHasDarkBargain(false);
			GameScreen.setLogger(str);
		} 
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Debuff a target's Accuracy and Mind by " + debuff + " for " + rounds + " rounds.";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		return target.debuffAccuracy(debuff, rounds) + "\n" + target.debuffMind(debuff, rounds);
	}

}
