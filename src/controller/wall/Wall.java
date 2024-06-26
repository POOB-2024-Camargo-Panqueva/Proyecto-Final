package controller.wall;

import controller.interfaces.ActionableElement;
import model.player.Player;
import model.wall.WallData;
import model.wall.WallType;

import java.awt.*;
import java.util.Arrays;
import java.util.UUID;

/**
 * Represents a wall element in the game.
 * This abstract class defines common functionality and properties for walls.
 */
public abstract class Wall implements ActionableElement {

    /** The data associated with the wall. */
    protected final WallData wallData;

    /**
     * Constructs a Wall object with default wall data.
     */
    public Wall() {
        this.wallData = new WallData();
    }

    /**
     * Constructs a Wall object with the specified wall data.
     *
     * @param wallData The wall data for this wall.
     */
    public Wall(WallData wallData) {
        this.wallData = wallData;
    }

    /**
     * Rotates the wall, changing its orientation.
     * This method rotates the wall shape by 90 degrees clockwise.
     */
    public void rotate() {
        int width = this.wallData.getWidth();
        int height = this.wallData.getHeight();

        WallType[][] shapeRotated = new WallType[height][width];

        for (int i = 0; i < width; i++) {
            shapeRotated[i] = Arrays.copyOf(this.wallData.getWallShape()[i], height);
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                shapeRotated[y][x] = this.wallData.getWallShape()[x][y];
            }
        }

        this.wallData.setWallShape(shapeRotated);
    }

    public WallData getWallData() {
        return wallData;
    }


    public Point getPositionOnBoard() {
        return this.wallData.getPositionOnBoard();
    }

    public char getCourse() {
        return this.wallData.getCourse();
    }

    public WallType[][] getWallShape() {
        return this.wallData.getWallShape();
    }

    public WallType getWallType() {
        return this.wallData.getWallType();
    }

    public int getWidth() {
        return this.wallData.getWidth();
    }

    public int getHeight() {
        return this.wallData.getHeight();
    }

    public Player getOwner() {
        return this.wallData.getOwner();
    }

    public UUID getWallId() {
        return this.wallData.getWallId();
    }

    public int getCreationTurn() {
        return this.wallData.getCreationTurn();
    }

    public boolean getIsAlly() {
        return this.wallData.getIsAlly();
    }

    public void setCourse(char course) {
        this.wallData.setCourse(course);
    }

    public void setWidth(int width) {
        this.wallData.setWidth(width);
    }

    public void setHeight(int height) {
        this.wallData.setHeight(height);
    }

    public void setWallShape(WallType[][] wallShape) {
        this.wallData.setWallShape(wallShape);
    }

    public void setWallType(WallType wallType) {
        this.wallData.setWallType(wallType);
    }

    public void setOwner(Player owner) {
        this.wallData.setOwner(owner);
    }

    public void setPositionOnBoard(Point positionOnBoard) {
        this.wallData.setPositionOnBoard(positionOnBoard);
    }

    public void setWallId(UUID wallId) {
        this.wallData.setWallId(wallId);
    }

    public void setCreationTurn(int creationTurn) {
        this.wallData.setCreationTurn(creationTurn);
    }

    public void setAlly(boolean ally) {
        this.wallData.setAlly(ally);
    }
    @Override
    public String toString() {
        if (this.getWallShape() == null) {
            return "Null";
        }
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < this.getWidth(); y++) {
            for (int x = 0; x < this.getHeight(); x++) {

                final WallType currPosition = this.getWallShape()[x][y];

                if (currPosition == null) {
                    sb.append(" 0 ").append(" ");
                    continue;
                }
                if (currPosition == WallType.NORMAL) {
                    sb.append("Nor").append(" ");
                } else if (currPosition == WallType.LARGE) {
                    sb.append("Lar").append(" ");
                } else if (currPosition == WallType.TEMPORAL_WALL) {
                    sb.append("Tem").append(" ");
                } else if (currPosition == WallType.ALLY) {
                    sb.append("All").append(" ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
