package adventurers.choices.rogue;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class PreciseStrike extends Choice implements ChoiceTargetsMob {

	private int deathblowCritRateBuff;
	private int deathblowCritDamageBuff;
	private int rounds;
	
	public PreciseStrike() {
		name = "Precise Strike";
		deathblowCritRateBuff = 40;
		deathblowCritDamageBuff = 2;
		rounds = 0;
		requiresTarget = true;
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			//GameScreen.chooseTarget(mobs, this);
			
			return true;
		} else {
			GameScreen.setLogger("You don't have enough Stamina!");
		}
		return false;
	}

	@Override
	public String getDescription(Player player) {
		return "Spend 1 Stamina, automatically hit one target, ignoring Dodge.";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		String str = player.spendStamina(1);
		if (player.hasDeathblow()) {
			str += "\n" + player.buffCritRate(deathblowCritRateBuff, rounds);
			str += "\n" + player.buffCritDamage(deathblowCritDamageBuff, rounds);
		}
		
		if (player.hasAmbush() && GameScreen.getRoundCounter() == 1) //if they have Ambush and it's the first round
			str += player.attackTarget(target, true, true, false);
		else
			str += player.attackTarget(target, true, false, false);
		
		return str;
	}

}
