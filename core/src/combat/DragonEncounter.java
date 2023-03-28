package combat;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.DragonWyrmling;
import mobs.Dragonkin;
import mobs.Mob;
import mobs.Wyvern;

public class DragonEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "Something hisses as you round the corner, and then lashes out "
				+ "when you come into sight!";
	}

	@Override
	public String getVictoryDescription() {
		return "The nest seems to be made partly of valuables taken from other "
				+ "would-be heroes. After taking what looks good, you head onward through the lair...";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		int mob1 = 1, mob2 = 1, mob3 = 1;
		int rand = GameScreen.generateRandom(1, 3);
		if (rand == 1) {
			for (int j = 0; j < GameScreen.generateRandom(2, 4); j++) { //fills the list with 2-4 wolves
				mobs.add(new Dragonkin(mob1));
				mob1++;
			}
		} else if (rand == 2){ //put in 1-2 bears
			rand = GameScreen.generateRandom(1, 2);
			for (int i = 0; i < rand; i++) {
				mobs.add(new DragonWyrmling(mob2));
				mob2++;
			}
		} else { //rand is 3, put in 1 giant snake
			mobs.add(new Wyvern(mob3));
			mob3++;
		}
		return mobs;
	}

}
