package com.yourname.tbuttonmod;

import net.minecraft.client.MinecraftClient;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FolderManager {
    
    public static void createFolders() {
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            File gameDir = client.runDirectory;
            
            File tCosmeticFolder = new File(gameDir, "TCosmetics");
            if (!tCosmeticFolder.exists()) {
                tCosmeticFolder.mkdirs();
            }
            
            File skinsFolder = new File(tCosmeticFolder, "skins");
            if (!skinsFolder.exists()) {
                skinsFolder.mkdirs();
            }
            
            // Create README
            File readme = new File(tCosmeticFolder, "README.txt");
            if (!readme.exists()) {
                String content = "TButton Mod - Skin Installation Guide\n" +
                               "====================================\n\n" +
                               "1. Place your skin PNG files in the 'skins' folder\n" +
                               "2. Launch Minecraft and click the T button\n\n" +
                               "Folder location: " + skinsFolder.getAbsolutePath();
                Files.write(readme.toPath(), content.getBytes());
            }
            
            TButtonMod.LOGGER.info("TCosmetics folders created at: " + tCosmeticFolder.getAbsolutePath());
        } catch (Exception e) {
            TButtonMod.LOGGER.error("Failed to create folders", e);
        }
    }
}
