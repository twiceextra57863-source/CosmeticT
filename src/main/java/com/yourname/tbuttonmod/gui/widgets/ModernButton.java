package com.yourname.tbuttonmod.gui.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ModernButton extends ButtonWidget {
    
    public ModernButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }
    
    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        int color = this.isHovered() ? 0xFF9B7EBD : 0xFF6B4E71;
        context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, color);
        context.drawCenteredTextWithShadow(this.getTextRenderer(), this.getMessage(), 
            this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, 0xFFFFFF);
    }
}
