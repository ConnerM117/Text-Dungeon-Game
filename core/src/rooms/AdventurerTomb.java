package rooms;

import java.util.ArrayList;
import java.util.List;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.Coins;
import items.Item;
import items.armor.Armor;
import items.consumables.Consumable;
import items.gear.Gear;
import items.weapons.Weapon;
import mobs.Player;

public class AdventurerTomb extends Room {

	private final String ATTACK_HIT = "You feel a blade slip through a chink your armor before you throw yourself back and away from them."
			+ "They lay breathing heavily, their eyes bloodshot and seething with madness. ";
	private final String ATTACK_MISS = "You throw yourself back and away from them. They lay breathing heavily, their eyes bloodshot "
			+ "and seething with madness. ";
	private final String WOUNDED = "Your wound oozes fresh blood. ";
	private boolean isAlive;
	private boolean isAwake;
	private boolean hasTalked;
	private boolean blessed;
	private int accuracy;
	private int woundRate;
	private int minDamage;
	private int maxDamage;
	private int woundRounds;
	private int woundDamage;
	private int minCoins;
	private int maxCoins;
	
	public AdventurerTomb(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Adventurer's Tomb";
		accuracy = 90;
		woundRate = 20;
		minDamage = 3;
		maxDamage = 6;
		woundRounds = 4;
		woundDamage = 2;
		minCoins = 3;
		maxCoins = 6;
		
		if (GameScreen.generateRandom(1, 10) == 1) //10% chance of the adventurer being alive
			isAlive = true;
	}
	
	@Override
	public String getDescription() {
		if (isAwake) {
			return "Here lies a pitiful half-dead adventurer, raving nonsense and brandishing a dagger. It would be dangerous to "
					+ "get close.";
		}
		return "Here lies a corpse: an ex-adventurer like you it would appear, from their trappings. If you have no qualms "
				+ "stealing from the dead, they might have something you could use.";
	}

	@Override
	public String getCompletedDescription() {
		if (blessed) {
			return "This place is empty; you recall it's where you restored the mind of the maddened adventurer.";
		} else if (isAwake && !isAlive) { //the player killed them
			return "Here lies the corpse of the pitiful adventurer you... helped along their way.";
		}
		return "Here lies a corpse: an ex-adventurer like you it would appear, from their trappings. They have nothing "
				+ "to offer you; best to leave the body be.";
	}

	@Override
	public void initRoomActions(Player player) {

		RoomAction talk = new RoomAction("Talk to them") {
			@Override
			public String resolveAction(Player player) {
				hasTalked = true;
				return "You speak, asking their name and how you can help. They groan loudly and answer, but it's the "
						+ "raving of a madman.";
			}
		};
		
		RoomAction attack = new RoomAction("Attack") {
			@Override
			public String resolveAction(Player player) {
				String str = "You draw your weapon and advance. Best to put them out of their misery. ";
				if (player.isAccurate()) {
					str += "You strike true and put an end to their miserable existence, telling yourself that you're doing them a favor. ";
					isAlive = false;
				} else {
					str += "There's still some life left in them, however, and they manage to defend and answer with a strike of their own! ";
					str += corpseAttack(player);
				}
				return str;
			}
		};
		
		RoomAction bless = new RoomAction("Invoke a Blessing") {
			@Override
			public String resolveAction(Player player) {
				String str = "You raise a hand and invoke your holy power to heal the plague that has stricken their mind. Their eyes "
						+ "refocus and their breathing slows. \"Thank you, friend. I thought that was the end for me. Once I've recovered "
						+ "I will be returning to the surface. Here, take this; I'll not be needing it.";
				str += "\n" + player.addToInventory(Item.getRandSpecialItem());
				str += "\n" + player.addToInventory(new Coins(minCoins*2, maxCoins*2));
				blessed = true;
				setCompleted(true);
				return str;
			}
		};	
		
		RoomAction searchAlive = new RoomAction("Search the Corpse") {
			@Override
			public String resolveAction(Player player) {
				StringBuilder str = new StringBuilder();				
				str.append("You grasp the body's shoulder and turn it over to face you. Suddenly the eyes snap open and "
						+ "it lunges at you! ");
				str.append(corpseAttack(player));
				
				isAwake = true;
				return str.toString();
			}
		};
		
		RoomAction searchCorpse = new RoomAction("Search the Corpse") {
			@Override
			public String resolveAction(Player player) {
				StringBuilder str = new StringBuilder();				
				str.append("You search the body thoroughly, though it is grotesque work. \"This could be me,\" you think. "
						+ "\"I wonder if eventually someone else will be rifling through my pockets...\"");
				
				int rand = GameScreen.generateRandom(1, 4);
				List<Integer> itemList = new ArrayList<>();
				//create a list of random and unique items the adventurer is carrying.
				for (int i = 0; i < rand; i++) {
					int randItem = GameScreen.generateRandom(1, 6);
					while (isDuplicateItem(randItem, itemList)) {
						randItem = GameScreen.generateRandom(1, 6);
					}
					itemList.add(randItem);
				}
				
				for (Integer i : itemList) {
					switch (i) {
					case 1:
						str.append("\n" + player.addToInventory(Armor.getRandArmorWeighted(floor.getFloorNumber())));
						break;
					case 2:
						str.append("\n" + player.addToInventory(Weapon.getRandWeaponWeighted(floor.getFloorNumber())));
						break;
					case 3:
						str.append("\n" + player.addToInventory(Gear.getRandGear()));
						break;
					case 4:
					case 5:
						str.append("\n" + player.addToInventory(Consumable.getRandConsumable()));
					case 6:
						str.append("\n" + player.addToInventory(new Coins(minCoins, maxCoins)));
						break;
					}
				}
				
				setCompleted(true);
				return str.toString();
			}
		};
		
		if (isAlive) {
			if (isAwake) {
				roomActions.add(talk);
				roomActions.add(attack);
				if (hasTalked && player.isHoly()) {
					roomActions.add(bless);
				}
			} else {
				roomActions.add(searchAlive);
			}
		} else {
			roomActions.add(searchCorpse);
		}

	}
	
	private String corpseAttack(Player player) {
		String str = "";
		int rand = GameScreen.generateRandom(1, 100);
		if (rand <= accuracy) {
			if (!player.doesDodge()) {
				str += ATTACK_HIT;
				player.takeDamage(GameScreen.generateRandom(minDamage, maxDamage));
				if (rand <= woundRate) {
					str += WOUNDED;
					player.setWounded(true, woundRounds, woundDamage);
				}
			} else {
				str += ATTACK_MISS;
			}
		} else {
			str += ATTACK_MISS;
		}
		return str;
	}
	
	private boolean isDuplicateItem(int randItem, List<Integer> itemList) {
		for (Integer i : itemList) {
			if (randItem == i)
				return true;
		}
		return false;
	}

}
