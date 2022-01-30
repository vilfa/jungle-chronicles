package si.vilfa.junglechronicles.Graphics.Gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import si.vilfa.junglechronicles.Gameplay.Game;

/**
 * @author luka
 * @date 29/01/2022
 * @package si.vilfa.junglechronicles.Graphics.Gui
 **/
public class RendererStatsGuiElement extends GuiElement
{
    private final Table actor;
    private final Label label;

    public RendererStatsGuiElement(Game game)
    {
        super(game);

        actor = new Table();
        label = new Label("", skin);

        initializeElement();
    }

    public Label getLabel()
    {
        return label;
    }

    @Override
    public Actor getActor()
    {
        return actor;
    }

    @Override
    protected void initializeElement()
    {
        actor.align(Align.bottomLeft);
        actor.defaults().pad(12f);
        actor.setFillParent(true);

        Label.LabelStyle style = label.getStyle();
        style.fontColor = new Color(0f, 0f, 255f, 1f);

        actor.add(label);
    }

    @Override
    public void update() { }

    @Override
    public void dispose()
    {
        super.dispose();
    }
}
