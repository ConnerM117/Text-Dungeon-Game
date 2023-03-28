package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class DarkBargain extends Choice {

	private int damage;
	private int debuff;
	private int rounds;
	
	public DarkBargain() {
		name = "Dark Bargain";
		damage = 3;
		debuff = 25;
		rounds = 4;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			String str = player.spendStamina(1);
			str += "\n" + player.getName() + " strikes a bargain with dark powers!";
			player.setHasDarkBargain(true);
			GameScreen.setLogger(str);
			return true;
		} else {
			String str = player.getName() + " strikes a bargain with dark powers!";
			int rand = GameScreen.generateRandom(1, 6);
			
			switch (rand) {
			case 1: str += player.takeDamage(damage); break;
			case 2: str += player.debuffAccuracy(debuff, rounds); break;
			case 3: str += player.debuffAgility(debuff, rounds); break;
			case 4: str += player.debuffToughness(debuff, rounds); break;
			case 5: str += player.debuffMind(debuff, rounds); break;
			case 6: str += player.debuffCritRate(debuff, rounds); break;
			}
			player.setHasDarkBargain(true);
			GameScreen.setLogger(str);
			return true;
		}
	}

	@Override
	public String getDescription(Player player) {
		return "Spend 1 Stamina or take a random debuff. Your next buff is doubled, or debuff affects all enemies.";
	}

}
