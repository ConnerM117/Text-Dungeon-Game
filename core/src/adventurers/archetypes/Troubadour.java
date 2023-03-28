package adventurers.archetypes;

import java.util.ArrayList;

import adventurers.choices.Choice;
import adventurers.choices.rogue.BonMot;
import adventurers.perks.rogue.BalladPerk;
import adventurers.perks.rogue.Bravado;
import adventurers.perks.rogue.FlourishPerk;
import adventurers.perks.rogue.Footwork;
import adventurers.perks.rogue.NotTheFace;

public class Troubadour extends Archetype {

	public Troubadour() {
		name = "Troubadour";
		description = "a fast-talking musician who relies on buffs and debuffs";
		perkChoices = new ArrayList<>();
		perkChoices.add(new BalladPerk());
		perkChoices.add(new Bravado());
		perkChoices.add(new FlourishPerk());
		perkChoices.add(new Footwork());
		perkChoices.add(new NotTheFace());
	}
	
	@Override
	public Choice getSignatureChoice() {
		return new BonMot();
	}

}
