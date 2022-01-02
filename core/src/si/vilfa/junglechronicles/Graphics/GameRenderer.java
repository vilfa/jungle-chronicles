package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Gameplay.Game;
import si.vilfa.junglechronicles.Level.Scene.BackgroundSceneTile;
import si.vilfa.junglechronicles.Level.Scene.PlayerSceneTile;
import si.vilfa.junglechronicles.Level.Scene.SceneTile;
import si.vilfa.junglechronicles.Player.AI.Enemy;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;
import si.vilfa.junglechronicles.Player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public class GameRenderer extends Renderer implements EventListener
{
    private final HashMap<HumanPlayer.State, PlayerSceneTile> playerAnimations = new HashMap<>();
    private final HashMap<Enemy.EnemySprite, PlayerSceneTile> enemyAnimations = new HashMap<>();

    private float timer = 0f;

    public GameRenderer(Game game)
    {
        super(game);

        initializePlayerAnimations();
        initializeEnemyAnimations();

        //        gameState.getCurrentLevel().getBackgrounds().first().setViewport(viewport);
    }

    private void initializePlayerAnimations()
    {
        HashMap<HumanPlayer.State, TextureAtlas> texturesByState = new HashMap<>();
        texturesByState.put(HumanPlayer.State.JUMP_LEFT,
                            new TextureAtlas("Graphics/Characters/AdventurerJump.atlas"));
        texturesByState.put(HumanPlayer.State.JUMP_RIGHT,
                            texturesByState.get(HumanPlayer.State.JUMP_LEFT));
        texturesByState.put(HumanPlayer.State.IDLE_LEFT,
                            new TextureAtlas("Graphics/Characters/AdventurerIdle.atlas"));
        texturesByState.put(HumanPlayer.State.IDLE_RIGHT,
                            texturesByState.get(HumanPlayer.State.IDLE_LEFT));
        texturesByState.put(HumanPlayer.State.RUN_LEFT,
                            new TextureAtlas("Graphics/Characters/AdventurerRun.atlas"));
        texturesByState.put(HumanPlayer.State.RUN_RIGHT,
                            texturesByState.get(HumanPlayer.State.RUN_LEFT));
        texturesByState.put(HumanPlayer.State.SLIDE_LEFT,
                            new TextureAtlas("Graphics/Characters/AdventurerSlide.atlas"));
        texturesByState.put(HumanPlayer.State.SLIDE_RIGHT,
                            texturesByState.get(HumanPlayer.State.SLIDE_LEFT));

        Vector2 playerScale = new Vector2(1f, 1f);
        if (game.getPlayer() != null)
        {
            Vector2 playerBox = game.getPlayer().getBox();
            TextureRegion playerRegion = texturesByState.get(HumanPlayer.State.IDLE_LEFT)
                                                        .getRegions()
                                                        .first();
            playerScale.set(playerBox.x / playerRegion.getRegionWidth(),
                            playerBox.y / playerRegion.getRegionHeight());
        }

        for (Map.Entry<HumanPlayer.State, TextureAtlas> entry : texturesByState.entrySet())
        {
            int i = 0;
            TreeMap<Integer, TextureAtlas.AtlasRegion> animationSpec = new TreeMap<>();
            for (TextureAtlas.AtlasRegion region : entry.getValue().getRegions())
            {
                animationSpec.put(i++, region);
            }

            playerAnimations.put(entry.getKey(),
                                 new PlayerSceneTile(animationSpec,
                                                     Animation.PlayMode.LOOP,
                                                     1 / 10f,
                                                     HumanPlayer.State.getLeft()
                                                                      .contains(entry.getKey(),
                                                                                false),
                                                     false,
                                                     playerScale));
        }
    }

    private void initializeEnemyAnimations()
    {
        HashMap<Enemy.EnemySprite, TextureAtlas> texturesByEnemies = new HashMap<>();
        texturesByEnemies.put(Enemy.EnemySprite.MONSTER_ONE,
                              new TextureAtlas("Graphics/Enemies/Monster1.atlas"));
        texturesByEnemies.put(Enemy.EnemySprite.MONSTER_TWO,
                              new TextureAtlas("Graphics/Enemies/Monster2.atlas"));
        texturesByEnemies.put(Enemy.EnemySprite.MONSTER_THREE,
                              new TextureAtlas("Graphics/Enemies/Monster3.atlas"));

        Vector2 enemyScale = new Vector2(1f, 1f);
        if (game.getCurrentLevel().getEnemies().size > 0)
        {
            Vector2 enemyBox = game.getCurrentLevel().getEnemies().first().getBox();
            TextureRegion enemyRegion = texturesByEnemies.get(Enemy.EnemySprite.MONSTER_ONE)
                                                         .getRegions()
                                                         .first();
            enemyScale.set(enemyBox.x / enemyRegion.getRegionWidth(),
                           enemyBox.y / enemyRegion.getRegionHeight());
        }

        for (Map.Entry<Enemy.EnemySprite, TextureAtlas> entry : texturesByEnemies.entrySet())
        {
            int i = 0;
            TreeMap<Integer, TextureAtlas.AtlasRegion> animationSpec = new TreeMap<>();
            for (TextureAtlas.AtlasRegion region : entry.getValue().getRegions())
            {
                animationSpec.put(i++, region);
            }

            enemyAnimations.put(entry.getKey(),
                                new PlayerSceneTile(animationSpec,
                                                    Animation.PlayMode.LOOP,
                                                    1 / 10f,
                                                    false,
                                                    false,
                                                    enemyScale));
        }
    }

    private void followPlayer(Player player)
    {
        Vector2 newCameraPos = new Vector2(player.getPosition());
        if (newCameraPos.x < viewport.getWorldWidth() / 2f)
        {
            newCameraPos.x = viewport.getWorldWidth() / 2f;
        }
        if (newCameraPos.y < viewport.getWorldHeight() / 2f)
        {
            newCameraPos.y = viewport.getWorldHeight() / 2f;
        }
        viewport.getCamera().position.set(newCameraPos, 0f);
        viewport.getCamera().update();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        followPlayer(game.getPlayer());
    }

    @Override
    public void draw()
    {
        if (!isDrawable) return;

        // TODO: 20/11/2021 Make draw calls use draw order.
        ScreenUtils.clear(1, 1, 1, 1);
        spriteBatch.setProjectionMatrix(getCombined());
        spriteBatch.begin();

        for (BackgroundSceneTile tile : game.getCurrentLevel().getBackgrounds())
        {
            // TODO: 17/12/2021 Implement parallax background scrolling

            //            Vector2 parallax = player.getLinearVelocity();
            //            parallax.scl(-.15f * Renderer.gameTime.getDeltaTime());
            tile.draw(spriteBatch);
        }

        for (SceneTile tile : game.getCurrentLevel().getTiles())
        {
            tile.draw(spriteBatch);
        }

        for (Player player : game.getCurrentLevel().getPlayers())
        {
            if (player instanceof Enemy)
            {
                Enemy enemy = (Enemy) player;
                PlayerSceneTile enemyAnimation = enemyAnimations.get(enemy.getEnemySprite());
                enemyAnimation.setCenter(enemy.getPosition());
                enemyAnimation.draw(spriteBatch);
            }
        }

        HumanPlayer player = game.getPlayer();
        PlayerSceneTile playerAnimation = playerAnimations.get(player.getState());
        playerAnimation.setCenter(player.getPosition());
        playerAnimation.draw(spriteBatch);

        spriteBatch.end();
    }

    @Override
    public void handleEvent(Event event) { }

    @Override
    public void dispose()
    {
        super.dispose();
    }

    @Override
    public void update()
    {
        if (!isUpdatable || game.isPaused()) return;

        timer += gameTime.getDeltaTime();
        if (timer > 1f)
        {
            log("fps:" + Gdx.graphics.getFramesPerSecond());
            timer = 0f;
        }

        enemyAnimations.forEach((k, v) -> v.update());
        playerAnimations.get(game.getPlayer().getState()).update();

        followPlayer(game.getPlayer());
    }
}
