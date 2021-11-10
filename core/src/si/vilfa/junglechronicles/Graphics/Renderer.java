package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public class Renderer implements IScreenAdapter
{
	private static final float MIN_WORLD_WIDTH = 800.0f;
	private static final float MIN_WORLD_HEIGHT = 600.0f;

	private final Camera camera;
	private final Viewport viewport;
	private final GameTime gameTime;

	public Renderer(GameTime gameTime)
	{
		this(new OrthographicCamera(), new ExtendViewport(MIN_WORLD_WIDTH, MIN_WORLD_HEIGHT), gameTime);
	}

	public Renderer(Camera camera, Viewport viewport, GameTime gameTime)
	{
		this.camera = camera;
		this.viewport = viewport;
		this.gameTime = gameTime;
	}

	public static float getMinWorldWidth()
	{
		return Renderer.MIN_WORLD_WIDTH;
	}

	public static float getMinWorldHeight()
	{
		return Renderer.MIN_WORLD_HEIGHT;
	}

	@Override
	public Camera getCamera()
	{
		return this.camera;
	}

	@Override
	public Viewport getViewport()
	{
		return this.viewport;
	}
}
