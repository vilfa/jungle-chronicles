package si.vilfa.junglechronicles.Graphics.Gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import si.vilfa.junglechronicles.Events.MenuEvent;
import si.vilfa.junglechronicles.Gameplay.Game;

/**
 * @author luka
 * @date 31/01/2022
 * @package si.vilfa.junglechronicles.Graphics.Gui
 **/
public class GameEndMenuGuiElement extends GuiElement
{
    private final BitmapFont fontBold;
    private final BitmapFont fontRegular;

    private final Table actor;

    private final Label playerDiedLabel;
    private final Label levelEndLabel;
    private final Label scoreLabel;

    private final TextButton replayLevelButton;
    private final TextButton mainMenuButton;
    private final TextButton nextLevelButton;

    public GameEndMenuGuiElement(Game game, BitmapFont boldFont, BitmapFont regularFont)
    {
        super(game);

        fontBold = boldFont;
        fontRegular = regularFont;

        actor = new Table();

        playerDiedLabel = new Label("You died!", skin);
        levelEndLabel = new Label("Level finished!", skin);
        scoreLabel = new Label("", skin);

        replayLevelButton = new TextButton("Play again", skin);
        mainMenuButton = new TextButton("Main menu", skin);
        nextLevelButton = new TextButton("Next level", skin);

        initializeElement();

        this.registerEventListener(MenuEvent.REPLAY_BUTTON_CLICK, game)
            .registerEventListener(MenuEvent.MAIN_MENU_BUTTON_CLICK, game)
            .registerEventListener(MenuEvent.NEXT_LEVEL_BUTTON_CLICK, game);
    }

    public void clear()
    {
        actor.clear();
    }

    public void setPlayerDied()
    {
        actor.add(playerDiedLabel).pad(10f, 0f, 10f, 0f).align(Align.center).row();
        actor.add(scoreLabel).pad(10f, 0f, 20f, 0f).align(Align.center).row();
        actor.add(replayLevelButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(mainMenuButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
    }

    public void setLevelEnd()
    {
        actor.add(levelEndLabel).pad(10f, 0f, 10f, 0f).align(Align.center).row();
        actor.add(scoreLabel).pad(10f, 0f, 20f, 0f).align(Align.center).row();
        actor.add(replayLevelButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(nextLevelButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(mainMenuButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
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

        Label.LabelStyle labelStyleBold = playerDiedLabel.getStyle();
        Label.LabelStyle labelStyleRegular = new Label.LabelStyle(labelStyleBold);

        labelStyleBold.font = fontBold;
        labelStyleRegular.font = fontRegular;

        playerDiedLabel.setStyle(labelStyleBold);
        levelEndLabel.setStyle(labelStyleBold);
        scoreLabel.setStyle(labelStyleRegular);

        replayLevelButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("replay button clicked");
                dispatchEvent(MenuEvent.REPLAY_BUTTON_CLICK);
            }
        });
        mainMenuButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("main menu button clicked");
                dispatchEvent(MenuEvent.MAIN_MENU_BUTTON_CLICK);
            }
        });
        nextLevelButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("next level button clicked");
                dispatchEvent(MenuEvent.NEXT_LEVEL_BUTTON_CLICK);
            }
        });
    }

    @Override
    public void update()
    {
        scoreLabel.setText(String.format("Score: %d\nTime: %.1fs",
                                         game.getPlayerScore(),
                                         game.getCurrentLevelDuration()));
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }
}
