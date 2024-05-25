package view.components.match;

import model.cell.CellType;
import view.context.GlobalContext;
import view.context.MatchContext;
import view.context.Style;
import view.themes.ThemeColor.ColorName;
import view.themes.ThemeColor.ColorVariant;

import java.awt.*;
import java.awt.image.BufferedImage;

import static view.components.match.Board.CELL_SIZE;
import static view.components.match.Board.WALL_SIZE;

/**
 * Represents a renderer for the cells of the board.
 * <p>
 * This class represents a renderer for the cells of the board.
 * It provides a structure for rendering the cells of the board on the screen.
 * The cell renderer is used to render the cells of the board on the screen.
 * </p>
 */
public final class CellRenderer {

    private final Style boardStyle;
    private final GlobalContext globalContext;
    private final MatchContext matchContext;

    /**
     * Creates a new cell renderer with the given style, global context, and match context.
     *
     * @param boardStyle    the style of the board.
     * @param globalContext the global context of the game.
     * @param matchContext  the match context of the game.
     */
    public CellRenderer(Style boardStyle, GlobalContext globalContext, MatchContext matchContext) {
        this.boardStyle = boardStyle;
        this.globalContext = globalContext;
        this.matchContext = matchContext;
    }

    /**
     * Renders the cells of the board on the screen.
     *
     * @param graphics the graphics object to render the cells with.
     */
    public void render(Graphics2D graphics) {

        int widthCells = this.matchContext.cells().length;
        int heightCells = this.matchContext.cells()[0].length;

        graphics.setColor(this.globalContext.currentTheme().getColor(ColorName.BACKGROUND, ColorVariant.NORMAL));

        for (int i = 0; i < widthCells; i++) {
            for (int j = 0; j < heightCells; j++) {
                CellType cellType = this.matchContext.cells()[i][j];

                int x = this.boardStyle.x + this.boardStyle.borderWidth + i * (CELL_SIZE + WALL_SIZE);
                int y = this.boardStyle.y + this.boardStyle.borderWidth + j * (CELL_SIZE + WALL_SIZE);

                graphics.drawImage(this.getCellImage(cellType), x, y, null);
            }
        }
    }

    /**
     * Gets the image of the cell with the given type.
     *
     * @param cellType the type of the cell.
     * @return the image of the cell.
     */
    private Image getCellImage(CellType cellType) {
        BufferedImage image = new BufferedImage(CELL_SIZE, CELL_SIZE, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        switch (cellType) {
            case NORMAL -> drawNormalCell(graphics);
            case DOUBLE_TURN -> drawDoubleTurnCell(graphics);
            case TELEPORT -> drawTeleportCell(graphics);
            case RETURN -> drawReturnCell(graphics);
            default ->
                //TODO: Handle this case or throw
                    throw new IllegalArgumentException("Invalid cell type: " + cellType);
        }

        graphics.dispose();

        return image;
    }

    /**
     * Draws a normal cell on the screen.
     *
     * @param graphics the graphics object to render the cell with.
     */
    private void drawNormalCell(Graphics2D graphics) {
        graphics.setColor(this.globalContext.currentTheme().getColor(ColorName.BACKGROUND, ColorVariant.NORMAL));
        graphics.fillRoundRect(0, 0, CELL_SIZE, CELL_SIZE, 8, 8);
    }

    /**
     * Draws a double turn cell on the screen.
     *
     * @param graphics the graphics object to render the cell with.
     */
    private void drawDoubleTurnCell(Graphics2D graphics) {
        graphics.setColor(this.globalContext.currentTheme().getColor(ColorName.GREEN, ColorVariant.DIMMED));
        graphics.fillRoundRect(0, 0, CELL_SIZE, CELL_SIZE, 8, 8);
    }

    /**
     * Draws a teleport cell on the screen.
     *
     * @param graphics the graphics object to render the cell with.
     */
    private void drawTeleportCell(Graphics2D graphics) {
        graphics.setColor(this.globalContext.currentTheme().getColor(ColorName.MAGENTA, ColorVariant.DIMMED));
        graphics.fillRoundRect(0, 0, CELL_SIZE, CELL_SIZE, 8, 8);
    }

    /**
     * Draws a return cell on the screen.
     *
     * @param graphics the graphics object to render the cell with.
     */
    private void drawReturnCell(Graphics2D graphics) {
        graphics.setColor(this.globalContext.currentTheme().getColor(ColorName.RED, ColorVariant.DIMMED));
        graphics.fillRoundRect(0, 0, CELL_SIZE, CELL_SIZE, 8, 8);
    }
}
