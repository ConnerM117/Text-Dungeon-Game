package adventurers.archetypes;

import java.util.ArrayList;

import adventurers.choices.Choice;
import adventurers.choices.mage.Fireball;
import adventurers.perks.mage.CauterizePerk;
import adventurers.perks.mage.FeedTheFlames;
import adventurers.perks.mage.FlameCloakPerk;
import adventurers.perks.mage.FlameWard;
import adventurers.perks.mage.Incinerate;

public class Pyromancer extends Archetype {

	public Pyromancer() {
		name = "Pyromancer";
		description = "a mage with a penchant for fire";
		perkChoices = new ArrayList<>();
		perkChoices.add(new CauterizePerk());
		perkChoices.add(new FeedTheFlames());
		perkChoices.add(new FlameCloakPerk());
		perkChoices.add(new FlameWard());
		perkChoices.add(new Incinerate());
	}

	@Override
	public Choice getSignatureChoice() {
		return new Fireball();
	}

}
