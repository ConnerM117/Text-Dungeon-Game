package rooms.story;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.textdungeon.game.GameScreen;
import com.textdungeon.game.TextDungeon;

import floors.Floor;
import items.Item;
import items.KeyItem;
import items.armor.Armor;
import items.consumables.SmithingTools;
import items.consumables.WeirdArtifact;
import items.weapons.Weapon;
import mobs.Player;
import rooms.Room;

public class Smithy extends Room {

	public static final String CLOCKWORK_KEY = "Clockwork Key";
	
	private boolean hasFirstImprovement;
	private boolean hasTools;
	private String smithingTools;
	private String weirdArtifact;
	private int improvementCost;
	private int armorImprovement;
	private int weaponImprovement;
	
	public Smithy(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Smithy";
		combatChance = NO_COMBAT_CHANCE;
		
		hasTools = false;
		smithingTools = new SmithingTools().getName();
		weirdArtifact = new WeirdArtifact().getName();
		improvementCost = 10;
		armorImprovement = 2;
		weaponImprovement = 2;
	}

	@Override
	public String getDescription() {
		if (GameScreen.isBlacksmithFreed) {
			if (hasTools)
				return "\"Hello again!\" the blacksmith says. \"Anything I can do for you?\"";
			else
				return "\"Hello again!\" You recognize the man hailing you as the one you freed from the goblin prison. He sits next to "
						+ "a run-down shack where stands a smithy, complete with a forge, smelter, anvil, and other tools of the trade. "
						+ "It seems to be in servicable condition, but he seems to be looking for something.";
		}
		return "Next to a run-down shack stands a smithy, complete with a forge, smelter, anvil, and other tools of the trade. It seems "
				+ "to be in servicable condition, but you don't know how to use it.";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (player.getInventory().containsKey(smithingTools) && GameScreen.isBlacksmithFreed) {
			RoomAction giveTools = new RoomAction("Give Tools") {
				@Override
				public String resolveAction(Player player) {
					player.removeFromInventory(smithingTools);
					hasTools = true;
					return "";
				}
			};
			
			roomActions.add(giveTools);
		}
		
		if (!hasTools && GameScreen.isBlacksmithFreed) {
			RoomAction speak = new RoomAction("Speak") {
				@Override
				public String resolveAction(Player player) {
					return "\"It's good to see you,\" he says. \"I am in your debt, but I'm afraid I've another favor to ask. It seems I've "
							+ "misplaced my " + smithingTools + " somewhere. If you find them, please bring them back to me. If you do, "
							+ "I'll be able to improve your weapons and armor.";
				}
			};
			
			roomActions.add(speak);
		} else if (hasTools && GameScreen.isBlacksmithFreed) {
			RoomAction hire = new RoomAction("Hire") {
				@Override
				public String resolveAction(Player player) {
					initBlacksmithTable(player);
					GameScreen.miscRoomTable.setVisible(true);
					return "";
				}
			};
			
			roomActions.add(hire);
			
			if (player.getInventory().containsKey(weirdArtifact)) {
				RoomAction showArtifact = new RoomAction("Show " + weirdArtifact) {
					@Override
					public String resolveAction(Player player) {
						player.removeFromInventory(weirdArtifact);
						player.addToInventory(new KeyItem(CLOCKWORK_KEY));
						return "\"What an odd artifact,\" he says as he takes it from you to study it. \"But... it looks like it "
								+ "can be opened? Give me a moment.\" A few minutes later he returns holding a key. \"I found this "
								+ "inside of it. I don't know what it unlocks, but perhaps you can make use of it.\"";
					}
				};
				
				roomActions.add(showArtifact);
			}			
		}
		
		
	}

	protected void initBlacksmithTable(Player player) {
		GameScreen.miscRoomTable.clear();
		Label blacksmithLabel = new Label("What would you like to improve?\nYour first improvement is free; each thereafter is 10 coins.", TextDungeon.skin);
		blacksmithLabel.setWrap(true);
		
		GameScreen.miscRoomTable.add(blacksmithLabel).colspan(3);
		GameScreen.miscRoomTable.row();
		
		int counter = 0;
		for (Item item : player.getInventory().values()) {
			
			if (item.getType() == Item.Type.ARMOR && !((Armor) item).isImproved()) {
				TextButton button = new TextButton(item.getName(), TextDungeon.skin);
				button.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if (hasFirstImprovement) { //if this is not the first improvement
							if (player.getCoins() < improvementCost) {
								GameScreen.setLogger("You don't have enough coins!");
								GameScreen.miscRoomTable.setVisible(false);
								return;
							}
							GameScreen.setLogger(player.spendCoins(improvementCost));
						}
						hasFirstImprovement = true;
						((Armor) item).modMaxArmor(armorImprovement);
						GameScreen.miscRoomTable.setVisible(false);
					}
				});
				
				GameScreen.playerSellTable.add(button);
				counter++;
				if (counter % 3 == 0)
					GameScreen.playerSellTable.row();
				
			} else if (item.getType() == Item.Type.WEAPON && !((Weapon) item).isImproved()) {
				TextButton button = new TextButton(item.getName(), TextDungeon.skin);
				button.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if (hasFirstImprovement) { //if this is not the first improvement
							if (player.getCoins() < improvementCost) {
								GameScreen.setLogger("You don't have enough coins!");
								GameScreen.miscRoomTable.setVisible(false);
								return;
							}
							GameScreen.setLogger(player.spendCoins(improvementCost));
						}
						hasFirstImprovement = true;
						((Weapon) item).modMaxDamage(weaponImprovement);
						GameScreen.miscRoomTable.setVisible(false);
					}
				});
				
				GameScreen.playerSellTable.add(button);
				counter++;
				if (counter % 3 == 0)
					GameScreen.playerSellTable.row();
			}
			
		}
	}

}
