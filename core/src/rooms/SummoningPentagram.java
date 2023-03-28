package rooms;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.textdungeon.game.GameScreen;
import com.textdungeon.game.TextDungeon;

import floors.Floor;
import items.consumables.RitualTome;
import mobs.Player;

public class SummoningPentagram extends Room {

	public static final String RITUAL_TOME = new RitualTome().getName();
	private final int STUDY_BONUS = 15;
	private final String MAKE_DEAL = "\"It is done,\" the demon says as the contract disappears. \"I have a feeling we will be seeing "
			+ "each other again...\"";
	
	private boolean isRitualPerformed;
	private boolean isSummoned;
	private boolean hasSpoken;
	private int XPMod;
	private int curseMod;
	private int statBuff;
	
	public SummoningPentagram(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Summoning Pentagram";
		isRitualPerformed = false;
		isSummoned = false;
		hasSpoken = false;
		XPMod = 3;
		curseMod = 3;
		statBuff = 20;
	}

	@Override
	public String getDescription() {
		if (hasSpoken)
			return "This room smells strongly of sulfur. On the floor is drawn a large pentagram in chalk and salt, and lit candles form "
					+ "a ring around it. \"I knew you'd be back,\" says the demon, holding aloft the contract.";
		return "This room smells strongly of sulfur. On the floor is drawn a large pentagram in chalk and salt, though it looks unfinished. "
				+ "Unlit candles ring the pentagram. If you knew rituals, you might be able to complete it, though that may be... inadvisable.";
	}

	@Override
	public String getCompletedDescription() {
		return "This room smells strongly of sulfur. On the floor is drawn a large pentagram in chalk and salt, and lit candles form "
				+ "a ring around it.";
	}

	@Override
	public void initRoomActions(Player player) {
		if (!isRitualPerformed && player.getInventory().containsKey(RITUAL_TOME)) {
			RoomAction performRitual = new RoomAction("Perform Ritual") {
				@Override
				public String resolveAction(Player player) {
					int ritualChance = player.getCurrentMind();
					if (GameScreen.isCursedPrisonStudied) {
						ritualChance += STUDY_BONUS;
						curseMod--;
					}
					if (GameScreen.isForbiddenTomeRead) {
						ritualChance += STUDY_BONUS;
						curseMod--;
					}
					if (GameScreen.generateRandom(1,100) <= ritualChance) {
						isSummoned = true;
						return "You begin the ritual, lighting the candles and finishing the pentagram beforehand. As you continue, the "
								+ "candles dim one by one without extinguishing. After the last dims, there's sudden silence, and they "
								+ "flare back to their normal brightness. \"You called?\" says a demonic voice from behind you.";
					} else {
						floor.modifyXPLimit(XPMod);
						player.modCurse(curseMod);
						return "You begin the ritual, lighting the candles and finishing the pentagram beforehand. But partway through, "
								+ "something goes wrong. The candles suddenly blow out, and shadow overtakes you. Something knows you are here...";
					}
				}
			};
			
			roomActions.add(performRitual);
		}
		
		if (isSummoned) {
			RoomAction speak = new RoomAction("Speak") {
				@Override
				public String resolveAction(Player player) {
					hasSpoken = true;
					return "\"I am at your service. And you at mine, coincidentally.\" With a flash of flame, a long parchment- "
							+ "undoubtedly a contract- appears in his hand. In his other appears a pair of spectacles that he places gingerly "
							+ "on his nose. \"If you agree, you will have your mortal abilities improved. For a price, of course. You need "
							+ "only tell me what you desire.\"";
				}
			};
			
			roomActions.add(speak);
		}
		
		if (hasSpoken) {
			RoomAction makeDeal = new RoomAction("Make a Deal") {
				@Override
				public String resolveAction(Player player) {
					initDealMenu(player);
					GameScreen.miscRoomTable.setVisible(true);
					return "";
				}
			};
			
			roomActions.add(makeDeal);
		}
	}
	
	private void finishDeal(Player player) {
		player.modCurse(curseMod);
		GameScreen.miscRoomTable.setVisible(false);
		setCompleted(true);
		GameScreen.setLogger(MAKE_DEAL);
	}

	private void initDealMenu(Player player) {
		GameScreen.miscRoomTable.clear();
		Label dealLabel = new Label("What do you desire?", TextDungeon.skin);
		
		TextButton accuracy = new TextButton("Accuracy", TextDungeon.skin);
		accuracy.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.modBaseAccuracy(statBuff/2);
				player.modBaseCritRate(statBuff);
				finishDeal(player);
			}
		});
		TextButton agility = new TextButton("Agility", TextDungeon.skin);
		agility.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.modBaseAgility(statBuff);
				finishDeal(player);
			}
		});
		TextButton mind = new TextButton("Mind", TextDungeon.skin);
		mind.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.modBaseMind(statBuff);
				finishDeal(player);
			}
		});
		TextButton strength = new TextButton("Strength", TextDungeon.skin);
		strength.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.modBaseStrength(statBuff);
				finishDeal(player);
			}
		});
		TextButton toughness = new TextButton("Toughness", TextDungeon.skin);
		agility.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.modBaseToughness(statBuff);
				finishDeal(player);
			}
		});
		TextButton nevermind = new TextButton("Nothing", TextDungeon.skin);
		nevermind.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameScreen.miscRoomTable.setVisible(false);
				GameScreen.setLogger("\"Cold feet, eh? Let me know when you change your mind. I'll be here...\"");
			}
		});
		
		GameScreen.miscRoomTable.add(dealLabel).colspan(3);
		GameScreen.miscRoomTable.row();
		GameScreen.miscRoomTable.add(accuracy);
		GameScreen.miscRoomTable.add(agility);
		GameScreen.miscRoomTable.add(mind);
		GameScreen.miscRoomTable.row();
		GameScreen.miscRoomTable.add(strength);
		GameScreen.miscRoomTable.add(toughness);
		GameScreen.miscRoomTable.add(nevermind);
	}
}
