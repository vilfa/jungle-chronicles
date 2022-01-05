package si.vilfa.junglechronicles.Graphics.Gui;

import com.badlogic.gdx.Gdx;
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
    private final TextButton soundButton;
    private final TextButton musicButton;
    private final TextButton resolutionButton;

    public OptionsMenuGuiElement(Game game)
    {
        super(game);

        actor = new Table();

        soundButton = new TextButton("", skin);
        musicButton = new TextButton("", skin);
        resolutionButton = new TextButton("", skin);

        initializeElement();

        this.registerEventListener(MenuEvent.SOUND_BUTTON_CLICK, game.getAudio())
            .registerEventListener(MenuEvent.MUSIC_BUTTON_CLICK, game.getAudio())
            .registerEventListener(MenuEvent.RESOLUTION_BUTTON_CLICK, game);
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

        soundButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("sound button clicked");
                dispatchEvent(MenuEvent.SOUND_BUTTON_CLICK);
            }
        });
        musicButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("music button clicked");
                dispatchEvent(MenuEvent.MUSIC_BUTTON_CLICK);
            }
        });
        resolutionButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);
                log("resolution button clicked");
                dispatchEvent(MenuEvent.RESOLUTION_BUTTON_CLICK);
            }
        });

        actor.add(soundButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(musicButton).pad(10f, 0f, 10f, 0f).width(200f).height(50f).row();
        actor.add(resolutionButton).pad(10f, 0f, 10f, 0f).width(300f).height(50f).row();

        update();
    }

    @Override
    public void update()
    {
        soundButton.getLabel()
                   .setText(String.format("Sound: %s",
                                          (game.getAudio().getSoundVolume() > 0f) ? "On" : "Off"));
        musicButton.getLabel()
                   .setText(String.format("Music: %s",
                                          (game.getAudio().getMusicVolume() > 0f) ? "On" : "Off"));
        resolutionButton.getLabel()
                        .setText(String.format("Resolution: %dx%d",
                                               Gdx.graphics.getWidth(),
                                               Gdx.graphics.getHeight()));
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }
}
