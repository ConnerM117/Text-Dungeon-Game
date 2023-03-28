package combat;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.Bear;
import mobs.GiantSnake;
import mobs.Mob;
import mobs.Wolf;

public class ForestEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "You hear something moving in the underbrush ahead, followed by a low growl. It "
				+ "moves to attack!";
	}

	@Override
	public String getVictoryDescription() {
		return "You move onward, but not before noticing that you weren't the first adventurer "
				+ "to reach the glade. Looks like they have some good stuff... and they don't need "
				+ "it any more!";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		int mob1 = 1, mob2 = 1, mob3 = 1;
		int rand = GameScreen.generateRandom(1, 3);
		if (rand == 1) {
			for (int j = 0; j < GameScreen.generateRandom(2, 5); j++) { //fills the list with 2-5 wolves
				mobs.add(new Wolf(mob1));
				mob1++;
			}
		} else if (rand == 2){ //put in 1-2 bears
			rand = GameScreen.generateRandom(1, 2);
			for (int i = 0; i < rand; i++) {
				mobs.add(new Bear(mob2));
				mob2++;
			}
		} else { //rand is 3, put in 1 giant snake
			mobs.add(new GiantSnake(mob3));
			mob3++;
		}
		return mobs;
	}

}
