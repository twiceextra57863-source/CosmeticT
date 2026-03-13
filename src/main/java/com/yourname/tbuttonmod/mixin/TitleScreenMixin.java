package com.yourname.tbuttonmod.mixin;

import com.yourname.tbuttonmod.gui.DashboardScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    
    @Unique
    private static final int BUTTON_COLOR = 0xFF6B4E71;
    @Unique
    private static final int BUTTON_HOVER_COLOR = 0xFF9B7EBD;
    @Unique
    private ButtonWidget tButton;
    
    protected TitleScreenMixin(Text title) {
        super(title);
    }
    
    @Inject(method = "init", at = @At("TAIL"))
    private void addTButton(CallbackInfo ci) {
        // Create custom styled button
        this.tButton = new ButtonWidget.Builder(
            Text.literal("T"),
            button -> MinecraftClient.getInstance().setScreen(new DashboardScreen(this))
        )
        .dimensions(this.width - 45, 5, 35, 35)
        .build() {
            @Override
            public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
                // Custom rendering
                int color = this.isHovered() ? BUTTON_HOVER_COLOR : BUTTON_COLOR;
                context.fill(this.getX(), this.getY(), 
                    this.getX() + this.width, this.getY() + this.height, color);
                
                // Draw border
                context.drawBorder(this.getX(), this.getY(), this.width, this.height, 0xFFFFFFFF);
                
                // Draw T text
                context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, 
                    "T", 
                    this.getX() + this.width / 2, 
                    this.getY() + (this.height - 8) / 2, 
                    0xFFFFFF);
            }
        };
        
        this.addDrawableChild(tButton);
    }
    
    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // Optional: Add any post-render effects
    }
}
