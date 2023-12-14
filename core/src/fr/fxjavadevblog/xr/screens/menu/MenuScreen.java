package fr.fxjavadevblog.xr.screens.menu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.displays.Blinker;
import fr.fxjavadevblog.xr.commons.displays.Interpolator;
import fr.fxjavadevblog.xr.commons.fonts.bitmap.FontUtils;
import fr.fxjavadevblog.xr.commons.fonts.bitmap.GdxBitmapString;
import fr.fxjavadevblog.xr.commons.fonts.ttf.GdxTrueTypeString;
import fr.fxjavadevblog.xr.commons.fonts.ttf.TrueTypeFont;
import fr.fxjavadevblog.xr.commons.libs.AssetLib;
import fr.fxjavadevblog.xr.commons.libs.ModAsset;
import fr.fxjavadevblog.xr.commons.libs.SoundAsset;
import fr.fxjavadevblog.xr.commons.libs.TextureAsset;
import fr.fxjavadevblog.xr.commons.utils.GdxCommons;
import fr.fxjavadevblog.xr.commons.utils.ModPlayer;
import fr.fxjavadevblog.xr.commons.utils.RandomUtils;
import fr.fxjavadevblog.xr.screens.AbstractScreen;
import fr.fxjavadevblog.xr.screens.XenonControler;
import fr.fxjavadevblog.xr.screens.XenonScreen;

public class MenuScreen extends AbstractScreen
{
	private static final String MSG = "PRESS SPACE BAR";
	private static Log log = LogFactory.getLog(MenuScreen.class);

	private BackgroundTravelling backgroundTravelling;

	private Blinker msgBlinker;
	private Interpolator interpolatorX;
	private Interpolator interpolatorY;

	private Texture titleTexture;
	private Texture upperLogo;
	
	private float titleX;
	private float titleY;
	
	private float gbX;
	private float gbY;

	private Monitor monitor;
	private DisplayMode currentMode;
	private GdxTrueTypeString message;

	private ModPlayer modPlayer;
	private int currentMusic;
	private GdxBitmapString pressSpaceBarMessage;

	public MenuScreen(XenonControler controler, SpriteBatch batch)
	{
		super(controler, batch);
		log.info("Instanciation de MenuScreen");
		backgroundTravelling = new BackgroundTravelling();
		
		titleTexture = TextureAsset.TITLE.get();
		titleX = GdxCommons.calculateCenteredPositionX(titleTexture);
		titleY = GdxCommons.calculateCenteredPositionY(titleTexture);
		
		upperLogo = TextureAsset.UPPER_LOGO.get();
		gbX = GdxCommons.calculateCenteredPositionX(upperLogo);
		gbY = titleY + titleTexture.getHeight() + 30;
		
		monitor = Gdx.graphics.getMonitor();
		currentMode = Gdx.graphics.getDisplayMode(monitor);
		message = new GdxTrueTypeString(TrueTypeFont.COMPUTER_30_WHITE.getFont(), "");
		this.createBlinkingMessage();
		modPlayer = ModPlayer.getInstance();
		currentMusic = RandomUtils.pickIndex(ModAsset.values());
		log.info("Instanciation de MenuScreen OK");
	}

	private void createBlinkingMessage()
	{
		pressSpaceBarMessage = new GdxBitmapString(FontUtils.FONT_XENON, MSG, 1.5f);
		interpolatorX = new Interpolator(Interpolation.sine, 1f, 5, (Global.SCREEN_WIDTH - pressSpaceBarMessage.getWidth()) / 2f);
		interpolatorY = new Interpolator(Interpolation.pow2, 0.5f, 10, (float) (Global.SCREEN_HEIGHT - titleTexture.getHeight()) / 2 - 50);
		pressSpaceBarMessage.setPosition(interpolatorX.getOriginalValue(), interpolatorY.getOriginalValue());
		msgBlinker = new Blinker(0.15f, pressSpaceBarMessage);
	}

	@Override
	public void show()
	{
		modPlayer.playLoop(ModAsset.values()[currentMusic].toString());
	}

	@Override
	public void hide()
	{
		modPlayer.stop();
	}

	@Override
	public void render(float deltaTime)
	{
		this.checkInput();
		this.getBatch().begin();
		this.backgroundTravelling.translateBackGround(deltaTime);
		this.backgroundTravelling.drawBackGround(this.getBatch());
		this.drawUpperLogo();
		this.drawTitle();
		this.drawDisplayMode();
		this.drawBlinkingMessage(deltaTime);
		this.getBatch().end();
	}

	private void drawUpperLogo()
	{
		this.getBatch().draw(upperLogo, gbX, gbY);
	}

	private void drawBlinkingMessage(float deltaTime)
	{
		pressSpaceBarMessage.setPosition(interpolatorX.calculate(deltaTime), interpolatorY.calculate(deltaTime));
		this.msgBlinker.render(this.getBatch(), deltaTime);
	}

	private void drawDisplayMode()
	{
		String msgDisplayMode = String.format("%s / %s / %d FPS", currentMode, monitor.name, Gdx.graphics.getFramesPerSecond());
		message.setText(msgDisplayMode);
		message.draw(this.getBatch(), (Global.SCREEN_WIDTH - message.getWidth()) / 2f, 60);
		message.setText("< " + modPlayer.getMusicName() + " >");
		message.draw(this.getBatch(), (Global.SCREEN_WIDTH - message.getWidth()) / 2f, 120);
		message.setText(String.format("Virtual Width : %d px / Virtual Height : %d px", Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT));
		message.draw(this.getBatch(), (Global.SCREEN_WIDTH - message.getWidth()) / 2f, 30);
	}

	private void drawTitle()
	{
		SpriteBatch batch = this.getBatch();
		batch.draw(titleTexture, titleX, titleY);
	}

	private void checkInput()
	{
		checkNextScreen();
		checkMetaKeys();
		checkMusicSwitcher();
	}

	private void checkNextScreen()
	{
		if (Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			SoundAsset.CLIC.play();
			this.getControler().showScreen(XenonScreen.GAME_PLAY);
		}
	}

	private void checkMetaKeys()
	{
		if (Gdx.input.isKeyJustPressed(Keys.F1))
		{
			GdxCommons.switchFullScreen();
		}

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			AssetLib.getInstance().disposeAll();
			Gdx.app.exit();
		}
	}

	private void checkMusicSwitcher()
	{
		if (Gdx.input.isKeyJustPressed(Keys.LEFT) && currentMusic > 0)
		{
			currentMusic--;
			updateMusic();
		}

		if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && currentMusic < ModAsset.values().length - 1)
		{
			currentMusic++;
			updateMusic();
		}
	}

	private void updateMusic()
	{
		modPlayer.stop();
		modPlayer.playLoop(ModAsset.values()[currentMusic].toString());
	}
}
