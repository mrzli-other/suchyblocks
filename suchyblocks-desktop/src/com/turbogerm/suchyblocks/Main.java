package com.turbogerm.suchyblocks;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "SuchyBlocks";
        cfg.useGL20 = true;
        cfg.height = 800;
        cfg.width = 450;
        cfg.resizable = false;
        
        new LwjglApplication(new SuchyBlocks(), cfg);
    }
}
