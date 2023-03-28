package adventurers.choices.rogue;

import java.util.List;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class WoundingStrike extends Choice implements ChoiceTargetsMob {

	private int woundDamageBuff;

	public WoundingStrike() {
		name = "Wounding Strike";
		woundDamageBuff = 1;
		requiresTarget = true;
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		// GameScreen.chooseTarget(mobs, this);

		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Attack one target with 100% chance of Wounding and +" + woundDamageBuff + " Wound Damage.";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		return player.buffWoundRate(100 - player.getWoundRate(), 0) + "\n" + player.buffWoundDamage(woundDamageBuff, 0)
				+ player.attackTarget(target, false, false, true);
	}

}
