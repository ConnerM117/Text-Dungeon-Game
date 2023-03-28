package combat;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.FireDemon;
import mobs.HornedDemon;
import mobs.Imp;
import mobs.Mob;
import mobs.PitDemon;

public class DemonEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "A makeshift shrine is built in this room, covered in blasphemous runes. "
				+ "Demons drawn to its power turn on you and attack!";
	}

	@Override
	public String getVictoryDescription() {
		return "The demons roar as they're pulled from the mortal world back into the hells. "
				+ "With the threat gone, you're free to search the offerings in the shrine.";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		int mob1 = 1, mob2 = 1, mob3 = 1;
		int rand = GameScreen.generateRandom(1, 3);
		if (rand <= 2) {
			for (int i = 0; i < GameScreen.generateRandom(2, 4); i++) { //fills the list with 2-4 enemies
				int rand2 = GameScreen.generateRandom(1, 3);
				if (rand2 == 1) {
					mobs.add(new Imp(mob1));
					mob1++;
				} else if (rand2 == 2) {
					mobs.add(new HornedDemon(mob2));
					mob2++;
				} else { //rand2 == 3
					mobs.add(new FireDemon(mob3));
					mob3++;
				}
			}
		} else { //1 in 3 chance of fighting pit demons
			for (int i = 0; i < GameScreen.generateRandom(1, 2); i++) {
				mobs.add(new PitDemon(mob1));
				mob1++;
			}
		}
		return mobs;
	}

}
