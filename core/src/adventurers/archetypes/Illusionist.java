package adventurers.archetypes;

import java.util.ArrayList;

import adventurers.choices.Choice;
import adventurers.choices.mage.Phantasm;
import adventurers.perks.mage.DominatePerk;
import adventurers.perks.mage.DoppelgangerPerk;
import adventurers.perks.mage.FearPerk;
import adventurers.perks.mage.InvisibilityPerk;
import adventurers.perks.mage.UltimatePhantasm;

public class Illusionist extends Archetype {

	public Illusionist() {
		name = "Illusionist";
		description = "a master of illusions and the mind";
		perkChoices = new ArrayList<>();
		perkChoices.add(new DominatePerk());
		perkChoices.add(new DoppelgangerPerk());
		perkChoices.add(new FearPerk());
		perkChoices.add(new InvisibilityPerk());
		perkChoices.add(new UltimatePhantasm());
	}
	
	@Override
	public Choice getSignatureChoice() {
		return new Phantasm();
	}

}
