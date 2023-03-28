package adventurers.choices.rogue;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.textdungeon.game.GameScreen;
import com.textdungeon.game.TextDungeon;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Ballad extends Choice {

	private int tempHP;
	private int dodgeBuff;
	private int toughBuff;
	private int mindBuff;
	private int rounds;
	
	private enum BuffChoice {
		TEMP_HP, DODGE, MIND, TOUGHNESS
	}
	
	BuffChoice buffChoice;
	
	public Ballad() {
		name = "Ballad";
		tempHP = 2;
		dodgeBuff = 30;
		toughBuff = 30;
		mindBuff = 30;
		rounds = 4;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		GameScreen.combatChoiceTable.clear();
		GameScreen.combatChoiceTable.setVisible(true);
		Label choiceLabel = new Label("Choose a buff...", TextDungeon.skin);
		
		TextButton tempHPButton = new TextButton("HP Shield", TextDungeon.skin);
		tempHPButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				buffChoice = BuffChoice.TEMP_HP;
				GameScreen.combatChoiceTable.setVisible(false);
			}
		});
		TextButton dodgeButton = new TextButton("Dodge", TextDungeon.skin);
		dodgeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				buffChoice = BuffChoice.DODGE;
				GameScreen.combatChoiceTable.setVisible(false);
			}
		});
		TextButton mindButton = new TextButton("Mind", TextDungeon.skin);
		mindButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				buffChoice = BuffChoice.MIND;
				GameScreen.combatChoiceTable.setVisible(false);
			}
		});
		TextButton toughnessButton = new TextButton("Toughness", TextDungeon.skin);
		toughnessButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				buffChoice = BuffChoice.TOUGHNESS;
				GameScreen.combatChoiceTable.setVisible(false);
			}
		});
		
		GameScreen.combatChoiceTable.add(choiceLabel).colspan(2);
		GameScreen.combatChoiceTable.row();
		GameScreen.combatChoiceTable.add(tempHPButton);
		GameScreen.combatChoiceTable.add(dodgeButton);
		GameScreen.combatChoiceTable.row();
		GameScreen.combatChoiceTable.add(mindButton);
		GameScreen.combatChoiceTable.add(toughnessButton);
		
		switch (buffChoice) {
		case TEMP_HP:
			GameScreen.setLogger(player.setTempHP(tempHP));
			break;
		case DODGE:
			GameScreen.setLogger(player.buffDodge(dodgeBuff, rounds));
			break;
		case MIND:
			GameScreen.setLogger(player.buffMind(mindBuff, rounds));
			break;
		case TOUGHNESS:
			GameScreen.setLogger(player.buffToughness(toughBuff, rounds));
			break;
		}

		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Choose a bonus: HP Shield, Dodge, Mind, or Toughness";
	}

}
