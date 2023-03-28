package combat;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.LizardmanChampion;
import mobs.LizardmanShaman;
import mobs.LizardmanWarrior;
import mobs.Mob;

public class SwampEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "In the swamp, something appears out of the mist that looks like a low hut. "
				+ "Lizardmen emerge from the water and attack!";
	}

	@Override
	public String getVictoryDescription() {
		return "The lizardmen defeated, you move onward through the swamp, making sure to"
				+ "\ncheck the hut for valuables.";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		int mob1 = 1, mob2 = 1, mob3 = 1;
		for (int i = 0; i < GameScreen.generateRandom(2, 4); i++) { //fills the list with 2-4 enemies
			int rand = GameScreen.generateRandom(1, 4);
			if (rand <= 2) {
				mobs.add(new LizardmanWarrior(mob1));
				mob1++;
			} else if (rand == 3) {
				mobs.add(new LizardmanShaman(mob2));
				mob2++;
			} else {
				mobs.add(new LizardmanChampion(mob3));
				mob3++;
			}
		}
		return mobs;
	}

}
