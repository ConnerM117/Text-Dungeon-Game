package adventurers.choices.warrior;

import java.util.List;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class KnightDuel extends Choice implements ChoiceTargetsMob {

	public KnightDuel() {
		name = "Knight's Duel";
		requiresTarget = true;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		//GameScreen.chooseTarget(mobs, this);
		
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Challenge one target to a duel, and gain bonuses to Armor and Damage against them.";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		player.setKnightDuelTarget(target);
		return player.getName() + " challenges " + target.getName() + " to a knightly duel!";
	}

}
