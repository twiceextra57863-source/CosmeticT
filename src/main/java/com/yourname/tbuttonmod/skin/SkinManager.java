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
            name.endsWith(".png") || name.endsWith(".jpg"));
        
        if (files != null) {
            for (File file : files) {
                try {
                    Identifier id = Identifier.of("tbuttonmod", "skins/" + file.getName());
                    NativeImage image = NativeImage.read(new FileInputStream(file));
                    NativeImageBackedTexture texture = new NativeImageBackedTexture(image);
                    MinecraftClient.getInstance().getTextureManager().registerTexture(id, texture);
                    
                    skins.add(new SkinEntry(file.getName(), id));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static List<SkinEntry> getSkins() {
        return skins;
    }
    
    public static class SkinEntry {
        private String name;
        private Identifier texture;
        
        public SkinEntry(String name, Identifier texture) {
            this.name = name;
            this.texture = texture;
        }
        
        public String getName() { return name; }
        public Identifier getTexture() { return texture; }
    }
}
