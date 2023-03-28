package rooms;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.textdungeon.game.GameScreen;
import com.textdungeon.game.TextDungeon;

import floors.Floor;
import mobs.*;
import items.*;
import items.weapons.*;
import items.armor.*;
import items.consumables.*;
import items.gear.*;
import items.runes.Rune;

public class Shop extends Room {
	
	private static float BUTTON_PAD = 5f;
	private static int BUTTON_WIDTH = 150;

	private enum BuyOrSell {
		BUY, SELL
	}
	BuyOrSell buyOrSell;
	
	private Item selectedItem;
	
	private List<Item> shopInventory;
	private boolean hasPurchased;	
	
	public Shop(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Shop";
		hasPurchased = false;
		buyOrSell = null;
		selectedItem = null;
		shopInventory = new ArrayList<>();
		shopInventory.add(new HealingPotion());
		shopInventory.add(new GoblinMoonshine());
		shopInventory.add(new Ration());
		shopInventory.add(Consumable.getRandConsumable());
		shopInventory.add(Consumable.getRandConsumable());
		shopInventory.add(Consumable.getRandConsumable());
		shopInventory.add(Weapon.getRandWeaponWeighted(floor.getFloorNumber()));
		shopInventory.add(Weapon.getRandWeaponWeighted(floor.getFloorNumber()));
		shopInventory.add(Armor.getRandArmorWeighted(floor.getFloorNumber()));
		shopInventory.add(Gear.getRandGear());
		shopInventory.add(Item.getRandItemWeighted(floor.getFloorNumber()));
		shopInventory.add(Item.getRandItemWeighted(floor.getFloorNumber()));
		shopInventory.add(Rune.getRandRuneWeighted(floor.getFloorNumber()));
		combatChance = NO_COMBAT_CHANCE;
	}
	
	@Override
	public String getDescription() {
		return "A wizened old goblin stands on a stool behind the counter with shelves of goods behind him. "
				+ "\"What'll it be today?\" he asks in a gravelly voice.";
	}

	@Override
	public String getCompletedDescription() {
		return "You can't complete a shop. Nice!";
	}

	@Override
	public void initRoomActions(Player player) {
		
		RoomAction buySell = new RoomAction("Buy/Sell") {
			@Override
			public String resolveAction(Player player) {
				initShopTables(player);
				GameScreen.shopTable.setVisible (true);
				return "The goblin grins, showing you what he's got in stock...";
			}
		};
		
		roomActions.add(buySell);
	}
	
	private void initShopTables(Player player) {
		GameScreen.shopTopRowButtons.clear();
		GameScreen.playerSellTable.clear();
		GameScreen.shopInventoryTable.clear();
		GameScreen.shopDescriptionTable.clear();
		GameScreen.shopDescription = new Label("", TextDungeon.skin);
		GameScreen.shopDescription.setWrap(true);
		
		TextButton playerInventoryButton = new TextButton(player.getName() + "'s Inventory", TextDungeon.skin);
		playerInventoryButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameScreen.shopInventoryTable.setVisible(false);
				GameScreen.playerSellTable.setVisible(true);
			}
		});
		
		TextButton shopButton = new TextButton("Shop Inventory", TextDungeon.skin);
		shopButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameScreen.shopInventoryTable.setVisible(true);
				GameScreen.playerSellTable.setVisible(false);
			}
		});
		
		TextButton buySellButton = new TextButton("Buy", TextDungeon.skin);
		buySellButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (selectedItem == null)
					return;
				
				switch (buyOrSell) {
				case BUY:
					if (player.getCoins() < selectedItem.getCost()) {
						GameScreen.shopDescription.setText("You don't have enough coins!");
						return;
					}
					player.addToInventory(selectedItem);
					player.spendCoins(selectedItem.getCost());
					shopInventory.remove(selectedItem);
					hasPurchased = true;
					break;
				case SELL:
					player.sellInventoryItem(selectedItem);;
					break;
				default:
					break;
				}
				initShopTables(player); //reset the tables to update inventories and labels
			}
		});
		
		TextButton exitButton = new TextButton("Exit", TextDungeon.skin);
		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (hasPurchased)
					GameScreen.setLogger("\"Thank you, come again!\" the goblin calls, clinking your coins.");
				else 
					GameScreen.setLogger("The shopkeeper grumbles as you move away without buying.");
				GameScreen.shopTable.setVisible(false);
			}
		});
		
		GameScreen.shopTopRowButtons.add(playerInventoryButton).pad(BUTTON_PAD);
		GameScreen.shopTopRowButtons.add(shopButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		GameScreen.shopTopRowButtons.add(exitButton).width(BUTTON_WIDTH).pad(BUTTON_PAD);
		
		Label playerSellLabel = new Label(player.getName() + "'s Inventory", TextDungeon.skin);
		Label playerCoinsLabel = new Label("Coins: " + player.getCoins(), TextDungeon.skin);
		GameScreen.playerSellTable.add(playerSellLabel).colspan(2).center();
		GameScreen.playerSellTable.row();
		GameScreen.playerSellTable.add(playerCoinsLabel).colspan(2).left();
		GameScreen.playerSellTable.row();
		
		int counter = 0;
		for (Item item : player.getInventory().values()) {
			
			TextButton button = new TextButton(item.getName(), TextDungeon.skin);
			button.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					selectedItem = item;
					buyOrSell = BuyOrSell.SELL;
					GameScreen.shopDescription.setText(item.getStatistics());
					buySellButton.setText("Sell " + item.getName());
				}
			});
			
			GameScreen.playerSellTable.add(button).width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 2 == 0)
				GameScreen.playerSellTable.row();
		}
		
		Label shopInventoryLabel = new Label("Shop Inventory", TextDungeon.skin);
		GameScreen.shopInventoryTable.add(shopInventoryLabel).colspan(2).center().expand();
		GameScreen.shopInventoryTable.row();
		GameScreen.shopInventoryTable.add(playerCoinsLabel).colspan(2).left();
		GameScreen.shopInventoryTable.row();
		
		counter = 0;
		for (Item item : shopInventory) {
			
			TextButton button = new TextButton(item.getName(), TextDungeon.skin);
			button.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					selectedItem = item;
					buyOrSell = BuyOrSell.BUY;
					GameScreen.shopDescription.setText(item.getStatistics());
					buySellButton.setText("Buy " + item.getName());
				}
			});
			
			GameScreen.shopInventoryTable.add(button).expand().width(BUTTON_WIDTH).pad(BUTTON_PAD);
			counter++;
			if (counter % 2 == 0)
				GameScreen.shopInventoryTable.row();
		}
		
		GameScreen.shopDescriptionTable.add(GameScreen.shopDescription).left().width(300).expandY();
		GameScreen.shopDescriptionTable.row();
		GameScreen.shopDescriptionTable.add(buySellButton).expandX().pad(BUTTON_PAD);
	}
	
}
