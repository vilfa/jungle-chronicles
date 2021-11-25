package si.vilfa.junglechronicles.Scene.Levels;

import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Scene.Scene;
import si.vilfa.junglechronicles.Scene.SimpleScene;

import java.util.ArrayList;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Scene.Levels
 **/
public abstract class GameLevel extends GameComponent implements Scene
{
	protected SimpleScene scene;
	protected Vector2 playerSpawn;
	protected ArrayList<Vector2> collectiblesSpawn;

	public GameLevel(SimpleScene scene, Vector2 playerSpawn)
	{
		super();
		this.initialize(0, true);
		this.scene = scene;
		this.playerSpawn = playerSpawn;
		this.collectiblesSpawn = new ArrayList<>();
	}

	@Override
	public abstract void update();

	@Override
	public abstract void dispose();

	@Override
	public void addItem(Object item)
	{
		scene.addItem(item);
	}

	@Override
	public void removeItem(Object item)
	{
		scene.removeItem(item);
	}

	@Override
	public ArrayList<Object> getItems()
	{
		return scene.getItems();
	}

	@Override
	public void clear()
	{
		scene.clear();
	}
}
