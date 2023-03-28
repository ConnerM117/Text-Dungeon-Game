package adventurers.choices;

import java.util.List;

import mobs.*;

public abstract class Choice {
	
	protected boolean requiresTarget;
	protected String name;
	
	/*
	 * Runs the choice's combat effects and returns whether it was executed or not
	 * 
	 * @return {@code true} if the choice's effects are executed, false otherwise 
	 */
	public abstract boolean runChoice(Player player, List<Mob> mobs);

	public abstract String getDescription(Player player);
	
	public String getName() {
		return name;
	}
	
	public boolean requiresTarget() {
		return requiresTarget;
	}

}
