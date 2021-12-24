package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Level.Objects.GameBlock;
import si.vilfa.junglechronicles.Player.AI.Enemy;
import si.vilfa.junglechronicles.Player.AI.Friend;
import si.vilfa.junglechronicles.Player.Player;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public class SimpleScene extends GameComponent implements Scene
{
    protected final Array<GameBlock> objects;
    protected final Array<SceneTile> tiles;
    protected final Array<Player> players;
    protected final Array<BackgroundSceneTile> backgrounds;
    protected final Array<GuiSceneTile> guiTiles;

    public SimpleScene()
    {
        super(0, true);
        objects = new Array<>();
        tiles = new Array<>();
        players = new Array<>();
        backgrounds = new Array<>();
        guiTiles = new Array<>();
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;

        for (GameBlock object : objects)
        {
            object.update();
        }
        for (SceneTile tile : tiles)
        {
            tile.update();
        }
        for (Player player : players)
        {
            player.update();
        }
        for (BackgroundSceneTile background : backgrounds)
        {
            background.update();
        }
        for (GuiSceneTile guiTile : guiTiles)
        {
            guiTile.update();
        }
    }

    @Override
    public void dispose()
    {
        objects.forEach(GameBlock::dispose);
        tiles.forEach(SceneTile::dispose);
        players.forEach(Player::dispose);
        backgrounds.forEach(BackgroundSceneTile::dispose);
        guiTiles.forEach(GuiSceneTile::dispose);
    }

    @Override
    public void addItem(Object item)
    {
        if (item instanceof GameBlock)
        {
            objects.add((GameBlock) item);
        } else if (item instanceof BackgroundSceneTile)
        {
            backgrounds.add((BackgroundSceneTile) item);
        } else if (item instanceof SceneTile)
        {
            tiles.add((SceneTile) item);
        } else if (item instanceof Player)
        {
            players.add((Player) item);
        } else if (item instanceof GuiSceneTile)
        {
            guiTiles.add((GuiSceneTile) item);
        } else
        {
            log("Trying to add unknown item:" + item);
        }
    }

    @Override
    public void removeItem(Object item)
    {
        if (item instanceof GameBlock)
        {
            objects.removeValue((GameBlock) item, false);
        } else if (item instanceof BackgroundSceneTile)
        {
            backgrounds.removeValue((BackgroundSceneTile) item, false);
        } else if (item instanceof SceneTile)
        {
            tiles.removeValue((SceneTile) item, false);
        } else if (item instanceof Player)
        {
            players.removeValue((Player) item, false);
        } else if (item instanceof GuiSceneTile)
        {
            guiTiles.removeValue((GuiSceneTile) item, false);
        } else
        {
            log("Trying to remove unknown item:" + item);
        }
    }

    @Override
    public Array<GameBlock> getObjects()
    {
        return objects;
    }

    @Override
    public Array<SceneTile> getTiles()
    {
        return tiles;
    }

    @Override
    public Array<Player> getPlayers()
    {
        return players;
    }

    @Override
    public Array<BackgroundSceneTile> getBackgrounds()
    {
        return backgrounds;
    }

    @Override
    public Array<Enemy> getEnemies()
    {
        Array<Enemy> enemies = new Array<>();
        for (Player player : players)
        {
            if (player instanceof Enemy) enemies.add((Enemy) player);
        }
        return enemies;
    }

    @Override
    public Array<Friend> getFriends()
    {
        Array<Friend> friends = new Array<>();
        for (Player player : players)
        {
            if (player instanceof Friend) friends.add((Friend) player);
        }
        return friends;
    }

    @Override
    public Array<GuiSceneTile> getGuiTiles()
    {
        return guiTiles;
    }

    @Override
    public void clear()
    {
        objects.clear();
        tiles.clear();
    }
}
