package si.vilfa.junglechronicles.Graphics.Gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Events.GameEvent;
import si.vilfa.junglechronicles.Gameplay.Game;

/**
 * @author luka
 * @date 05/01/2022
 * @package si.vilfa.junglechronicles.Graphics.Gui
 **/
public class LeaderboardGuiElement extends GuiElement implements EventListener
{
    private final Table actor;

    public LeaderboardGuiElement(Game game)
    {
        super(game);

        actor = new Table();

        initializeElement();
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
        actor.defaults().pad(12f);
        actor.setFillParent(true);

        int position = 1;
        for (Integer score : game.getPreferences().getLeaderboard())
        {
            TextField textField = new TextField(String.format("%d. %d", position++, score), skin);
            textField.setDisabled(true);
            actor.add(textField).row();
        }
    }

    @Override
    public void update()
    {
        actor.clear();
        initializeElement();
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }

    @Override
    public void handleEvent(Event event)
    {
        if (event.getType().equals(GameEvent.GAME_LEADERBOARD_UPDATE))
        {
            update();
        }
    }
}
