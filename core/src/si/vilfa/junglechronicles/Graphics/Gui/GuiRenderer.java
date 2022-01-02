package si.vilfa.junglechronicles.Graphics.Gui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import si.vilfa.junglechronicles.Gameplay.Game;
import si.vilfa.junglechronicles.Graphics.Renderer;

/**
 * @author luka
 * @date 23/12/2021
 * @package si.vilfa.junglechronicles.Graphics.Gui
 **/
public class GuiRenderer extends Renderer
{
    private final ScreenViewport guiViewport;
    private final Stage stage;

    private final HudGuiElement hud;
    private final MainMenuGuiElement mainMenu;
    private final PauseMenuGuiElement pauseMenu;

    public GuiRenderer(Game game)
    {
        super(game);

        guiViewport = new ScreenViewport();

        hud = new HudGuiElement(game, new TextureAtlas("Graphics/HUD.atlas"));
        mainMenu = new MainMenuGuiElement(game);
        pauseMenu = new PauseMenuGuiElement(game);

        stage = new Stage(guiViewport);
        stage.addActor(hud.getActor());
        stage.addActor(mainMenu.getActor());
    }

    public HudGuiElement getHud()
    {
        return hud;
    }

    public MainMenuGuiElement getMainMenu()
    {
        return mainMenu;
    }

    public PauseMenuGuiElement getPauseMenu()
    {
        return pauseMenu;
    }

    public Stage getStage()
    {
        return stage;
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        guiViewport.update(width, height, true);
    }

    @Override
    public void draw()
    {
        stage.draw();
    }

    @Override
    public void dispose()
    {
        super.dispose();
        stage.dispose();
        hud.dispose();
        mainMenu.dispose();
        pauseMenu.dispose();
    }

    @Override
    public void update()
    {
        if (!isUpdatable || game.isPaused()) return;
        hud.update();
        mainMenu.update();
        pauseMenu.update();
        stage.act();
    }
}
