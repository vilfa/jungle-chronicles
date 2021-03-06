package si.vilfa.junglechronicles.Graphics.Gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Events.GameEvent;
import si.vilfa.junglechronicles.Events.MenuEvent;
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

    private final BitmapFont fontRegular;
    private final BitmapFont fontBold;
    private final Color fontColor;

    private final HudGuiElement hud;
    private final MainMenuGuiElement mainMenu;
    private final LevelsMenuGuiElement levelsMenu;
    private final PauseMenuGuiElement pauseMenu;
    private final OptionsMenuGuiElement optionsMenu;
    private final LeaderboardGuiElement leaderboardMenu;
    private final RendererStatsGuiElement renderStats;
    private final GameEndMenuGuiElement gameEndMenu;
    private final Image background;
    private boolean isRenderStatsEnabled = false;

    public GuiRenderer(Game game)
    {
        super(game);

        fontColor = new Color(0.42156863f, 1f, 0.5552286f, 1f);

        FreeTypeFontGenerator fontGeneratorRegular = new FreeTypeFontGenerator(Gdx.files.internal(
                "Graphics/Fonts/OpenSans/OpenSans-SemiBold.ttf"));
        FreeTypeFontGenerator fontGeneratorBold = new FreeTypeFontGenerator(Gdx.files.internal(
                "Graphics/Fonts/OpenSans/OpenSans-ExtraBold.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameterRegular
                = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameterRegular.color = fontColor;
        fontParameterRegular.size = 24;
        fontRegular = fontGeneratorRegular.generateFont(fontParameterRegular);

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameterBold
                = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameterBold.color = fontColor;
        fontParameterBold.size = 48;
        fontBold = fontGeneratorBold.generateFont(fontParameterBold);

        fontGeneratorBold.dispose();
        fontGeneratorRegular.dispose();


        guiViewport = new ScreenViewport();

        hud = new HudGuiElement(game, new TextureAtlas("Graphics/HUD.atlas"));
        mainMenu = new MainMenuGuiElement(game);
        levelsMenu = new LevelsMenuGuiElement(game);
        pauseMenu = new PauseMenuGuiElement(game);
        optionsMenu = new OptionsMenuGuiElement(game);
        leaderboardMenu = new LeaderboardGuiElement(game);
        gameEndMenu = new GameEndMenuGuiElement(game, fontBold, fontRegular);
        renderStats = new RendererStatsGuiElement(game);

        background = new Image(new Texture("Graphics/Bg.png"));
        background.setFillParent(true);

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

    public LevelsMenuGuiElement getLevelsMenu()
    {
        return levelsMenu;
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

    public GameEndMenuGuiElement getGameEndMenu()
    {
        return gameEndMenu;
    }

    public RendererStatsGuiElement getRenderStats()
    {
        return renderStats;
    }

    public Stage getStage()
    {
        return stage;
    }

    public boolean isRenderStatsEnabled()
    {
        return isRenderStatsEnabled;
    }

    @Override
    public void handleEvent(Event event)
    {
        if (event.getType().equals(GameEvent.GAME_SCREEN_CHANGE) && (event.getEventData().size == 1
                                                                     || event.getEventData().size
                                                                        == 2))
        {
            GameScreen gameScreen = (GameScreen) event.getEventData().first();
            stage.clear();
            if (isRenderStatsEnabled) stage.addActor(renderStats.getActor());

            switch (gameScreen)
            {
                case IN_GAME:
                    stage.addActor(hud.getActor());
                    break;
                case MAIN_MENU:
                    stage.addActor(background);
                    stage.addActor(mainMenu.getActor());
                    break;
                case LEVELS_MENU:
                    stage.addActor(background);
                    stage.addActor(levelsMenu.getActor());
                    break;
                case PAUSE_MENU:
                    stage.addActor(background);
                    stage.addActor(pauseMenu.getActor());
                    break;
                case OPTIONS_MENU:
                    stage.addActor(background);
                    stage.addActor(optionsMenu.getActor());
                    break;
                case GAME_END:
                    if (event.getEventData().size == 2)
                    {
                        stage.addActor(background);
                        if ((boolean) event.getEventData().get(1))
                        {
                            gameEndMenu.clear();
                            gameEndMenu.setPlayerDied();
                            stage.addActor(gameEndMenu.getActor());
                        } else
                        {
                            gameEndMenu.clear();
                            gameEndMenu.setLevelEnd();
                            stage.addActor(gameEndMenu.getActor());
                        }
                    } else
                    {
                        error("unknown game end event");
                    }
                    break;
                case LEADERBOARD_MENU:
                    stage.addActor(background);
                    stage.addActor(leaderboardMenu.getActor());
                    break;
            }
        }

        if (event.getType().equals(MenuEvent.RENDER_STATS_BUTTON_CLICK))
        {
            isRenderStatsEnabled = !isRenderStatsEnabled;

            stage.clear();
            if (isRenderStatsEnabled) stage.addActor(renderStats.getActor());

            switch (game.getGameScreen())
            {
                case IN_GAME:
                    stage.addActor(hud.getActor());
                    break;
                case MAIN_MENU:
                    stage.addActor(background);
                    stage.addActor(mainMenu.getActor());
                    break;
                case LEVELS_MENU:
                    stage.addActor(background);
                    stage.addActor(levelsMenu.getActor());
                    break;
                case PAUSE_MENU:
                    stage.addActor(background);
                    stage.addActor(pauseMenu.getActor());
                    break;
                case OPTIONS_MENU:
                    stage.addActor(background);
                    stage.addActor(optionsMenu.getActor());
                    break;
                case GAME_END:
                    if (event.getEventData().size == 2)
                    {
                        stage.addActor(background);
                        if ((boolean) event.getEventData().get(2))
                        {
                            gameEndMenu.clear();
                            gameEndMenu.setPlayerDied();
                            stage.addActor(gameEndMenu.getActor());
                        } else
                        {
                            gameEndMenu.clear();
                            gameEndMenu.setLevelEnd();
                            stage.addActor(gameEndMenu.getActor());
                        }
                    } else
                    {
                        error("unknown game end event");
                    }
                    break;
                case LEADERBOARD_MENU:
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
        renderStats.dispose();
        gameEndMenu.dispose();
    }

    @Override
    public void update()
    {
        mainMenu.update();
        pauseMenu.update();
        optionsMenu.update();
        renderStats.update();
        gameEndMenu.update();
        if (!isUpdatable || game.isPaused()) return;
        hud.update();
        stage.act();
    }
}
