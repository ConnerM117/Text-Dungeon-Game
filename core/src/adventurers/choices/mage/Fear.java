package adventurers.choices.mage;

import java.util.List;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class Fear extends Choice implements ChoiceTargetsMob {

	private int accuracyDebuff;
	private int critRateDebuff;
	private int mindDebuff;
	private int rounds;

	public Fear() {
		name = "Fear";
		accuracyDebuff = 30;
		critRateDebuff = 20;
		mindDebuff = 20;
		rounds = 4;
		requiresTarget = true;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		//GameScreen.chooseTarget(mobs, this);
		
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "One target takes -" + accuracyDebuff + " Accuracy, -" + critRateDebuff + " Crit Rate, and -" + mindDebuff + " Mind.";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		if (!target.notice()) {
			String str = target.getName() + " is overwhelmed with fear!";
			str += "\n" + target.debuffAccuracy(accuracyDebuff, rounds);
			str += "\n" + target.debuffCritRate(critRateDebuff, rounds);
			str += "\n" + target.debuffMind(mindDebuff, rounds);
			return str;
		} else {
			return target.getName() + " manages to resist the fear!";
		}
	}

}
