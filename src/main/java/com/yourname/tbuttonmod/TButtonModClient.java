package com.yourname.tbuttonmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TButtonModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TButtonMod.LOGGER.info("TButtonMod Client Initialized!");
    }
}