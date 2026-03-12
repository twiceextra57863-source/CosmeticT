package com.yourname.tbuttonmod;

import net.minecraft.client.MinecraftClient;
import java.io.File;
import java.io.IOException;

public class FolderManager {
    
    public static void createFolders() {
        MinecraftClient client = MinecraftClient.getInstance();
        File gameDir = client.runDirectory;
        
        // Create main TCosmetics folder
        File tCosmeticFolder = new File(gameDir, "TCosmetics");
        if (!tCosmeticFolder.exists()) {
            tCosmeticFolder.mkdirs();
        }
        
        // Create skins subfolder
        File skinsFolder = new File(tCosmeticFolder, "skins");
        if (!skinsFolder.exists()) {
            skinsFolder.mkdirs();
        }
        
        // Create README file
        File readme = new File(tCosmeticFolder, "README.txt");
        if (!readme.exists()) {
            try {
                readme.createNewFile();
                // Write instructions
                java.nio.file.Files.write(readme.toPath(), 
                    "Place your skin PNG files in the 'skins' folder to use them in the T-Cosmetics dashboard!".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
