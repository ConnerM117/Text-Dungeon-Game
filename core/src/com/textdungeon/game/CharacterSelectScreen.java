package com.textdungeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.textdungeon.game.TextDungeon.ScreenKey;

import adventurers.archetypes.Assassin;
import adventurers.archetypes.Berserker;
import adventurers.archetypes.Burglar;
import adventurers.archetypes.Illusionist;
import adventurers.archetypes.Knight;
import adventurers.archetypes.ManAtArms;
import adventurers.archetypes.Occultist;
import adventurers.archetypes.Pyromancer;
import adventurers.archetypes.Troubadour;
import mobs.Player;

public class CharacterSelectScreen implements Screen {

	private static float BUTTON_PAD = 5f;
	private static float BUTTON_WIDTH = 100f;
	
	public enum KinChoice {
		HUMAN, ELF, DWARF, ORC
	}
	
	public enum AdventurerChoice {
		MAGE, ROGUE, WARRIOR
	}
	
	public enum ArchetypeChoice {
		ILLUSIONIST, OCCULTIST, PYROMANCER,
		ASSASSIN, BURGLAR, TROUBADOUR,
		BERSERKER, KNIGHT, MANATARMS
	}
	
	final TextDungeon game;
	private final Player player;
	
	KinChoice kinChoice;
	AdventurerChoice adventurerChoice;
	ArchetypeChoice archetypeChoice;
	
	private Illusionist illusionist;
	private Occultist occultist;
	private Pyromancer pyromancer;
	private Assassin assassin;
	private Burglar burglar;
	private Troubadour troubadour;
	private Berserker berserker;
	private Knight knight;
	private ManAtArms manAtArms;
	
	Stage stage;
	OrthographicCamera camera;
	Viewport viewport;
	Table table;
	Table kinTable;
	Table adventurerTable;
	Table archetypeTable;
	Stack archetypeStack;
	Table miscTable;
	
	Table mageArchetypes;
	Table rogueArchetypes;
	Table warriorArchetypes;
	
	Label lblKinChoice;
	Label lblKinDescription;
	Label lblAdventurerChoice;
	Label lblAdventurerDescription;
	Label lblArchetypeChoice;
	Label lblArchetypeDescription;
	
	public CharacterSelectScreen(final TextDungeon game, final Player player) {
		this.game = game;
		this.player = player;
		
		illusionist = new Illusionist();
		occultist = new Occultist();
		pyromancer = new Pyromancer();
		assassin = new Assassin();
		burglar = new Burglar();
		troubadour = new Troubadour();
		berserker = new Berserker();
		knight = new Knight();
		manAtArms = new ManAtArms();
		
		camera = new OrthographicCamera();
		viewport = new FitViewport(800, 480);
		stage = new Stage(viewport);
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		miscTable = new Table();
		stage.addActor(miscTable);
		
		kinTable = new Table();
		adventurerTable = new Table();
		archetypeTable = new Table();
		archetypeStack = new Stack();
		
		mageArchetypes = new Table();
		rogueArchetypes = new Table();
		warriorArchetypes = new Table();
		
		archetypeStack.add(mageArchetypes);
		archetypeStack.add(rogueArchetypes);
		archetypeStack.add(warriorArchetypes);
		
		initKinTable();
		initAdventurerTable();
		initArchetypeTables();
		initMiscGroup();
		
		table.add(kinTable).expandY().width(stage.getWidth()/4).top().padTop(20f);
		table.add(adventurerTable).width(stage.getWidth()/3).top().padTop(20f);
		table.add(archetypeTable).width(stage.getWidth() /3).top().padTop(20f);
		table.pad(30f);
		table.padTop(30f);
		
		miscTable.setPosition(Gdx.graphics.getWidth()/2 + 80f, Gdx.graphics.getHeight()-20);
		//table.setDebug(true);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.BLACK);
		
		camera.update();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
        camera.update();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}
	
	private void initKinTable() {
		Label lblKinTitle = new Label("Choose Your Kin", TextDungeon.skin);
		lblKinChoice = new Label("", TextDungeon.skin);
		lblKinDescription = new Label("", TextDungeon.skin);
		lblKinDescription.setWrap(true);
		TextButton humanButton = new TextButton("Human", TextDungeon.skin);
		humanButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblKinChoice.clear();
				lblKinChoice.setText("HUMAN");
				lblKinDescription.clear();
				lblKinDescription.setText("Humans are hardy and adaptable."
						+ "\nBonuses: +5 to Dodge, Toughness, Mind, and Strength.");
				kinChoice = KinChoice.HUMAN;
			}
		});
		TextButton dwarfButton = new TextButton("Dwarf", TextDungeon.skin);
		dwarfButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblKinChoice.clear();
				lblKinChoice.setText("DWARF");
				lblKinDescription.clear();
				lblKinDescription.setText("Dwarves are tough and resilient."
						+ "\nBonuses: +15 Toughness, +3 Hit Points.");
				kinChoice = KinChoice.DWARF;
			}
		});
		TextButton elfButton = new TextButton("Elf", TextDungeon.skin);
		elfButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblKinChoice.clear();
				lblKinChoice.setText("ELF");
				lblKinDescription.clear();
				lblKinDescription.setText("Elves are nimble and intelligent."
						+ "\nBonuses: +10 to Dodge and Mind.");
				kinChoice = KinChoice.ELF;
			}
		});
		TextButton orcButton = new TextButton("Orc", TextDungeon.skin);
		orcButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblKinChoice.clear();
				lblKinChoice.setText("ORC");
				lblKinDescription.clear();
				lblKinDescription.setText("Orcs are strong and battle-worn."
						+ "\nBonuses: +10 to Toughness and Strength.");
				kinChoice = KinChoice.ORC;
			}
		});
		
		kinTable.add(lblKinTitle);
		kinTable.row();
		kinTable.add(humanButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		kinTable.row();
		kinTable.add(dwarfButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		kinTable.row();
		kinTable.add(elfButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		kinTable.row();
		kinTable.add(orcButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		kinTable.row();
		kinTable.add(lblKinChoice).padTop(BUTTON_PAD);
		kinTable.row();
		kinTable.add(lblKinDescription).width(stage.getWidth()/4).top().left().padTop(BUTTON_PAD);
	}
	
	private void initAdventurerTable() {
		Label lblAdventurerTitle = new Label("Choose Adventurer", TextDungeon.skin);
		lblAdventurerChoice = new Label("", TextDungeon.skin);
		lblAdventurerDescription = new Label("", TextDungeon.skin);
		lblAdventurerDescription.setWrap(true);
		
		TextButton mageButton = new TextButton("Mage", TextDungeon.skin);
		mageButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblAdventurerChoice.clear();
				lblAdventurerChoice.setText("MAGE");
				lblAdventurerDescription.clear();
				lblAdventurerDescription.setText("Mages are powerful and versatile magic-users "
						+ "with a reliance on Mind and Magic Damage. Spells that improve your "
						+ "stats can be more useful than damage in the right circumstances."
						+ "\nBonuses: Mind +10, Dodge +5.");
				adventurerChoice = AdventurerChoice.MAGE;
				mageArchetypes.setVisible(true);
				rogueArchetypes.setVisible(false);
				warriorArchetypes.setVisible(false);
				archetypeChoice = null;
			}
		});
		TextButton rogueButton = new TextButton("Rogue", TextDungeon.skin);
		rogueButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblAdventurerChoice.clear();
				lblAdventurerChoice.setText("ROGUE");
				lblAdventurerDescription.clear();
				lblAdventurerDescription.setText("Rogues are stealthy and fast fighters that "
						+ "rely heavily on Dodge and on building up buffs to land high-damage "
						+ "attacks. They are also typically more adept and navigating the dungeon."
						+ "\nBonuses: Dodge +10, Mind +5.");
				adventurerChoice = AdventurerChoice.ROGUE;
				mageArchetypes.setVisible(false);
				rogueArchetypes.setVisible(true);
				warriorArchetypes.setVisible(false);
				archetypeChoice = null;
			}
		});
		TextButton warriorButton = new TextButton("Warrior", TextDungeon.skin);
		warriorButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblAdventurerChoice.clear();
				lblAdventurerChoice.setText("WARRIOR");
				lblAdventurerDescription.clear();
				lblAdventurerDescription.setText("Warriors are straightforward adventurers "
						+ "that can take a bruising and hit hard. What they lack in utility "
						+ "they make up for in combat prowess."
						+ "\nBonuses: Strength +10, Toughness +5.");
				adventurerChoice = AdventurerChoice.WARRIOR;
				mageArchetypes.setVisible(false);
				rogueArchetypes.setVisible(false);
				warriorArchetypes.setVisible(true);
				archetypeChoice = null;
			}
		});
		
		adventurerTable.add(lblAdventurerTitle);
		adventurerTable.row();
		adventurerTable.add(mageButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		adventurerTable.row();
		adventurerTable.add(rogueButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		adventurerTable.row();
		adventurerTable.add(warriorButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		adventurerTable.row();
		adventurerTable.add(lblAdventurerChoice);
		adventurerTable.row();
		adventurerTable.add(lblAdventurerDescription).width(stage.getWidth()/3).top().left();
	}
	
	private void initArchetypeTables() {
		Label lblArchetypeTitle = new Label("Choose Archetype", TextDungeon.skin);
		lblArchetypeChoice = new Label("", TextDungeon.skin);
		lblArchetypeDescription = new Label("", TextDungeon.skin);
		lblArchetypeDescription.setWrap(true);
		
		TextButton illusionistButton = new TextButton("Illusionist", TextDungeon.skin);
		illusionistButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblArchetypeChoice.clear();
				lblArchetypeChoice.setText("ILLUSIONIST");
				lblArchetypeDescription.clear();
				lblArchetypeDescription.setText("Illusionists focus on misdirection to "
						+ "turn the tide of battle in their favor.\n" 
						+ "Signature Ability: " + illusionist.getSignatureChoice().getName()
						+ "\n" + illusionist.getSignatureChoice().getDescription(player));
				archetypeChoice = ArchetypeChoice.ILLUSIONIST;
			}
		});
		TextButton occultistButton = new TextButton("Occultist", TextDungeon.skin);
		occultistButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblArchetypeChoice.clear();
				lblArchetypeChoice.setText("OCCULTIST");
				lblArchetypeDescription.clear();
				lblArchetypeDescription.setText("Occultists deal with dark powers "
						+ "to grant themselves additional bonuses and increase their destructive capabilities.\n" 
						+ "Signature Ability: " + occultist.getSignatureChoice().getName()
						+ "\n" + occultist.getSignatureChoice().getDescription(player));
				archetypeChoice = ArchetypeChoice.OCCULTIST;
			}
		});
		TextButton pyromancerButton = new TextButton("Pyromancer", TextDungeon.skin);
		pyromancerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblArchetypeChoice.clear();
				lblArchetypeChoice.setText("PYROMANCER");
				lblArchetypeDescription.clear();
				lblArchetypeDescription.setText("Pyromancers maximize their destructive potential "
						+ "with their penchant for fire.\n" 
						+ "Signature Ability: " + pyromancer.getSignatureChoice().getName()
						+ "\n" + pyromancer.getSignatureChoice().getDescription(player));
				archetypeChoice = ArchetypeChoice.PYROMANCER;
			}
		});
		
		mageArchetypes.add(illusionistButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		mageArchetypes.row();
		mageArchetypes.add(occultistButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		mageArchetypes.row();
		mageArchetypes.add(pyromancerButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		archetypeStack.add(mageArchetypes);
		mageArchetypes.setVisible(false);
		
		TextButton assassinButton = new TextButton("Assassin", TextDungeon.skin);
		assassinButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblArchetypeChoice.clear();
				lblArchetypeChoice.setText("ASSASSIN");
				lblArchetypeDescription.clear();
				lblArchetypeDescription.setText("Assassins have abilities that help them deal "
						+ "big damage in a single attack.\n" 
						+ "Signature Ability: " + assassin.getSignatureChoice().getName()
						+ "\n" + assassin.getSignatureChoice().getDescription(player));
				archetypeChoice = ArchetypeChoice.ASSASSIN;
			}
		});
		TextButton burglarButton = new TextButton("Burglar", TextDungeon.skin);
		burglarButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblArchetypeChoice.clear();
				lblArchetypeChoice.setText("BURGLAR");
				lblArchetypeDescription.clear();
				lblArchetypeDescription.setText("Burglars are sneaky rogues that can more "
						+ "easily navigate the dangers of the dungeon.\n" 
						+ "Signature Ability: " + burglar.getSignatureChoice().getName()
						+ "\n" + burglar.getSignatureChoice().getDescription(player));
				archetypeChoice = ArchetypeChoice.BURGLAR;
			}
		});
		TextButton troubadourButton = new TextButton("Troubadour", TextDungeon.skin);
		troubadourButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblArchetypeChoice.clear();
				lblArchetypeChoice.setText("TROUBADOUR");
				lblArchetypeDescription.clear();
				lblArchetypeDescription.setText("Troubadours are fast-talking musicians "
						+ "who rely on buffing themselves and debuffing their enemies.\n" 
						+ "Signature Ability: " + troubadour.getSignatureChoice().getName()
						+ "\n" + troubadour.getSignatureChoice().getDescription(player));
				archetypeChoice = ArchetypeChoice.TROUBADOUR;
			}
		});
		
		rogueArchetypes.add(assassinButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		rogueArchetypes.row();
		rogueArchetypes.add(burglarButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		rogueArchetypes.row();
		rogueArchetypes.add(troubadourButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		archetypeStack.add(rogueArchetypes);
		rogueArchetypes.setVisible(false);
		
		TextButton berserkerButton = new TextButton("Berserker", TextDungeon.skin);
		berserkerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblArchetypeChoice.clear();
				lblArchetypeChoice.setText("BERSERKER");
				lblArchetypeDescription.clear();
				lblArchetypeDescription.setText("Berserkers enter a rage in the midst of battle,"
						+ "granting them extraordinary power and resilience.\n" 
						+ "Signature Ability: " + berserker.getSignatureChoice().getName()
						+ "\n" + berserker.getSignatureChoice().getDescription(player));
				archetypeChoice = ArchetypeChoice.BERSERKER;
			}
		});
		TextButton knightButton = new TextButton("Knight", TextDungeon.skin);
		knightButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblArchetypeChoice.clear();
				lblArchetypeChoice.setText("KNIGHT");
				lblArchetypeDescription.clear();
				lblArchetypeDescription.setText("Knights are holy warriors whose abilities "
						+ "focus more on defense, but their power shouldn't be underestimated.\n" 
						+ "Signature Ability: " + knight.getSignatureChoice().getName()
						+ "\n" + knight.getSignatureChoice().getDescription(player));
				archetypeChoice = ArchetypeChoice.KNIGHT;
			}
		});
		TextButton manAtArmsButton = new TextButton("Man At Arms", TextDungeon.skin);
		manAtArmsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lblArchetypeChoice.clear();
				lblArchetypeChoice.setText("MAN AT ARMS");
				lblArchetypeDescription.clear();
				lblArchetypeDescription.setText("Man-At-Arms are well-rounded warriors with "
						+ "a mastery of all weapons.\n" 
						+ "Signature Ability: " + manAtArms.getSignatureChoice().getName()
						+ "\n" + manAtArms.getSignatureChoice().getDescription(player));
				archetypeChoice = ArchetypeChoice.MANATARMS;
			}
		});
		
		warriorArchetypes.add(berserkerButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		warriorArchetypes.row();
		warriorArchetypes.add(knightButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		warriorArchetypes.row();
		warriorArchetypes.add(manAtArmsButton).width(BUTTON_WIDTH).padTop(BUTTON_PAD);
		archetypeStack.add(warriorArchetypes);
		warriorArchetypes.setVisible(false);
		
		archetypeTable.add(lblArchetypeTitle);
		archetypeTable.row();
		archetypeTable.add(archetypeStack);
		archetypeTable.row();
		archetypeTable.add(lblArchetypeChoice);
		archetypeTable.row();
		archetypeTable.add(lblArchetypeDescription).prefWidth(250f).top().left();
	}

	private void initMiscGroup() {
		Label lblName = new Label("Name:", TextDungeon.skin);
		TextField nameField = new TextField("", TextDungeon.skin);
		nameField.setText(getRandomName());
		TextButton randomNameButton = new TextButton("Random Name", TextDungeon.skin);
		randomNameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				nameField.setText(getRandomName());
			}
		});
		
		TextButton backButton = new TextButton("Back", TextDungeon.skin);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(ScreenKey.MAINMENU);
			}
		});
		
		Window confirmWindow = new Window("Confirm", TextDungeon.skin);
		confirmWindow.setMovable(false);
		TextButton yes = new TextButton("Yes", TextDungeon.skin);
		yes.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (kinChoice != null && adventurerChoice != null && archetypeChoice != null) {
					if (nameField.getText().isBlank())
						player.setName(getRandomName());
					else
						player.setName(nameField.getText());
					
					player.setKin(kinChoice);
					player.setAdventurer(adventurerChoice);
					player.setArchetype(archetypeChoice);
					
					clearDescriptionFields();
					resetChoices();
					
					confirmWindow.setVisible(false);
					game.setScreen(ScreenKey.GAME);
				} else {
					confirmWindow.setVisible(false);
				}
				
			}
		});
		TextButton no = new TextButton("No", TextDungeon.skin);
		no.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				confirmWindow.setVisible(false);
			}
		});
		
		confirmWindow.add(new Label("Are you sure you're ready?", TextDungeon.skin)).colspan(2);
		confirmWindow.row();
		confirmWindow.add(yes);
		confirmWindow.add(no);
		confirmWindow.pack();
		float newWidth = 250, newHeight = 100;
		confirmWindow.setBounds((stage.getWidth() - newWidth ) / 2, (stage.getHeight() - newHeight ) / 2, newWidth , newHeight );
		confirmWindow.setVisible(false);
		stage.addActor(confirmWindow);
		
		TextButton confirmButton = new TextButton("Confirm Character", TextDungeon.skin);
		confirmButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (kinChoice != null && adventurerChoice != null && archetypeChoice != null)
					confirmWindow.setVisible(true);
			}
		});
		
		miscTable.add(backButton).padRight(50f);
		miscTable.add(lblName);
		miscTable.add(nameField);
		miscTable.add(randomNameButton);
		miscTable.add(confirmButton).padLeft(50f);
		
	
	}
	
	protected void resetChoices() {
		kinChoice = null;
		adventurerChoice = null;
		archetypeChoice = null;
	}

	protected void clearDescriptionFields() {
		lblKinChoice.clear();
		lblKinDescription.clear();
		lblAdventurerChoice.clear();
		lblAdventurerDescription.clear();
		lblArchetypeChoice.clear();
		lblArchetypeDescription.clear();
	}

	private String getRandomName() {
		int mf = GameScreen.generateRandom(1, 2);
		if (mf == 1) {
			return getMaleName();
		} else {
			return getFemaleName();
		}
	}
	
	private String getMaleName() {
		int rand = GameScreen.generateRandom(1, 50);
		switch (rand) {
		case 1: return "Aeneas";
		case 2: return "Ajax";
		case 3: return "Alexander";
		case 4: return "Anton";
		case 5: return "Armando";
		case 6: return "Arthur";
		case 7: return "Brogan";
		case 8: return "Bruce";
		case 9: return "Ciaran";
		case 10: return "Deimos";
		case 11: return "Elden";
		case 12: return "Farhan";
		case 13: return "Feodor";
		case 14: return "Flavius";
		case 15: return "Gawain";
		case 16: return "Griffin";
		case 17: return "Gwyn";
		case 18: return "Haval";
		case 19: return "Isaiah";
		case 20: return "Jacques";
		case 21: return "Jasper";
		case 22: return "Kristian";
		case 23: return "Kristoff"; 
		case 24: return "Leif";
		case 25: return "Leopold";
		case 26: return "Lorenzo";
		case 27: return "Lucian";
		case 28: return "Lukas";
		case 29: return "Markus";
		case 30: return "Mathias";
		case 31: return "Mauricio";
		case 32: return "Nicholas";
		case 33: return "Olaf";
		case 34: return "Ormos";
		case 35: return "Pavel";
		case 36: return "Quentyn";
		case 37: return "Rafael";
		case 38: return "Ronan";
		case 39: return "Sebastian";
		case 40: return "Stefan";
		case 41: return "Tobias";
		case 42: return "Tristan";
		case 43: return "Troy";
		case 44: return "Ulysses";
		case 45: return "Victor";
		case 46: return "Vincent";
		case 47: return "Wesley";
		case 48: return "Xander";
		case 49: return "Ynyr";
		case 50: return "Zavad";
		default: return "Adventurer";
		}
	}
	
	private String getFemaleName() {
		int rand = GameScreen.generateRandom(1, 50);
		switch (rand) {
		case 1: return "Aela";
		case 2: return "Anisa";
		case 3: return "Aoife";
		case 4: return "Bronwen";
		case 5: return "Bruna";
		case 6: return "Celesta";
		case 7: return "Cerys";
		case 8: return "Claudia";
		case 9: return "Deia";
		case 10: return "Eden";
		case 11: return "Esther";
		case 12: return "Etsuko";
		case 13: return "Eve";
		case 14: return "Fiona";
		case 15: return "Gulizar";
		case 16: return "Hafsah";
		case 17: return "Haleema";
		case 18: return "Helen";
		case 19: return "Ida";
		case 20: return "Iqra";
		case 21: return "Isadora";
		case 22: return "Joan";
		case 23: return "Josefine"; 
		case 24: return "Katerina";
		case 25: return "Keira";
		case 26: return "Kyla";
		case 27: return "Lachlan";
		case 28: return "Lanora";
		case 29: return "Leona";
		case 30: return "Louvenia";
		case 31: return "Millicent";
		case 32: return "Nadia";
		case 33: return "Naomi";
		case 34: return "Orla";
		case 35: return "Petrina";
		case 36: return "Phillipa";
		case 37: return "Pippa";
		case 38: return "Qamra";
		case 39: return "Rosario";
		case 40: return "Sabine";
		case 41: return "Sapphire";
		case 42: return "Saskia";
		case 43: return "Sofia";
		case 44: return "Theokleia";
		case 45: return "Tryphena";
		case 46: return "Yessenia";
		case 47: return "Yevette";
		case 48: return "Zanna";
		case 49: return "Zara";
		case 50: return "Zaynab";
		default: return "Adventurer";
		}
	}
}
