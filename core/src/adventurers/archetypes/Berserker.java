package adventurers.archetypes;

import java.util.ArrayList;

import adventurers.choices.Choice;
import adventurers.choices.warrior.Rage;
import adventurers.perks.warrior.Bloodlust;
import adventurers.perks.warrior.Ironhide;
import adventurers.perks.warrior.RecklessAttackPerk;
import adventurers.perks.warrior.ToughRage;
import adventurers.perks.warrior.WoundedFury;

public class Berserker extends Archetype {

	public Berserker() {
		name = "Berserker";
		description = "a warrior who utilizes rage in the midst of combat";
		perkChoices = new ArrayList<>();
		perkChoices.add(new Bloodlust());
		perkChoices.add(new Ironhide());
		perkChoices.add(new RecklessAttackPerk());
		perkChoices.add(new ToughRage());
		perkChoices.add(new WoundedFury());
	}

	@Override
	public Choice getSignatureChoice() {
		return new Rage();
	}
}
