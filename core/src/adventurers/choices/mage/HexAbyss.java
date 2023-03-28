package adventurers.choices.mage;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.textdungeon.game.GameScreen;
import com.textdungeon.game.TextDungeon;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class HexAbyss extends Choice implements ChoiceTargetsMob {

	private int debuff;
	private int rounds;
	
	private enum HexChoice {
		ACCURACY, AGILITY, TOUGHNESS, MIND
	}
	
	HexChoice hexChoice;
	
	public HexAbyss() {
		name = "Hex of the Abyss";
		debuff = 30;
		rounds = 4;
		requiresTarget = true;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		requiresTarget = true;
		GameScreen.combatChoiceTable.clear();
		GameScreen.combatChoiceTable.setVisible(true);
		Label choiceLabel = new Label("Which stat do you hex?", TextDungeon.skin);
		
		TextButton accuracyButton = new TextButton("Accuracy", TextDungeon.skin);
		accuracyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hexChoice = HexChoice.ACCURACY;
				GameScreen.combatChoiceTable.setVisible(false);
			}
		});
		TextButton agilityButton = new TextButton("Agility", TextDungeon.skin);
		agilityButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hexChoice = HexChoice.AGILITY;
				GameScreen.combatChoiceTable.setVisible(false);
			}
		});
		TextButton mindButton = new TextButton("Mind", TextDungeon.skin);
		mindButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hexChoice = HexChoice.MIND;
				GameScreen.combatChoiceTable.setVisible(false);
			}
		});
		TextButton toughnessButton = new TextButton("Toughness", TextDungeon.skin);
		toughnessButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hexChoice = HexChoice.TOUGHNESS;
				GameScreen.combatChoiceTable.setVisible(false);
			}
		});
		
		GameScreen.combatChoiceTable.add(choiceLabel).colspan(2);
		GameScreen.combatChoiceTable.row();
		GameScreen.combatChoiceTable.add(accuracyButton);
		GameScreen.combatChoiceTable.add(agilityButton);
		GameScreen.combatChoiceTable.row();
		GameScreen.combatChoiceTable.add(mindButton);
		GameScreen.combatChoiceTable.add(toughnessButton);
		
		
		if (player.hasDarkBargain()) {
			requiresTarget = false;
			GameScreen.setLogger("The dark powers increase the power you channel...");
			for (Mob m : mobs) {
				switch (hexChoice) {
				case ACCURACY:
					GameScreen.setLogger(m.debuffAccuracy(debuff, rounds));
					break;
				case AGILITY:
					GameScreen.setLogger(m.debuffAgility(debuff, rounds));
					break;
				case MIND:
					GameScreen.setLogger(m.debuffMind(debuff, rounds));	
					break;
				case TOUGHNESS:
					GameScreen.setLogger(m.debuffToughness(debuff, rounds));
					break;
				default:
					break;
				}
			}
			player.setHasDarkBargain(false);
		} 
		
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Choose a target and debuff one stat of your choice.";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		switch (hexChoice) {
		case ACCURACY:
			return (target.debuffAccuracy(debuff, rounds));
		case AGILITY:
			return (target.debuffAgility(debuff, rounds));
		case MIND:
			return (target.debuffMind(debuff, rounds));	
		case TOUGHNESS:
			return (target.debuffToughness(debuff, rounds));
		}
		return "There was an error with your choice.";
	}

}
