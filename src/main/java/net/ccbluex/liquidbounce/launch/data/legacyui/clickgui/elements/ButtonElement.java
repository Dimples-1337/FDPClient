/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.launch.data.legacyui.clickgui.elements;

import net.ccbluex.liquidbounce.launch.options.LegacyUiLaunchOption;

public class ButtonElement extends Element {

    public int hoverTime;
    protected String displayName;
    protected int color = 0xffffff;

    public ButtonElement(String displayName) {
        createButton(displayName);
    }

    public void createButton(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float button) {
        LegacyUiLaunchOption.clickGui.style.drawButtonElement(mouseX, mouseY, this);
        super.drawScreen(mouseX, mouseY, button);
    }

    @Override
    public int getHeight() {
        return 16;
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + 16;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
