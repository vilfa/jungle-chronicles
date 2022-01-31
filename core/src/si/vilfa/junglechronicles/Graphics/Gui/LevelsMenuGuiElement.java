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
 * @date 31/01/2022
 * @package si.vilfa.junglechronicles.Graphics.Gui
 **/
public class LevelsMenuGuiElement extends GuiElement
{
    private final Table actor;
    private final TextButton levelOneButton;
    private final TextButton levelTwoButton;
    private final TextButton levelThreeButton;

    public LevelsMenuGuiElement(Game game)
    {
        super(game);

        actor = new Table();

        levelOneButton = new TextButton("Level 1", skin);
        levelTwoButton = new TextButton("Level 2", skin);
        levelThreeButton = new TextButton("Level 3", skin);

        initializeElement();

        this.registerEventListener(MenuEvent.LEVEL_CHANGE_BUTTON_CLICK, game);
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

        levelOneButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("level 1 button clicked");
                dispatchEvent(MenuEvent.LEVEL_CHANGE_BUTTON_CLICK, 1);
            }
        });
        levelTwoButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("level 2 button clicked");
                dispatchEvent(MenuEvent.LEVEL_CHANGE_BUTTON_CLICK, 2);
            }
        });
        levelThreeButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("level 3 button clicked");
                dispatchEvent(MenuEvent.LEVEL_CHANGE_BUTTON_CLICK, 3);
            }
        });

        actor.add(levelOneButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(levelTwoButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(levelThreeButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
    }

    @Override
    public void update() { }

    @Override
    public void dispose()
    {
        super.dispose();
    }
}
