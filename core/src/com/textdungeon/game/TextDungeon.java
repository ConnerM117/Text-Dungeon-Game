package com.textdungeon.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import mobs.Player;

public class TextDungeon extends Game {
	
	public BitmapFont font;
	
	public static Skin skin;
	
	private MainMenuScreen mainMenu;
	private GameScreen gameScreen;
	private CharacterSelectScreen characterScreen;
	private TutorialScreen tutorialScreen;
	private final Player player = new Player();
	
	public enum ScreenKey { CHARACTER, GAME, MAINMENU, TUTORIAL }
	
	@Override
	public void create () {
		
		skin = new Skin(Gdx.files.internal("Skins/textSkin.json"));
		
		FileHandle atlasFile = Gdx.files.internal("Skins/textSkin.atlas"); //atlas
		skin.addRegions(new TextureAtlas(atlasFile));
		FileHandle fontFile = Gdx.files.internal("Skins/NovemberBitmapFont.fnt");
		font = new BitmapFont(fontFile);
		skin.add("default-font", font);
		
		mainMenu = new MainMenuScreen(this);
		gameScreen = new GameScreen(this, player);
		characterScreen = new CharacterSelectScreen(this, player);
		tutorialScreen = new TutorialScreen(this);
		
		this.setScreen(mainMenu);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		font.dispose();
		skin.dispose();
	}
	
	public void setScreen(ScreenKey screenKey) {
		switch (screenKey) {
		case CHARACTER:
			this.setScreen(characterScreen);
			break;
		case GAME:
			this.setScreen(gameScreen);
			break;
		case MAINMENU:
			this.setScreen(mainMenu);
			break;
		case TUTORIAL:
			this.setScreen(tutorialScreen);
			break;
		}
	}
	
}
