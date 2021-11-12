package si.vilfa.junglechronicles.Scene.Levels;

import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Graphics.GameTime;
import si.vilfa.junglechronicles.Scene.SimpleScene;

import java.util.ArrayList;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Scene.Levels
 **/
public abstract class GameLevel extends GameComponent
{
	protected SimpleScene scene;
	protected Vector2 playerSpawn;
	protected ArrayList<Vector2> collectiblesSpawn;

	public GameLevel(SimpleScene scene, Vector2 playerSpawn)
	{
		this.scene = scene;
		this.playerSpawn = playerSpawn;
		this.collectiblesSpawn = new ArrayList<>();
	}

	@Override
	public abstract void update(GameTime gameTime);

	public SimpleScene getScene()
	{
		return this.scene;
	}
}
