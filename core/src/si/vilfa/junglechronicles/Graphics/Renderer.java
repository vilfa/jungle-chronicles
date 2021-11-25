package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Player.Human.Player;
import si.vilfa.junglechronicles.Scene.Levels.Level;
import si.vilfa.junglechronicles.Scene.Objects.GoldCollectible;
import si.vilfa.junglechronicles.Scene.Objects.MidgroundBlock;
import si.vilfa.junglechronicles.Scene.Objects.TerrainBlock;

import java.util.ArrayList;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public class Renderer extends DrawableGameComponent implements WindowAdapter
{
	public static GameTime gameTime;
	private final int screenRefreshRate;
	private final Viewport viewport;
	private final Level level;
	private final SpriteBatch spriteBatch;
	private float WORLD_WIDTH = 100.0f;
	private float WORLD_HEIGHT = 100.0f;
	private int screenWidth;
	private int screenHeight;
	private float aspectRatio;
	private ArrayList<Sprite> worldBackgroundSprites;
	private Sprite playerSprite;
	private Sprite terrainBlockSprite;
	private Sprite midgroundBlockSprite;
	private Sprite goldCollectibleSprite;

	// TODO Remove this after no longer needed for debugging.
	private float timer = 0f;

	public Renderer(Level level)
	{
		super();
		Renderer.gameTime = new GameTime();

		this.initializeDrawable(0, true, 0, true);
		this.level = level;
		this.spriteBatch = new SpriteBatch();
		this.initializeSprites();

		Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(
				Gdx.graphics.getPrimaryMonitor());
		this.screenWidth = displayMode.width;
		this.screenHeight = displayMode.height;
		this.screenRefreshRate = displayMode.refreshRate;
		this.aspectRatio = (float) this.screenWidth / (float) this.screenHeight;
		this.WORLD_WIDTH *= aspectRatio;

		this.viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
		this.viewport.apply(true);
	}

	private void initializeSprites()
	{
		// TODO Make these sprites scene objects and draw them by draw order.
		worldBackgroundSprites = new ArrayList<>();
		worldBackgroundSprites.add(
				new Sprite(new Texture("World@2x/WorldForestBackground@2x.png")));
		worldBackgroundSprites.add(
				new Sprite(new Texture("World@2x/WorldForestShadowsNight@2x.png")));
		worldBackgroundSprites.add(new Sprite(new Texture("World@2x/WorldForestNight@2x.png")));
		worldBackgroundSprites.add(new Sprite(new Texture("World@2x/WorldForestTreetops@2x.png")));
		worldBackgroundSprites.add(new Sprite(new Texture("World@2x/WorldGroundGrass@2x.png")));
		worldBackgroundSprites.add(new Sprite(new Texture("World@2x/WorldGroundBushes@2x.png")));

		playerSprite = new Sprite(new Texture("128/Player/PlayerR1@128x128.png"));
		terrainBlockSprite = new Sprite(new Texture(
				"128/Terrain/TerrainTopCenter@128x128.png"));
		midgroundBlockSprite = new Sprite(new Texture(
				"128/Midground/MidgroundCenter@128x128.png"));
		goldCollectibleSprite = new Sprite(new Texture(
				"128/Gold/Gold6@128x128.png"));
	}

	@Override
	public void resize(int width, int height)
	{
		screenWidth = width;
		screenHeight = height;
		aspectRatio = (float) screenWidth / (float) screenHeight;
		viewport.update(screenWidth, screenHeight, true);
		log("Resize: width=" + width + ", height=" + height);
	}

	@Override
	public float getAspectRatio()
	{
		return aspectRatio;
	}

	@Override
	public void setAspectRatio(float aspectRatio)
	{
		this.aspectRatio = aspectRatio;
	}

	@Override
	public void draw()
	{
		if (!isDrawable) {return;}
		// TODO Make draw calls use draw order.
		ScreenUtils.clear(1, 1, 1, 1);
		spriteBatch.begin();
		for (Sprite sprite : worldBackgroundSprites)
		{
			sprite.setPosition(0, 0);
			sprite.draw(spriteBatch);
		}
		for (Object item : level.getItems())
		{
			Vector2 position;
			if (item instanceof Player)
			{
				position = ((Player) item).getPosition();
				playerSprite.setPosition(position.x, position.y);
				playerSprite.draw(spriteBatch);
			} else if (item instanceof TerrainBlock)
			{
				position = ((TerrainBlock) item).getPosition();
				terrainBlockSprite.setPosition(position.x, position.y);
				terrainBlockSprite.draw(spriteBatch);
			} else if (item instanceof MidgroundBlock)
			{
				position = ((MidgroundBlock) item).getPosition();
				midgroundBlockSprite.setPosition(position.x, position.y);
				midgroundBlockSprite.draw(spriteBatch);
			} else if (item instanceof GoldCollectible)
			{
				position = ((GoldCollectible) item).getPosition();
				goldCollectibleSprite.setPosition(position.x, position.y);
				goldCollectibleSprite.draw(spriteBatch);
			}
		}
		spriteBatch.end();
	}

	@Override
	public void dispose()
	{
		level.dispose();

		for (Sprite sprite : worldBackgroundSprites)
		{
			sprite.getTexture().dispose();
		}
		playerSprite.getTexture().dispose();
		terrainBlockSprite.getTexture().dispose();
		midgroundBlockSprite.getTexture().dispose();
		goldCollectibleSprite.getTexture().dispose();

		spriteBatch.dispose();
	}

	@Override
	public void update()
	{
		if (!isUpdatable) {return;}
		// TODO Remove this after no longer needed for debugging.
		timer += Renderer.gameTime.getDeltaTime();
		if (timer > 1f)
		{
			log("FPS:" + Gdx.graphics.getFramesPerSecond());
			timer = 0;
		}
	}
}
