package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import si.vilfa.junglechronicles.Graphics.GameTime;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Input.Processors.PlayerInputProcessor;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.Human.Player;
import si.vilfa.junglechronicles.Scene.Levels.Level;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public class Gameplay
{
	private GameTime gameTime;
	private Renderer renderer;
	private Player player;
	private InputMultiplexer inputMultiplexer;
	private PhysicsEngine physics;
	private Level level;

	public Gameplay()
	{
		this.gameTime = new GameTime();
		this.renderer = new Renderer(this.gameTime);
		this.player = new Player();
		this.inputMultiplexer = new InputMultiplexer(new PlayerInputProcessor(this.player));
		this.physics = new PhysicsEngine();
		this.level = new Level();

		Gdx.input.setInputProcessor(this.inputMultiplexer);
	}
}
