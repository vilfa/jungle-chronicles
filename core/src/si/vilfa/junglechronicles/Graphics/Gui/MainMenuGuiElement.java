package si.vilfa.junglechronicles.Graphics.Gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import si.vilfa.junglechronicles.Events.MenuEvent;
import si.vilfa.junglechronicles.Gameplay.Game;

/**
 * @author luka
 * @date 02/01/2022
 * @package si.vilfa.junglechronicles.Graphics.Gui
 **/
public class MainMenuGuiElement extends GuiElement
{
    private final Table actor;
    private final TextButton playButton;
    private final TextButton optionsButton;
    private final TextButton leaderboardButton;
    private final TextButton exitButton;

    public MainMenuGuiElement(Game game)
    {
        super(game);

        actor = new Table();

        playButton = new TextButton("Play", skin);
        optionsButton = new TextButton("Options", skin);
        leaderboardButton = new TextButton("Leaderboard", skin);
        exitButton = new TextButton("Exit", skin);

        initializeElement();

        this.registerEventListener(MenuEvent.PLAY_BUTTON_CLICK, game)
            .registerEventListener(MenuEvent.OPTIONS_BUTTON_CLICK, game)
            .registerEventListener(MenuEvent.LEADERBOARD_BUTTON_CLICK, game)
            .registerEventListener(MenuEvent.EXIT_BUTTON_CLICK, game);
    }

    @Override
    public Actor getActor()
    {
        return actor;
    }

    @Override
    protected void initializeElement()
    {
        actor.align(Align.center);
        actor.defaults().expandX().pad(12f);
        actor.setFillParent(true);

        playButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                dispatchEvent(MenuEvent.PLAY_BUTTON_CLICK);
                log("play button clicked");
            }
        });
        optionsButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                dispatchEvent(MenuEvent.OPTIONS_BUTTON_CLICK);
                log("options button clicked");
            }
        });
        leaderboardButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                dispatchEvent(MenuEvent.LEADERBOARD_BUTTON_CLICK);
                log("leaderboard button clicked");
            }
        });
        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                dispatchEvent(MenuEvent.EXIT_BUTTON_CLICK);
                log("exit button clicked");
            }
        });

        actor.add(playButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(optionsButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(leaderboardButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(exitButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
    }

    @Override
    public void update() { }

    @Override
    public void dispose()
    {
        super.dispose();
    }
}
