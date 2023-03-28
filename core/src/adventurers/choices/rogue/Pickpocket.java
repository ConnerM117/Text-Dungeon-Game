package adventurers.choices.rogue;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.textdungeon.game.GameScreen;
import com.textdungeon.game.TextDungeon;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import items.Item;
import items.consumables.Consumable;
import mobs.Mob;
import mobs.Player;

public class Pickpocket extends Choice implements ChoiceTargetsMob {

	private int expertThiefChoices;
	
	public Pickpocket() {
		name = "Pickpocket";
		expertThiefChoices = 3;
		requiresTarget = true;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			//GameScreen.chooseTarget(mobs, this);
			
			return true;
		} else {
			GameScreen.log("You don't have enough Stamina!");
			return false;
		}
	}

	@Override
	public String getDescription(Player player) {
		if (player.isExpertThief())
			return "Spend 1 Stamina to steal one of three items from an enemy.";
		return "Spend 1 Stamina to steal a random item from an enemy.";
	}

	private void getItemChoice(Player player, Mob target) {
		List<Item> itemList = new ArrayList<>();
		for (int i = 0; i < expertThiefChoices; i++) {
			itemList.add(createItem(target));
		}
		
		GameScreen.combatChoiceTable.clear();
		GameScreen.combatChoiceTable.setVisible(true);
		Label choiceLabel = new Label("Which item do you steal?", TextDungeon.skin);
		GameScreen.combatChoiceTable.add(choiceLabel).colspan(2);
		GameScreen.combatChoiceTable.row();
		
		int counter = 0;
		for (Item item : itemList) {
			TextButton button = new TextButton(item.getName(), TextDungeon.skin);
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					player.addToInventory(item);
					GameScreen.combatChoiceTable.setVisible(false);
				}
			});
			
			GameScreen.combatChoiceTable.add(button);
			counter++;
			if (counter % 2 == 0) {
				GameScreen.combatChoiceTable.row();
			}
		}
	}
	
	private Item createItem(Mob target) {
		Item item = null;
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1)
			item = Consumable.getRandConsumable();
		else
			item = target.getItemDrop();
		
		if (item == null) { //some targets don't have an item drop
			item = Consumable.getRandConsumable();
		}
		return item;
	}

	@Override
	public String targetMob(Player player, Mob target) {
		String str = "";
		
		if (!target.isStolenFrom()) { //if the target hasn't been stolen from yet
			str += (name + " steals from " + target.getName() + "!\n" + player.spendStamina(1));
		} else {
			str += (target.getName() + " has already been stolen from!");
			return str;
		}

		if (player.isExpertThief()) {
			getItemChoice(player, target);
		} else {
			str += (player.addToInventory(createItem(target)));
		}
		
		return str;
	}
}
