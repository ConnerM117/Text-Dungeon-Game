package adventurers.choices.warrior;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Intimidate extends Choice {

	private int accuracyDebuff;
	private int dodgeDebuff;
	private int rounds;
	
	public Intimidate() {
		accuracyDebuff = 20;
		dodgeDebuff = 10;
		rounds = 3;
		name = "Intimidate";
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		for (Mob m : mobs) {
			String str = "";
			if (!m.notice()) { //mob can resist with Mind
				str += m.debuffAccuracy(accuracyDebuff, rounds);
				str += "\n" + m.debuffAgility(dodgeDebuff, rounds);
				GameScreen.setLogger(str);
			} else {
				GameScreen.setLogger(m.getName() + " isn't intimidated!");
			}
		}
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Each foe takes a -" + accuracyDebuff + " to Accuracy and -" + dodgeDebuff + " to Dodge, but can resist with Mind.";
	}

}
