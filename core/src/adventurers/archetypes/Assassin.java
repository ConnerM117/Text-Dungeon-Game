package adventurers.archetypes;

import java.util.ArrayList;

import adventurers.choices.Choice;
import adventurers.choices.rogue.PreciseStrike;
import adventurers.perks.rogue.Ambush;
import adventurers.perks.rogue.Deathblow;
import adventurers.perks.rogue.Poisoner;
import adventurers.perks.rogue.Skirmisher;
import adventurers.perks.rogue.WoundingStrikePerk;

public class Assassin extends Archetype {

	public Assassin() {
		name = "Assassin";
		description = "a precise striker who relies on accuracy and big damage";
		perkChoices = new ArrayList<>();
		perkChoices.add(new Ambush());
		perkChoices.add(new Deathblow());
		perkChoices.add(new Poisoner());
		perkChoices.add(new Skirmisher());
		perkChoices.add(new WoundingStrikePerk());
	}
	
	@Override
	public Choice getSignatureChoice() {
		return new PreciseStrike();
	}

}
