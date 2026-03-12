package com.yourname.tbuttonmod;

import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.nio.file.Path;

public class FolderManager {
    public static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("tbuttonmod");

    public static void init() {
        File configFolder = CONFIG_DIR.toFile();
        if (!configFolder.exists()) {
            if (configFolder.mkdirs()) {
                TButtonMod.LOGGER.info("Created config directory for TButtonMod.");
            }
        }
    }
}