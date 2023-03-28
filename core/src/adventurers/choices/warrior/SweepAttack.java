package adventurers.choices.warrior;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class SweepAttack extends Choice implements ChoiceTargetsMob {
	
	private int accuracyPenalty;
	private List<Mob> mobChoices;
	
	public SweepAttack() {
		accuracyPenalty = 25;
		name = "Sweep Attack";
		requiresTarget = true;
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (mobs.size() > 1) { //if there's more than one valid target
			//GameScreen.chooseTarget(mobs, this);
			mobChoices = mobs;
			
			return true;
		} else { //there's only one target
			GameScreen.log("There's only one target!");
		}
		return false;
	}

	@Override
	public String getDescription(Player player) {
		return "Attack with -" + accuracyPenalty + " Accuracy, but targeting a second random enemy.";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		player.debuffAccuracy(accuracyPenalty, 0);
		Mob target2 = null;
		
		do {
			target2 = mobChoices.get(GameScreen.generateRandom(0, mobChoices.size()-1));
		} while (target == target2);
		
		return player.attackTarget(target, false, false, true) + "\n" + player.attackTarget(target2, false, false, true);
	}

}
