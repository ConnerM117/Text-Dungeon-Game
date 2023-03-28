package adventurers.archetypes;

import java.util.ArrayList;

import adventurers.choices.Choice;
import adventurers.choices.rogue.Pickpocket;
import adventurers.perks.rogue.DeepPockets;
import adventurers.perks.rogue.ExpertThief;
import adventurers.perks.rogue.ItchyPalm;
import adventurers.perks.rogue.SkeletonKey;
import adventurers.perks.rogue.TrapExpert;

public class Burglar extends Archetype {

	public Burglar() {
		name = "Burglar";
		description = "a slippery thief with a knack for navigating the dungeon";
		perkChoices = new ArrayList<>();
		perkChoices.add(new DeepPockets());
		perkChoices.add(new ExpertThief());
		perkChoices.add(new ItchyPalm());
		perkChoices.add(new SkeletonKey());
		perkChoices.add(new TrapExpert());
	}

	@Override
	public Choice getSignatureChoice() {
		return new Pickpocket();
	}

}
