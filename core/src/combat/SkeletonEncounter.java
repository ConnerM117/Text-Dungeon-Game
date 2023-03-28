package combat;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.Mob;
import mobs.SkeletonArcher;
import mobs.SkeletonGiant;
import mobs.SkeletonWarrior;

public class SkeletonEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "The clattering of bones echoes off the stone walls as you enter what looks like an old tomb. "
				+ "Skeletons rise from their graves and attack!";
	}

	@Override
	public String getVictoryDescription() {
		return "Having defeated the skeletons, you step over their fallen bones and head farther into the dark."
				+ "\nBut not before robbing their graves. They're dead, and besides, they attacked you.";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		int mob1 = 1, mob2 = 1, mob3 = 1;
		for (int i = 0; i < GameScreen.generateRandom(2, 4); i++) { //fills the list with 2-4 enemies
			int rand = GameScreen.generateRandom(1, 4);
			if (rand <= 2) {
				mobs.add(new SkeletonWarrior(mob1));
				mob1++;
			} else if (rand == 3) {
				mobs.add(new SkeletonArcher(mob2));
				mob2++;
			} else {
				mobs.add(new SkeletonGiant(mob3));
				mob3++;
			}
		}
		return mobs;
	}

}
