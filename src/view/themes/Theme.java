package view.themes;

import view.themes.ThemeColor.ColorName;
import view.themes.ThemeColor.ColorVariant;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a theme object that can be applied to the game view.
 * <p>
 * This class represents a theme object that can be applied to the game view.
 * It provides a structure for storing theme properties such as colors and fonts.
 * The theme object can be applied to the game view to change its appearance.
 * </p>
 */
public abstract class Theme {

    private final HashMap<ColorName, HashMap<ColorVariant, Color>> colors;

    /**
     * Creates a new Theme with the given colors.
     *
     * @param primary          the primary color of the theme.
     * @param primaryBright    the primary contrast color of the theme.
     * @param primaryDimmed    the secondary color of the theme.
     * @param background       the background color of the theme.
     * @param backgroundBright the background contrast color of the theme.
     * @param backgroundDimmed the background contrast color of the theme.
     * @param foreground       the foreground color of the theme.
     * @param foregroundBright the foreground contrast color of the theme.
     * @param foregroundDimmed the foreground contrast color of the theme.
     * @param red              the red color of the theme.
     * @param redBright        the red contrast color of the theme.
     * @param redDimmed        the red contrast color of the theme.
     * @param yellow           the yellow color of the theme.
     * @param yellowBright     the yellow contrast color of the theme.
     * @param yellowDimmed     the yellow contrast color of the theme.
     * @param green            the green color of the theme.
     * @param greenBright      the green contrast color of the theme.
     * @param greenDimmed      the green contrast color of the theme.
     * @param cyan             the cyan color of the theme.
     * @param cyanBright       the cyan contrast color of the theme.
     * @param cyanDimmed       the cyan contrast color of the theme.
     * @param blue             the blue color of the theme.
     * @param blueBright       the blue contrast color of the theme.
     * @param blueDimmed       the blue contrast color of the theme.
     * @param magenta          the magenta color of the theme.
     * @param magentaBright    the magenta contrast color of the theme.
     * @param magentaDimmed    the magenta contrast color of the theme.
     */
    public Theme(
            Color primary,
            Color primaryBright,
            Color primaryDimmed,
            Color background,
            Color backgroundBright,
            Color backgroundDimmed,
            Color foreground,
            Color foregroundBright,
            Color foregroundDimmed,
            Color white,
            Color whiteBright,
            Color whiteDimmed,
            Color black,
            Color blackBright,
            Color blackDimmed,
            Color red,
            Color redBright,
            Color redDimmed,
            Color yellow,
            Color yellowBright,
            Color yellowDimmed,
            Color green,
            Color greenBright,
            Color greenDimmed,
            Color cyan,
            Color cyanBright,
            Color cyanDimmed,
            Color blue,
            Color blueBright,
            Color blueDimmed,
            Color magenta,
            Color magentaBright,
            Color magentaDimmed
    ) {

        this.colors = new HashMap<>();

        this.colors.put(ColorName.PRIMARY, new HashMap<>() {{
            put(ColorVariant.NORMAL, primary);
            put(ColorVariant.BRIGHT, primaryBright);
            put(ColorVariant.DIMMED, primaryDimmed);
        }});

        this.colors.put(ColorName.BACKGROUND, new HashMap<>() {{
            put(ColorVariant.NORMAL, background);
            put(ColorVariant.BRIGHT, backgroundBright);
            put(ColorVariant.DIMMED, backgroundDimmed);
        }});

        this.colors.put(ColorName.FOREGROUND, new HashMap<>() {{
            put(ColorVariant.NORMAL, foreground);
            put(ColorVariant.BRIGHT, foregroundBright);
            put(ColorVariant.DIMMED, foregroundDimmed);
        }});

        this.colors.put(ColorName.BLACK, new HashMap<>() {{
            put(ColorVariant.NORMAL, Color.BLACK);
            put(ColorVariant.BRIGHT, Color.BLACK);
            put(ColorVariant.DIMMED, Color.BLACK);
        }});

        this.colors.put(ColorName.WHITE, new HashMap<>() {{
            put(ColorVariant.NORMAL, Color.WHITE);
            put(ColorVariant.BRIGHT, Color.WHITE);
            put(ColorVariant.DIMMED, Color.WHITE);
        }});

        this.colors.put(ColorName.RED, new HashMap<>() {{
            put(ColorVariant.NORMAL, red);
            put(ColorVariant.BRIGHT, redBright);
            put(ColorVariant.DIMMED, redDimmed);
        }});

        this.colors.put(ColorName.YELLOW, new HashMap<>() {{
            put(ColorVariant.NORMAL, yellow);
            put(ColorVariant.BRIGHT, yellowBright);
            put(ColorVariant.DIMMED, yellowDimmed);
        }});

        this.colors.put(ColorName.GREEN, new HashMap<>() {{
            put(ColorVariant.NORMAL, green);
            put(ColorVariant.BRIGHT, greenBright);
            put(ColorVariant.DIMMED, greenDimmed);
        }});

        this.colors.put(ColorName.CYAN, new HashMap<>() {{
            put(ColorVariant.NORMAL, cyan);
            put(ColorVariant.BRIGHT, cyanBright);
            put(ColorVariant.DIMMED, cyanDimmed);
        }});

        this.colors.put(ColorName.BLUE, new HashMap<>() {{
            put(ColorVariant.NORMAL, blue);
            put(ColorVariant.BRIGHT, blueBright);
            put(ColorVariant.DIMMED, blueDimmed);
        }});

        this.colors.put(ColorName.MAGENTA, new HashMap<>() {{
            put(ColorVariant.NORMAL, magenta);
            put(ColorVariant.BRIGHT, magentaBright);
            put(ColorVariant.DIMMED, magentaDimmed);
        }});

        this.colors.put(ColorName.TRANSPARENT, new HashMap<>() {{
            put(ColorVariant.NORMAL, new Color(0, 0, 0, 0));
            put(ColorVariant.BRIGHT, new Color(0, 0, 0, 0));
            put(ColorVariant.DIMMED, new Color(0, 0, 0, 0));
        }});
    }

    /**
     * Gets the color of the given name and variant.
     *
     * @param colorName    the name of the color.
     * @param colorVariant the variant of the color.
     * @return the color of the given name and variant.
     */
    public Color getColor(ColorName colorName, ColorVariant colorVariant) {
        return colors.get(colorName).get(colorVariant);
    }

    /**
     * Gets the color of the given theme color.
     *
     * @param themeColor the theme color.
     * @return the color of the given theme color.
     */
    public Color getColor(ThemeColor themeColor) {
        return colors.get(themeColor.name()).get(themeColor.variant());
    }

    public ArrayList<ColorName> getPlayerColors() {
        ArrayList<ColorName> colorList = new ArrayList<>();

        colorList.add(ColorName.RED);
        colorList.add(ColorName.YELLOW);
        colorList.add(ColorName.GREEN);
        colorList.add(ColorName.CYAN);
        colorList.add(ColorName.BLUE);
        colorList.add(ColorName.MAGENTA);

        return colorList;
    }
}