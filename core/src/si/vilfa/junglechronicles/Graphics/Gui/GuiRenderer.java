package si.vilfa.junglechronicles.Graphics.Gui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Events.GameEvent;
import si.vilfa.junglechronicles.Gameplay.Game;
import si.vilfa.junglechronicles.Graphics.Renderer;

/**
 * @author luka
 * @date 23/12/2021
 * @package si.vilfa.junglechronicles.Graphics.Gui
 **/
public class GuiRenderer extends Renderer implements EventListener
{
    private final ScreenViewport guiViewport;
    private final Stage stage;

    private final HudGuiElement hud;
    private final MainMenuGuiElement mainMenu;
    private final PauseMenuGuiElement pauseMenu;
    private final OptionsMenuGuiElement optionsMenu;
    private final LeaderboardGuiElement leaderboardMenu;

    private final Image background;

    public GuiRenderer(Game game)
    {
        super(game);

        guiViewport = new ScreenViewport();

        hud = new HudGuiElement(game, new TextureAtlas("Graphics/HUD.atlas"));
        mainMenu = new MainMenuGuiElement(game);
        pauseMenu = new PauseMenuGuiElement(game);
        optionsMenu = new OptionsMenuGuiElement(game);
        leaderboardMenu = new LeaderboardGuiElement(game);

        background = new Image();
        background.setFillParent(true);
        background.setColor(0, 0, 0, 0.25f);

        stage = new Stage(guiViewport);
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

    public OptionsMenuGuiElement getOptionsMenu()
    {
        return optionsMenu;
    }

    public LeaderboardGuiElement getLeaderboardMenu()
    {
        return leaderboardMenu;
    }

    public Stage getStage()
    {
        return stage;
    }

    @Override
    public void handleEvent(Event event)
    {
        if (event.getType().equals(GameEvent.GAME_SCREEN_CHANGE) && event.getEventData().size == 1)
        {
            GameScreen gameScreen = (GameScreen) event.getEventData().first();
            switch (gameScreen)
            {
                case IN_GAME:
                    stage.clear();
                    stage.addActor(hud.getActor());
                    break;
                case MAIN_MENU:
                    stage.clear();
                    stage.addActor(background);
                    stage.addActor(mainMenu.getActor());
                    break;
                case PAUSE_MENU:
                    stage.clear();
                    stage.addActor(background);
                    stage.addActor(pauseMenu.getActor());
                    break;
                case OPTIONS_MENU:
                    stage.clear();
                    stage.addActor(background);
                    stage.addActor(optionsMenu.getActor());
                    break;
                case LEADERBOARD_MENU:
                    stage.clear();
                    stage.addActor(background);
                    stage.addActor(leaderboardMenu.getActor());
                    break;
            }
        }
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
        optionsMenu.dispose();
        leaderboardMenu.dispose();
    }

    @Override
    public void update()
    {
        mainMenu.update();
        pauseMenu.update();
        optionsMenu.update();
        if (!isUpdatable || game.isPaused()) return;
        hud.update();
        stage.act();
    }
}
