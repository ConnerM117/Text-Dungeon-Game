package combat;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.EldritchBeast;
import mobs.EldritchHorror;
import mobs.EldritchSpawn;
import mobs.Mob;

public class EldritchEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "An intense warbling fills the air, and time and space distort around you. "
				+ "They are coming!";
	}

	@Override
	public String getVictoryDescription() {
		return "The distortions cease. Was it a dream? Was it real? Is that... your blood?"
				+ "\nOr is this place finally getting to you?";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		int mob1 = 1, mob2 = 1, mob3 = 1;
		int rand = GameScreen.generateRandom(1, 3);
		if (rand == 1) {
			for (int j = 0; j < GameScreen.generateRandom(2, 4); j++) { //fills the list with 2-4 eldritch spawn/beasts
				int rand2 = GameScreen.generateRandom(1, 3);
				if (rand2 <= 2) {
					mobs.add(new EldritchSpawn(mob1));
					mob1++;
				} else {
					mobs.add(new EldritchBeast(mob2));
					mob2++;
				}
			}
		} else { //rand is 3, put in 1 EldritchHorror
			mobs.add(new EldritchHorror(mob3));
			mob3++;
		}
		return mobs;
	}

}
