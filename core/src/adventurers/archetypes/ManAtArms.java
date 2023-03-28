package adventurers.archetypes;

import java.util.ArrayList;

import adventurers.choices.Choice;
import adventurers.choices.warrior.ParryRiposte;
import adventurers.perks.warrior.BladeStorm;
import adventurers.perks.warrior.BladeWall;
import adventurers.perks.warrior.CleavePerk;
import adventurers.perks.warrior.Feint;
import adventurers.perks.warrior.FollowUp;

public class ManAtArms extends Archetype {

	public ManAtArms() {
		name = "Man At Arms";
		description = "a versatile fighter with a mastery of all weapons";
		perkChoices = new ArrayList<>();
		perkChoices.add(new BladeStorm());
		perkChoices.add(new BladeWall());
		perkChoices.add(new CleavePerk());
		perkChoices.add(new Feint());
		perkChoices.add(new FollowUp());
	}
	@Override
	public Choice getSignatureChoice() {
		return new ParryRiposte();
	}

}
