package com.yourname.tbuttonmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TButtonMod implements ModInitializer {
    public static final String MOD_ID = "tbuttonmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    @Override
    public void onInitialize() {
        LOGGER.info("TButton Mod initializing...");
        FolderManager.createFolders();
        LOGGER.info("TButton Mod initialized!");
    }
}
