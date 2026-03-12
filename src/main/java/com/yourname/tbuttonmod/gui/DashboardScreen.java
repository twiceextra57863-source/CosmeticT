package com.yourname.tbuttonmod.gui;

import com.yourname.tbuttonmod.gui.widgets.ModernButton;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class DashboardScreen extends Screen {
    private final Screen parent;

    public DashboardScreen(Screen parent) {
        super(Text.literal("T-Button Dashboard"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 4 + 24;

        this.addDrawableChild(new ModernButton(centerX - 100, startY, 200, 20, Text.literal("Menu"), button -> {
            this.client.setScreen(new MenuScreen(this));
        }));
        this.addDrawableChild(new ModernButton(centerX - 100, startY + 24, 200, 20, Text.literal("Theme Settings"), button -> {
            this.client.setScreen(new ThemeSettingsScreen(this));
        }));
        this.addDrawableChild(new ModernButton(centerX - 100, startY + 48, 200, 20, Text.literal("Skin Preview"), button -> {
            this.client.setScreen(new SkinPreviewScreen(this));
        }));
        this.addDrawableChild(new ModernButton(centerX - 100, startY + 96, 200, 20, Text.literal("Back"), button -> {
            this.client.setScreen(this.parent);
        }));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}