package rooms.story;

import com.textdungeon.game.GameScreen;

import combat.BoundDemonEncounter;
import combat.CombatEncounter;
import floors.Floor;
import items.Item;
import items.consumables.RitualTome;
import items.runes.Rune;
import mobs.Player;
import rooms.Room;

public class BoundDemon extends Room {

	private boolean hasSpoken;
	private boolean hasDisbelieved;
	private boolean hasAgreed;
	private boolean isHuman;
	private boolean isAttacking;
	private int curseValue;
	private String ritualTome = new RitualTome().getName();
	
	public BoundDemon(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		combatChance = NO_COMBAT_CHANCE;
		name = "Bound Demon";
		hasSpoken = false;
		hasDisbelieved = false;
		hasAgreed = false;
		isAttacking = false;
		curseValue = 2;
		
		if (GameScreen.generateRandom(1, 2) == 1)
			isHuman = true;
		else
			isHuman = false;
	}
	
	@Override
	public CombatEncounter getCombatEncounter() {
		return new BoundDemonEncounter();
	}

	@Override
	public String getDescription() {
		if (!hasSpoken)
			return "A perfect circle drawn with chalk and salt spans most of this otherwise empty stone room. A tall figure "
					+ "with red skin, a tail, and black horns stands in the circle. Upon seeing you, he rushes right up to the edge "
					+ "without crossing and says, \"Help! Help me!\"";
		return "\"There are two rituals in the tome,\" the demon says. \"You need to perform the first ritual.\"";
	}

	@Override
	public String getCompletedDescription() {
		return "A perfect circle drawn with chalk and salt spans most of this otherwise empty stone room.";
	}

	@Override
	public void initRoomActions(Player player) {
		if (!hasSpoken) {
			RoomAction speak = new RoomAction("Speak") {
				@Override
				public String resolveAction(Player player) {
					hasSpoken = true;
					return "\"Listen, please! I'm not what I appear to be! I'm human, but made some mistakes in a ritual, and... well, "
							+ "this is the result. Please, I need you to help change me back!\"";
				}
			};
			
			roomActions.add(speak);
		} else if (!hasAgreed) {
			RoomAction speak = new RoomAction("I'll help you") {
				@Override
				public String resolveAction(Player player) {
					hasAgreed = true;
					return "He brightens. \"Excellent. To reverse this, you'll need the Ritual Tome from the Forbidden Library, and "
							+ "follow the instructions therein. I'll look forward to your return.\"";
				}
			};
			
			roomActions.add(speak);
		}
		
		if (!hasDisbelieved && hasSpoken) {
			RoomAction disbelieve = new RoomAction("Don't Believe You") {
				@Override
				public String resolveAction(Player player) {
					hasDisbelieved = true;
					if (player.isMindful()) {
						if (isHuman) { //he's human and telling the truth
							return "The demon sags. \"I can't blame you, honestly. It's an odd sight, I know... I wouldn't believe me "
									+ "either.\" His response seems to be genuine.";
						} else { //he's a demon and is lying
							return "The demon's face contorts. \"Please! I know what this looks like, but you've got to help me! I will "
									+ "reward you if you can help set me free!\"";
						}
					}
					//otherwise, you can't tell whether he's lying or not
					return "The demon sags. \"I can't blame you, honestly. I wouldn't believe me either.\" You have a difficult time "
							+ "telling whether he's being truthful or not.";
				}
			};
			
			roomActions.add(disbelieve);
		}
		
		RoomAction attack = new RoomAction("Attack!") {
			@Override
			public String resolveAction(Player player) {
				isAttacking = true;
				return "";
			}
		};
		
		roomActions.add(attack);
		
		if (player.getInventory().containsKey(ritualTome)) {
			RoomAction unbindRitual = new RoomAction("First Ritual") {
				@Override
				public String resolveAction(Player player) {
					GameScreen.setLogger("Following the instructions in the tome, you perform the ritual designated by the demon. After a few minutes, "
							+ "it goes almost completely dark, and he begins to scream in pain. You push through, starting to "
							+ "wonder if you're doing the right thing, when suddenly the light returns and the screaming fades.");
					if (isHuman) {
						setCompleted(true);
						return "In the demon's place lies a man, who slowly stands. \"Thank you,\" he says. \"I thought I was stuck "
								+ "like that... but I can't let you go without a reward. Here, take this.\n" 
								+ player.addToInventory(Rune.getRandGreaterRune());
					} else {
						addDroppedItem(Item.getDemonItem());
						isAttacking = true;
						return "\"Freedom,\" cackles the demon. \"You mortals are so gullible! My first act will be spreading your "
								+ "blood on the walls!\"";
					}
				}
			};
			
			RoomAction bindRitual = new RoomAction("Second Ritual") {
				@Override
				public String resolveAction(Player player) {
					GameScreen.setLogger("Following the instructions in the tome, you perform a ritual- but not the one the demon "
							+ "told you to perform. After a few minutes, "
							+ "it goes almost completely dark, and he begins to scream in pain. You push through, starting to "
							+ "wonder if you're doing the right thing, when suddenly the light returns and the screaming fades.");
					setCompleted(true);
					if (isHuman) {
						player.modCurse(curseValue);
						return "\"Not that ritual! It's WRONG. IT'S ALL WRONG!\" he wails. \"I AM DAMNED! DAMNED! BECAUSE OF YOU! He "
								+ "continues to wail as energy builds in the circle, out of your control, and then with a brilliant "
								+ "flash, he is gone. The weight of your decision rests heavy upon you.";
					} else {
						addDroppedItem(Item.getDemonItem());
						return "\"NO!\" the demon screeches. \"NO! IT'S WRONG! THE WRONG ONE!\" He continues to wail as energy builds in "
								+ "the circle, out of your control, and then with a brilliant flash, he is gone. Something drops to the "
								+ "ground where the thing was standing.";
					}
				}
			};
			
			roomActions.add(bindRitual);
			roomActions.add(unbindRitual);
		}
	}
	
	@Override
	public boolean hasCombat() {
		if (isAttacking) {
			return true;
		}
		return false;
	}
	
	@Override
	public void setCompleted(boolean isCompleted) {
		super.setCompleted(isCompleted);
		isAttacking = false;
	}

}
