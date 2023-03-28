package adventurers.archetypes;

import java.util.ArrayList;

import adventurers.choices.Choice;
import adventurers.choices.warrior.KnightDuel;
import adventurers.perks.warrior.BattleMedicPerk;
import adventurers.perks.warrior.GuidedSmite;
import adventurers.perks.warrior.InvinciblePerk;
import adventurers.perks.warrior.Ironclad;
import adventurers.perks.warrior.SmitePerk;

public class Knight extends Archetype {

	public Knight() {
		name = "Knight";
		description = "a holy warrior who swears a sacred oath";
		perkChoices = new ArrayList<>();
		perkChoices.add(new BattleMedicPerk());
		perkChoices.add(new GuidedSmite());
		perkChoices.add(new InvinciblePerk());
		perkChoices.add(new Ironclad());
		perkChoices.add(new SmitePerk());
	}
	
	@Override
	public Choice getSignatureChoice() {
		return new KnightDuel();
	}

}
