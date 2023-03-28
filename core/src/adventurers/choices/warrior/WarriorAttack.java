package adventurers.choices.warrior;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class WarriorAttack extends Choice implements ChoiceTargetsMob {
	
	private List<Mob> mobChoices;
	
	public WarriorAttack() {
		name = "Attack";
		requiresTarget = true;
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		mobChoices = mobs;
		//GameScreen.chooseTarget(mobs, this);
		
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Attack one target, dealing " + player.getCurrentDamage() + " damage";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		String str = player.attackTarget(target, false, false, true);
		
		//if player has Cleave, the target was defeated, and there's another target
		if (player.hasCleave() && target.getCurrentHitPoints() <= 0 && mobChoices.size() > 1) { 
			str += "\n" + player.getName() + " Cleaves through their foe and strikes another!";
			Mob target2 = null;
			
			do {
				target2 = mobChoices.get(GameScreen.generateRandom(0, mobChoices.size()-1));
			} while (target == target2);
			
			str += "\n" + player.attackTarget(target2, false, false, true);
		}
		
		return str;
	}

}
