package adventurers.archetypes;

import java.util.ArrayList;

import adventurers.choices.Choice;
import adventurers.choices.mage.DarkBargain;
import adventurers.perks.mage.HexAbyssPerk;
import adventurers.perks.mage.HexDespairPerk;
import adventurers.perks.mage.HexVulnerabilityPerk;
import adventurers.perks.mage.HexWeaknessPerk;
import adventurers.perks.mage.Sacrifice;

public class Occultist extends Archetype {

	public Occultist() {
		name = "Occultist";
		description = "one who delves into dark magic";
		perkChoices = new ArrayList<>();
		perkChoices.add(new HexAbyssPerk());
		perkChoices.add(new HexDespairPerk());
		perkChoices.add(new HexVulnerabilityPerk());
		perkChoices.add(new HexWeaknessPerk());
		perkChoices.add(new Sacrifice());
	}

	@Override
	public Choice getSignatureChoice() {
		return new DarkBargain();
	}

}
