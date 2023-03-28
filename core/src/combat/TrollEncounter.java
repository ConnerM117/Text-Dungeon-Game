package combat;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.Hobgoblin;
import mobs.Mob;
import mobs.Ogre;
import mobs.Troll;

public class TrollEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "A low grunting sounds from the back of the cavern, amid bed pallets made of "
				+ "bones and moss. Something rises from the ground and attacks!";
	}

	@Override
	public String getVictoryDescription() {
		return "Having defeated your opposition, you search their belongings for anything "
				+ "valuable. Then you continue onward through the caverns...";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		int mob1 = 1, mob2 = 1, mob3 = 1;
		int rand = 0;
		for (int i = 0; i < 2; i++) {
			rand = GameScreen.generateRandom(1, 2);
			if (rand == 1) {
				for (int j = 0; j < GameScreen.generateRandom(1, 4); j++) { //fills the list with 1-4 hobgoblins
					mobs.add(new Hobgoblin(mob1));
					mob1++;
				}
			} else { //rand is 2 put in one ogre or troll
				rand = GameScreen.generateRandom(1, 2);
				if (rand == 1) { // put in an ogre
					mobs.add(new Ogre(mob2));
					mob2++;
				}
				else { //rand is 2 put in a troll
					mobs.add(new Troll(mob3));
					mob3++;
				}
			}
		}
		return mobs;
	}

}
