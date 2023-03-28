package adventurers.perks.kin;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.textdungeon.game.GameScreen;
import com.textdungeon.game.TextDungeon;

import adventurers.Mage;
import adventurers.Rogue;
import adventurers.Warrior;
import adventurers.perks.general.Perk;
import mobs.Player;

public class JackOfAllTrades extends Perk {

	private static final float BUTTON_PAD = 5f;
	private static final int BUTTON_WIDTH = 100;
	
	List<Perk> perkChoices;
	Perk selectedPerk;
	
	public JackOfAllTrades() {
		name = "Jack Of All Trades";
		description = "Choose one perk from an Adventurer (but not Archetype) other than your own";
		perkChoices = new ArrayList<>();
	}
	
	@Override
	public void applyBenefits(Player player) {
		Warrior warrior = new Warrior();
		Rogue rogue = new Rogue();
		Mage mage = new Mage();
		String adv = player.getAdventurer().getName();
		
		Label perkDescription = new Label("", TextDungeon.skin);
		perkDescription.setWrap(true);

		//if it's not a warrior, add warrior stuff
		if (!adv.equals(warrior.getName())) {
			for (Perk p : warrior.getPerkChoices()) {
				perkChoices.add(p);
			}
		}
		//if it's not a rogue, add rogue stuff
		if (!adv.equals(rogue.getName())) {
			for (Perk p : rogue.getPerkChoices()) {
				perkChoices.add(p);
			}
		}
		//if it's not a mage, add mage stuff
		if (!adv.equals(mage.getName())) {
			for (Perk p : mage.getPerkChoices()) {
				perkChoices.add(p);
			}
		}
		
		GameScreen.combatChoiceTable.clear();
		Label perkLabel = new Label("Choose a new Perk...", TextDungeon.skin);
		GameScreen.combatChoiceTable.add(perkLabel).colspan(3).pad(20f);
		GameScreen.combatChoiceTable.row();
		
		int counter = 0;
		for (Perk p : perkChoices) {
			TextButton button = new TextButton(p.getName(), TextDungeon.skin);
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					p.applyBenefits(player);
					perkDescription.setText(p.getName() + "\n" + p.getDescription());
					selectedPerk = p;
				}
			});
			
			GameScreen.combatChoiceTable.add(button).pad(BUTTON_PAD).width(BUTTON_WIDTH);
			counter++;
			if (counter % 3 == 0) {
				GameScreen.combatChoiceTable.row();
			}
		}
		
		// only add another row if the last perk didn't
		if (counter % 3 != 0)
			GameScreen.combatChoiceTable.row();
		GameScreen.combatChoiceTable.add(perkDescription).colspan(3).width(300f);
		
		TextButton confirmButton = new TextButton("Confirm", TextDungeon.skin);
		confirmButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (selectedPerk == null) {
					perkDescription.setText("You must choose a perk before you can confirm.");
					return;
				}
				
				selectedPerk.applyBenefits(player);
				GameScreen.combatChoiceTable.setVisible(false);
				GameScreen.combatChoiceTable.clear();
			}
		});
		
		GameScreen.combatChoiceTable.row();
		GameScreen.combatChoiceTable.add(confirmButton).colspan(3).pad(BUTTON_PAD).width(BUTTON_WIDTH);

		GameScreen.combatChoiceTable.setVisible(true);
	}
}
