package mobs.bosses;

import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.*;

public abstract class Boss extends Mob {
	
	public Boss() {
		super();
		baseStamina = 3;
		currentStamina = baseStamina;
	}
	
	public abstract String staminaActionTwo(Mob target, List<Mob> mobs);
	
	public String getCombatChoice(Mob mob, List<Mob> mobs) {
		//boss has 4 choices: attack, special move, or two stamina moves
		int choice = 0;
			
		if (getCurrentStamina() == 0) {
			choice = GameScreen.generateRandom(1, 3);
		} else { //it has Stamina, then allow for stamina move
			choice = GameScreen.generateRandom(1, 5);
		}
		
		if (choice <= 2) { //choice is 1 or 2
			return attackTarget(mob, false, false, true);
		} else if (choice == 3) {
			return specialAction(mob, mobs);
		} else if (choice == 4) { 
			spendStamina(1);
			return staminaAction(mob, mobs);
		} else { //choice is 5
			spendStamina(1);
			return staminaActionTwo(mob, mobs);
		}
	}

}
