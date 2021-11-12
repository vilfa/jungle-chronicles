package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public class Renderer extends DrawableGameComponent implements IScreenAdapter
{
	private static final float MIN_WORLD_WIDTH = 800.0f;
	private static final float MIN_WORLD_HEIGHT = 600.0f;
	private final Camera camera;
	private final Viewport viewport;
	private final GameTime gameTime;
	private final Level level;
	private final SpriteBatch spriteBatch;

	private Texture playerSprite;
	private Texture terrainBlockSprite;
	private Texture midgroundBlockSprite;
	private Texture goldCollectibleSprite;

	public Renderer(GameTime gameTime, Level level)
	{
		this(new OrthographicCamera(), gameTime, level);
	}

	public Renderer(Camera camera, GameTime gameTime, Level level)
	{
		this.initializeDrawable(0, true, 0, true);
		this.camera = camera;
		this.viewport = new ExtendViewport(MIN_WORLD_WIDTH, MIN_WORLD_HEIGHT, this.camera);
		this.gameTime = gameTime;
		this.level = level;
		this.spriteBatch = new SpriteBatch();
		this.initializeTextures();
	}

	private void initializeTextures()
	{
		this.playerSprite = new Texture(new FileHandle("assets/4k/Player/PlayerR1@4096x4096.png"),
		                                true);
		this.terrainBlockSprite = new Texture(
				new FileHandle("assets/4k/Terrain/TerrainTopCenter@4096x4096.png"), true);
		this.midgroundBlockSprite = new Texture(
				new FileHandle("assets/4k/Midground/MidgroundCenter@4096x4096.png"), true);
		this.goldCollectibleSprite = new Texture(
				new FileHandle("assets/4k/Gold/Gold6@4096x4096.png"), true);
	}

	@Override
	public Camera getCamera()
	{
		return camera;
	}

	@Override
	public Viewport getViewport()
	{
		return viewport;
	}

	@Override
	public void draw(GameTime gameTime)
	{
		ScreenUtils.clear(1, 1, 1, 1);
		spriteBatch.begin();
		for (Object item : level.getScene().getItems())
		{
			Vector2 position;
			if (item instanceof Player)
			{
				position = ((Player) item).getPosition();
				spriteBatch.draw(playerSprite, position.x, position.y);
			} else if (item instanceof TerrainBlock)
			{
				position = ((TerrainBlock) item).getPosition();
				spriteBatch.draw(terrainBlockSprite, position.x, position.y);
			} else if (item instanceof MidgroundBlock)
			{
				position = ((MidgroundBlock) item).getPosition();
				spriteBatch.draw(midgroundBlockSprite, position.x, position.y);
			} else if (item instanceof GoldCollectible)
			{
				position = ((GoldCollectible) item).getPosition();
				spriteBatch.draw(goldCollectibleSprite, position.x, position.y);
			}
		}
		spriteBatch.end();
	}

	@Override
	public void dispose()
	{
		level.dispose();
		spriteBatch.dispose();
	}

	@Override
	public void update(GameTime gameTime)
	{

	}
}
