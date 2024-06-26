package view.components.ui;

import view.components.GameComponent;
import view.context.GlobalContext;
import view.input.MouseEvent;
import view.themes.ThemeColor;
import view.themes.ThemeColor.ColorName;
import view.themes.ThemeColor.ColorVariant;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a selector component that can be rendered on the screen.
 * <p>
 * This class represents a selector component that can be rendered on the screen.
 * It provides a basic structure for rendering selectors on the screen and
 * handling mouse events.
 * </p>
 *
 * @param <T> the type of the options in the selector.
 */
public final class Selector<T> extends GameComponent {

    private final ArrayList<T> options;
    private final SelectorType type;
    private final int min;
    private final int max;
    private int selectedOption;

    /**
     * Creates a new Selector with the given options and context provider.
     *
     * @param options       the list of options for the selector.
     * @param globalContext the context provider for the selector.
     */
    public Selector(ArrayList<T> options, GlobalContext globalContext) {
        super(globalContext);

        this.options = options;
        this.selectedOption = 0;
        this.min = 0;
        this.max = options.size() - 1;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.type = SelectorType.OBJECT;
    }

    /**
     * Creates a new Selector with the given options, min, max, and context provider.
     *
     * @param min           the minimum value for the selector.
     * @param max           the maximum value for the selector.
     * @param globalContext the context provider for the selector.
     */
    public Selector(int min, int max, GlobalContext globalContext) {
        super(globalContext);

        this.options = null;
        this.selectedOption = min;
        this.min = min;
        this.max = max;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.type = SelectorType.NUMBER;
    }

    /**
     * Creates a new Selector with the given default value and context provider.
     *
     * @param defaultValue  the default value for the selector.
     * @param globalContext the context provider for the selector.
     */
    public Selector(boolean defaultValue, GlobalContext globalContext) {
        super(globalContext);

        this.options = null;
        this.selectedOption = defaultValue ? 1 : 0;
        this.min = 0;
        this.max = 1;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.type = SelectorType.BOOLEAN;
    }

    /**
     * Updates the component's logic.
     * <p>
     * This method only handles mouse events for the selector.
     * It runs the {@link #pollMouseEvents} method to check for mouse events.
     * The selected option is updated when the user clicks on the selector.
     * </p>
     */
    @Override
    public void update() {
        this.pollMouseEvents();
    }

    /**
     * Renders the selector component on the screen.
     *
     * @param graphics the graphics object to render the component with.
     */
    @Override
    public void render(Graphics2D graphics) {
        //TODO: Remove code duplication with Button render method
        graphics.setFont(this.style.font);

        FontMetrics fontMetrics = this.globalContext.window().getCanvas().getFontMetrics(this.style.font);
        Color backgroundColor = this.globalContext.currentTheme().getColor(this.getStyle().backgroundColor);
        Color foregroundColor = this.globalContext.currentTheme().getColor(this.getStyle().foregroundColor);

        graphics.setColor(backgroundColor);
        graphics.fillRoundRect(
                this.style.x,
                this.style.y,
                this.style.width,
                this.style.height,
                this.style.borderRadius,
                this.style.borderRadius
        );

        int textWidth = fontMetrics.stringWidth(this.getStringValue());
        int textHeight = fontMetrics.getHeight();
        int adjust = 8;

        graphics.setColor(foregroundColor);
        graphics.drawString(
                this.getStringValue(),
                this.style.x + (this.style.width - textWidth) / 2,
                this.style.y + (this.style.height + textHeight - adjust) / 2
        );

        graphics.drawString(
                ">",
                this.style.x + this.style.width - 32,
                this.style.y + (this.style.height + textHeight - adjust) / 2
        );
        graphics.drawString(
                "<",
                this.style.x + 16,
                this.style.y + (this.style.height + textHeight - adjust) / 2
        );
    }

    /**
     * Fits the component's size to its content.
     * <p>
     * This method is not implemented for the Selector component.
     * Selector components have a fixed size and do not need to fit to their content.
     * </p>
     */
    @Override
    public void fitSize() {
    }

    /**
     * Sets up the default style for the component.
     * <p>
     * This method sets up the default style for the selector component.
     * It sets the background color to the primary color of the current theme,
     * the foreground color to the background color of the current theme,
     * the font to a {@code 26pt} font, the height to {@code 60px}, the width to {@code 300px},
     * and the border radius to {@code 16px}.
     * <br>
     * This method is called in the constructor of the component.
     * </p>
     */
    @Override
    protected void setupDefaultStyle() {
        //TODO: Remove code duplication with Button default style
        this.style.backgroundColor = new ThemeColor(ColorName.PRIMARY, ColorVariant.NORMAL);
        this.style.foregroundColor = new ThemeColor(ColorName.BACKGROUND, ColorVariant.NORMAL);
        this.style.font = this.globalContext.gameFont().deriveFont(26.0f);
        this.style.height = 60;
        this.style.width = 300;
        this.style.borderRadius = 16;
    }

    /**
     * Sets up the default event listeners for the component.
     * <p>
     * This method sets up the default event listeners for the selector component.
     * It adds a click event listener that changes the selected option when the user clicks on the selector.
     * The selected option is changed to the previous option when the user clicks on the left side of the selector.
     * The selected option is changed to the next option when the user clicks on the right side of the selector.
     * </p>
     */
    @Override
    protected void setupDefaultEventListeners() {
        super.setupDefaultEventListeners();

        this.addMouseListener(MouseEvent.EventType.RELEASED, event -> {
            Point relativePoint = event.relativeMousePosition;
            if (relativePoint.x < this.style.width / 2) {
                this.decrementSelectedOption();
            } else {
                this.incrementSelectedOption();
            }
        });
    }

    /**
     * Increments the selected option in the selector.
     *
     * <p>
     * This method increments the selected option in the selector.
     * If the selector is an object selector, the selected option is incremented by one.
     * If the selector is a number selector, the selected option is incremented by one if it is less than the maximum value.
     * If the selector is a boolean selector, the selected option is toggled between true and false.
     * <br>
     * The {@link ComponentEvent#VALUE_CHANGED} event is dispatched with the previous and new selected options.
     * </p>
     */
    public void incrementSelectedOption() {
        T previousValue = this.getSelectedOption();

        if (this.type == SelectorType.OBJECT)
            this.selectedOption = (this.selectedOption + 1) % this.options.size();
        else if (this.type == SelectorType.NUMBER)
            this.selectedOption += this.selectedOption < this.max ? 1 : 0;
        else {
            this.selectedOption = this.selectedOption == 0 ? 1 : 0;
        }

        this.dispatchComponentEvent(ComponentEvent.VALUE_CHANGED, previousValue, this.getSelectedOption());
    }

    /**
     * Decrements the selected option in the selector.
     *
     * <p>
     * This method decrements the selected option in the selector.
     * If the selector is an object selector, the selected option is decremented by one.
     * If the selector is a number selector, the selected option is decremented by one if it is greater than the minimum value.
     * If the selector is a boolean selector, the selected option is toggled between true and false.
     * <br>
     * The {@link ComponentEvent#VALUE_CHANGED} event is dispatched with the previous and new selected options.
     * </p>
     */
    public void decrementSelectedOption() {
        T previousValue = this.getSelectedOption();

        if (this.type == SelectorType.OBJECT)
            this.selectedOption = (this.selectedOption - 1 + this.options.size()) % this.options.size();
        else if (this.type == SelectorType.NUMBER)
            this.selectedOption -= this.selectedOption > this.min ? 1 : 0;
        else {
            this.selectedOption = this.selectedOption == 1 ? 0 : 1;
        }

        this.dispatchComponentEvent(ComponentEvent.VALUE_CHANGED, previousValue, this.getSelectedOption());
    }

    /**
     * Gets the selected option from the selector.
     *
     * @return the selected option from the selector.
     */
    public T getSelectedOption() {
        return switch (this.type) {
            case OBJECT -> this.options.get(this.selectedOption);
            case NUMBER -> (T) Integer.valueOf(this.selectedOption);
            case BOOLEAN -> (T) Boolean.valueOf(this.selectedOption == 1);
        };
    }

    /**
     * Sets the selected option for the selector.
     * <p>
     * This method sets the selected option for the selector.
     * If the selector is an object selector, the selected option is set to the given option.
     * If the selector is a number selector, the selected option is set to the given number if it is within the range.
     * If the selector is a boolean selector, the selected option is set to the given boolean value.
     * <br>
     * The {@link ComponentEvent#VALUE_CHANGED} event is dispatched with the previous and new selected options.
     * If the option is not found in the selector, an {@link IllegalArgumentException} is thrown.
     * If the option is out of range, an {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * @param selectedOption the selected option to set for the selector.
     */
    public void setSelectedOption(T selectedOption) {
        if (this.type == SelectorType.OBJECT) {
            int index = this.options.indexOf(selectedOption);
            if (index == -1) {
                throw new IllegalArgumentException("Option not found in selector");
            }
            this.setSelectedIndex(index);
        }

        if (this.type == SelectorType.NUMBER) {
            int index = (int) selectedOption;
            if (index < this.min || index > this.max) {
                throw new IllegalArgumentException("Option out of range");
            }
            this.selectedOption = index;
        }

        if (this.type == SelectorType.BOOLEAN) {
            boolean value = (boolean) selectedOption;
            this.selectedOption = value ? 1 : 0;
        }

        this.dispatchComponentEvent(ComponentEvent.VALUE_CHANGED, selectedOption, this.getSelectedOption());
    }

    /**
     * Gets the selected index from the selector.
     *
     * @return the selected index from the selector.
     */
    public int getSelectedIndex() {
        return this.selectedOption;
    }

    /**
     * Sets the selected index for the selector.
     * <p>
     * This method sets the selected index for the selector.
     * If the selector is an object selector, the selected index is set to the given index.
     * If the selector is a number selector, the selected index is set to the given number if it is within the range.
     * If the selector is a boolean selector, the selected index is set to the given boolean value.
     * <br>
     * The {@link ComponentEvent#VALUE_CHANGED} event is dispatched with the previous and new selected options.
     * If the index is out of range, an {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * @param selectedOption the selected index to set for the selector.
     */
    public void setSelectedIndex(int selectedOption) {

        if (this.type != SelectorType.OBJECT) {
            throw new UnsupportedOperationException("Selected index can only be set for object selectors");
        }

        if (selectedOption != this.selectedOption) {
            this.dispatchComponentEvent(ComponentEvent.VALUE_CHANGED, this.getSelectedOption(), this.options.get(selectedOption));
            this.selectedOption = selectedOption;
        }
    }

    /**
     * Gets the string value of the selected option.
     *
     * @return the string value of the selected option.
     */
    private String getStringValue() {
        return switch (this.type) {
            case OBJECT -> this.options.get(this.selectedOption).toString();
            case NUMBER -> Integer.toString(this.selectedOption);
            case BOOLEAN -> this.selectedOption == 1 ? "Yes" : "No";
        };
    }

    /**
     * Sets the maximum value for the number selector.
     * <p>
     * This method sets the maximum value for the number selector.
     * If the selector is not a number selector, an {@link UnsupportedOperationException} is thrown.
     * If the maximum value is less than the minimum value, an {@link IllegalArgumentException} is thrown.
     * The selected option is updated to the maximum value if it is greater than the maximum value.
     * <br>
     * The {@link ComponentEvent#VALUE_CHANGED} event is dispatched with the previous and new selected options.
     * </p>
     *
     * @param max the maximum value to set for the number selector.
     */
    public void setMax(int max) {
        if (this.type != SelectorType.NUMBER) {
            throw new UnsupportedOperationException("Max value can only be set for number selectors");
        }

        if (max < this.min) {
            throw new IllegalArgumentException("Max value cannot be less than min value");
        }

        this.selectedOption = Math.min(max, this.selectedOption);
    }

    /**
     * Enum representing the different selector types.
     *
     * <p>
     * This enum represents the different types of selectors that can be created.
     * The selector type can be an object selector, a number selector, or a boolean selector.
     * </p>
     */
    public enum SelectorType {

        /**
         * The type of the selector is an object selector.
         * <p>
         * This type represents a selector that can select from a list of objects.
         * </p>
         */
        OBJECT,

        /**
         * The type of the selector is a number selector.
         * <p>
         * This type represents a selector that can select from a range of numbers.
         * </p>
         */
        NUMBER,

        /**
         * The type of the selector is a boolean selector.
         * <p>
         * This type represents a selector that can select between true and false.
         * </p>
         */
        BOOLEAN
    }
}
