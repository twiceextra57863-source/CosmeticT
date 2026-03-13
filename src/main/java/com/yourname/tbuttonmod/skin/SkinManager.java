package com.yourname.tbuttonmod.skin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class SkinManager {
    private static List<SkinEntry> skins = new ArrayList<>();
    private static File skinFolder;
    
    public static void initialize() {
        MinecraftClient client = MinecraftClient.getInstance();
        File gameDir = client.runDirectory;
        skinFolder = new File(gameDir, "TCosmetics/skins");
        
        if (!skinFolder.exists()) {
            skinFolder.mkdirs();
        }
        
        loadSkins();
    }
    
    public static void loadSkins() {
        skins.clear();
        File[] files = skinFolder.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".png") || 
            name.toLowerCase().endsWith(".jpg") || 
            name.toLowerCase().endsWith(".jpeg"));
        
        if (files != null) {
            for (File file : files) {
                try {
                    // Create a safe identifier
                    String fileName = file.getName().replaceAll("[^a-zA-Z0-9.-]", "_");
                    Identifier id = Identifier.of("tbuttonmod", "skins/" + fileName);
                    
                    // Load image
                    try (FileInputStream fis = new FileInputStream(file)) {
                        NativeImage image = NativeImage.read(fis);
                        NativeImageBackedTexture texture = new NativeImageBackedTexture(image);
                        MinecraftClient.getInstance().getTextureManager().registerTexture(id, texture);
                        
                        skins.add(new SkinEntry(file.getName(), id));
                        System.out.println("Loaded skin: " + file.getName());
                    }
                } catch (Exception e) {
                    System.err.println("Failed to load skin: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
        
        System.out.println("Loaded " + skins.size() + " skins");
    }
    
    public static List<SkinEntry> getSkins() {
        return skins;
    }
    
    public static class SkinEntry {
        private final String name;
        private final Identifier texture;
        
        public SkinEntry(String name, Identifier texture) {
            this.name = name;
            this.texture = texture;
        }
        
        public String getName() { return name; }
        public Identifier getTexture() { return texture; }
    }
}
