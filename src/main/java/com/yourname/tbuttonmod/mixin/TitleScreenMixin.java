package com.yourname.tbuttonmod.mixin;

import com.yourname.tbuttonmod.gui.DashboardScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "init()V")
    private void addTButton(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(Text.literal("T"), button -> {
            if (this.client != null) {
                this.client.setScreen(new DashboardScreen(this));
            }
        }).dimensions(2, 2, 20, 20).build());
    }
}