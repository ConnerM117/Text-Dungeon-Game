package traps;

import com.textdungeon.game.GameScreen;

import mobs.Player;

public class GasTrap extends Trap {

	private int poisonDamage;
	
	public GasTrap() {
		isArmed = true;
		debuff = 15;
		debuffRounds = 5;
		poisonDamage = 1;
		description = "fill the area with gas";
	}

	@Override
	public String triggerTrap(Player player) {
		String str = ("Gas billows out toward you!\n");
		if (player.isTough()) {
			str += ("But you're tough enough to withstand it!");
		} else { //the player doesn't dodge
			int rand = GameScreen.generateRandom(1, 5); //get a random debuff
			switch (rand) {
			case 1: str += player.debuffAccuracy(debuff, debuffRounds); break;
			case 2: str += player.debuffAgility(debuff, debuffRounds); break;
			case 3: str += player.debuffToughness(debuff, debuffRounds); break;
			case 4: str += player.debuffMind(debuff, debuffRounds); break;
			case 5: str += player.setPoisoned(true, poisonDamage); break;
			default: str += player.setPoisoned(true, poisonDamage); break;
			}
		}
		return str;
	}

}
