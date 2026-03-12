package com.yourname.tbuttonmod.gui.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ModernButton extends ButtonWidget {
    public ModernButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int color = this.isHovered() ? 0xFF555555 : 0xFF333333;
        context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, color);
        
        int border = 0xFF000000;
        context.drawBorder(this.getX(), this.getY(), this.width, this.height, border);

        context.drawCenteredTextWithShadow(
            MinecraftClient.getInstance().textRenderer, 
            this.getMessage(), 
            this.getX() + this.width / 2, 
            this.getY() + (this.height - 8) / 2, 
            this.active ? 0xFFFFFFFF : 0xFFAAAAAA
        );
    }
}