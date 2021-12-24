package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Gameplay.Game;
import si.vilfa.junglechronicles.Level.Scene.GuiSceneTile;

import java.util.HashMap;

/**
 * @author luka
 * @date 23/12/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public class GuiRenderer extends Renderer
{
    private final BitmapFont font;
    private final TextureAtlas guiTextureAtlas;
    private final HashMap<GuiElement, GuiSceneTile> tilesByElement;
    private final HashMap<GuiElementType, HashMap<GuiElementPosition, Array<GuiSceneTile>>>
            elementsByPositionByType;

    public GuiRenderer(Game game)
    {
        super(game);

        font = new BitmapFont();
        guiTextureAtlas = new TextureAtlas("Graphics/HUD.atlas");
        tilesByElement = new HashMap<>();
        elementsByPositionByType = new HashMap<>();

        initializeGuiElements();
    }

    private void initializeGuiElements()
    {
        for (GuiElement guiElement : GuiElement.values())
        {
            TextureRegion textureRegion
                    = guiTextureAtlas.findRegion(guiElement.getTextureRegionName());
            if (textureRegion != null)
            {
                tilesByElement.put(guiElement, new GuiSceneTile(textureRegion));
            } else
            {
                log("Missing gui element:" + guiElement.getTextureRegionName());
            }
        }


    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
    }

    @Override
    public void draw()
    {
        spriteBatch.setProjectionMatrix(getCombined());
        spriteBatch.begin();

        for (GuiSceneTile tile : game.getCurrentLevel().getGuiTiles())
        {
            tile.draw(spriteBatch);
        }

        spriteBatch.end();
    }

    @Override
    public void dispose()
    {
        super.dispose();
        font.dispose();
        guiTextureAtlas.dispose();
    }

    @Override
    public void update()
    {

    }

    public enum GuiElement
    {
        ZERO("hud0"),
        ONE("hud1"),
        TWO("hud2"),
        THREE("hud3"),
        FOUR("hud4"),
        FIVE("hud5"),
        SIX("hud6"),
        SEVEN("hud7"),
        EIGHT("hud8"),
        NINE("hud9"),
        HEART_EMPTY("hudHeart_empty"),
        HEART_HALF("hudHeart_full"),
        HEART_FULL("hudHeart_half"),
        JEWEL_BLUE_FULL("hudJewel_blue"),
        JEWEL_BLUE_EMPTY("hudJewel_blue_empty"),
        JEWEL_GREEN_FULL("hudJewel_green"),
        JEWEL_GREEN_EMPTY("hudJewel_green_empty"),
        JEWEL_RED_FULL("hudJewel_red"),
        JEWEL_RED_EMPTY("hudJewel_red_empty"),
        JEWEL_YELLOW_FULL("hudJewel_yellow"),
        JEWEL_YELLOW_EMPTY("hudJewel_yellow_empty"),
        KEY_BLUE_FULL("hudKey_blue"),
        KEY_BLUE_EMPTY("hudKey_blue_empty"),
        KEY_GREEN_FULL("hudKey_green"),
        KEY_GREEN_EMPTY("hudKey_green_empty"),
        KEY_RED_FULL("hudKey_red"),
        KEY_RED_EMPTY("hudKey_red_empty"),
        KEY_YELLOW_FULL("hudKey_yellow"),
        KEY_YELLOW_EMPTY("hudKey_yellow_empty");

        private final String textureRegionName;

        GuiElement(String textureRegionName)
        {
            this.textureRegionName = textureRegionName;
        }

        public String getTextureRegionName()
        {
            return textureRegionName;
        }
    }

    public enum GuiElementType
    {
        HEALTH_INDICATOR, SCORE_INDICATOR, TIME_INDICATOR
    }

    public enum GuiElementPosition
    {
        TOP_LEFT,
        TOP_RIGHT,
        TOP_CENTER,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        BOTTOM_CENTER,
        MID_LEFT,
        MID_RIGHT,
        MID_CENTER
    }
}
