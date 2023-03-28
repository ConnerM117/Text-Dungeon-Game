package combat;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.Mob;
import mobs.Necromancer;
import mobs.Wight;
import mobs.Zombie;

public class UndeadEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "The moaning of the dead sounds from around the corner. As soon as "
				+ "you come into sight, they attack!";
	}

	@Override
	public String getVictoryDescription() {
		return "Some of the undead are made from past adventurers. It's grisly, but they might "
				+ "have something that could save your life later...";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		int mob1 = 1, mob2 = 1, mob3 = 1;
		for (int i = 0; i < GameScreen.generateRandom(2, 4); i++) { //fills the list with 2-4 enemies
			int rand = GameScreen.generateRandom(1, 3);
			if (rand == 1) {
				mobs.add(new Zombie(mob1));
				mob1++;
			} else if (rand == 2) {
				mobs.add(new Wight(mob2));
				mob2++;
			} else {
				mobs.add(new Necromancer(mob3));
				mob3++;
			}
		}
		return mobs;
	}

}
