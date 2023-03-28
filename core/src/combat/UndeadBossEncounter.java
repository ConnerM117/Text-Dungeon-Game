package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mob;
import mobs.bosses.Lich;

public class UndeadBossEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "This room doesn't look like it belongs in the dungeon. Bookshelves hold a plethora"
				+ "of books, and complex alchemy equipment bubbles and steams. A man with a deathly"
				+ "pallor and skin stretched tight over his bones says, \"I was wondering when you"
				+ "were going to get here... you will make a fine servant!\"";
	}

	@Override
	public String getVictoryDescription() {
		return "The Lich's eyes roll back and he turns to dust, his cloak falling to the ground"
				+ "\nin an ashen cloud. You scour the place for something useful before heading onward"
				+ "\ninto the dark...";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new Lich());
		return mobs;
	}

}
