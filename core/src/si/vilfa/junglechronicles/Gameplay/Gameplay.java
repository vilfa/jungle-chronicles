package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Component.Disposable;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Graphics.WindowAdapter;
import si.vilfa.junglechronicles.Input.Events.*;
import si.vilfa.junglechronicles.Input.Processors.GameplayInputProcessor;
import si.vilfa.junglechronicles.Input.Processors.PlayerInputProcessor;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.Human.Player;
import si.vilfa.junglechronicles.Scene.Levels.Level;
import si.vilfa.junglechronicles.Scene.Objects.TerrainBlock;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public class Gameplay extends DrawableGameComponent implements Disposable, WindowAdapter, InputEventSubscriber
{
	private final Renderer renderer;
	private final Player player;
	private final InputMultiplexer inputMultiplexer;
	private final PhysicsEngine physics;
	private final Level level;

	public Gameplay()
	{
		super();
		this.initializeDrawable(0, true, 0, true);
		this.level = new Level();
		this.physics = new PhysicsEngine();
		this.player = new Player();
		this.renderer = new Renderer(this.level);
		this.inputMultiplexer = new InputMultiplexer();
		this.inputMultiplexer.addProcessor(new PlayerInputProcessor(this.player));
		this.inputMultiplexer.addProcessor(new GameplayInputProcessor(this));

		this.level.addItem(player);
		this.level.addItem(new TerrainBlock(new Vector2(128, 256), new Vector2(100, 0),
		                                    Float.POSITIVE_INFINITY));
		this.level.addItem(new TerrainBlock(new Vector2(256, 256), new Vector2(100, 0),
		                                    Float.POSITIVE_INFINITY));
		Gdx.input.setInputProcessor(this.inputMultiplexer);
	}

	@Override
	public void draw()
	{
		if (!isDrawable) {return;}
		renderer.draw();
	}

	@Override
	public void dispose()
	{
		log("Called dispose");
		level.dispose();
		player.dispose();
		physics.dispose();
		renderer.dispose();
	}

	@Override
	public void update()
	{
		if (!isUpdatable) {return;}
		player.update();
		level.update();
		renderer.update();
	}

	@Override
	public void resize(int width, int height)
	{
		renderer.resize(width, height);
	}

	@Override
	public float getAspectRatio()
	{
		return renderer.getAspectRatio();
	}

	@Override
	public void setAspectRatio(float aspectRatio)
	{
		renderer.setAspectRatio(aspectRatio);
	}

	@Override
	public void handleKeyDown(KeyDownInputEvent event)
	{
	}

	@Override
	public void handleKeyUp(KeyUpInputEvent event)
	{
		if (event.getKeyCode() == Input.Keys.ESCAPE)
		{
			System.exit(0);
		}
	}

	@Override
	public void handleKeyTyped(KeyTypedInputEvent event)
	{
	}

	@Override
	public void handleTouchDown(TouchDownInputEvent event)
	{
	}

	@Override
	public void handleTouchUp(TouchUpInputEvent event)
	{
	}

	@Override
	public void handleTouchDragged(TouchDraggedInputEvent event)
	{
	}

	@Override
	public void handleMouseMoved(MouseMovedInputEvent event)
	{
	}

	@Override
	public void handleScrolled(ScrolledInputEvent event)
	{
	}
}
