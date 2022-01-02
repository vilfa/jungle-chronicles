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
public class OptionsMenuGuiElement extends GuiElement
{
    private final Table actor;
    private final TextButton resumeButton;
    private final TextButton optionsButton;
    private final TextButton exitButton;

    public OptionsMenuGuiElement(Game game)
    {
        super(game);

        actor = new Table();

        resumeButton = new TextButton("Resume", skin);
        optionsButton = new TextButton("Options", skin);
        exitButton = new TextButton("Exit", skin);

        initializeElement();

        this.registerEventListener(MenuEvent.RESUME_BUTTON_CLICK, game)
            .registerEventListener(MenuEvent.OPTIONS_BUTTON_CLICK, game)
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

        resumeButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("resume button clicked");
                dispatchEvent(MenuEvent.RESUME_BUTTON_CLICK);
            }
        });
        optionsButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("options button clicked");
                dispatchEvent(MenuEvent.OPTIONS_BUTTON_CLICK);
            }
        });
        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("exit button clicked");
                dispatchEvent(MenuEvent.EXIT_BUTTON_CLICK);
            }
        });

        actor.add(resumeButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(optionsButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(exitButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
    }

    @Override
    public void update() { }

    @Override
    public void dispose() { }
}
