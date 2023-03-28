package adventurers.archetypes;

import java.util.List;

import adventurers.choices.Choice;
import adventurers.perks.general.Perk;

public abstract class Archetype {
	
	protected String name;
	protected String description;
	protected List<Perk> perkChoices;
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<Perk> getPerkChoices() {
		return perkChoices;
	}
	
	public abstract Choice getSignatureChoice();

}
