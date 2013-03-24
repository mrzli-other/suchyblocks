/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.turbogerm.suchyblocks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.turbogerm.suchyblocks.SuchyBlocks;

public final class MainMenuScreen extends ScreenBase {
    
    private static final float BUTTON_WIDTH = SuchyBlocks.VIEWPORT_WIDTH * 0.8f;
    private static final float BUTTON_HEIGHT = SuchyBlocks.VIEWPORT_HEIGHT * 0.1f;
    private static final float BUTTON_PADDING = SuchyBlocks.VIEWPORT_HEIGHT * 0.1f;
    
    public MainMenuScreen(SuchyBlocks game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener());
        
        TextButtonStyle menuTextButtonStyle = new TextButtonStyle(mGuiSkin.get(TextButtonStyle.class));
        menuTextButtonStyle.font = mGuiSkin.get("xxxl-font", BitmapFont.class);
        
        TextButton startButton = new TextButton("START", mGuiSkin);
        startButton.setStyle(menuTextButtonStyle);
        startButton.addListener(getStartInputListener(startButton));
        
        TextButton highScoreButton = new TextButton("HIGH SCORE", mGuiSkin);
        highScoreButton.setStyle(menuTextButtonStyle);
        highScoreButton.addListener(getHighScoreListener(highScoreButton));
        
        TextButton infoButton = new TextButton("INFO", mGuiSkin);
        infoButton.setStyle(menuTextButtonStyle);
        infoButton.addListener(getCreditsListener(infoButton));
        
        Table table = new Table();
        table.setFillParent(true);
        table.add(startButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(BUTTON_PADDING);
        table.row();
        table.add(highScoreButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(BUTTON_PADDING);
        table.row();
        table.add(infoButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        
        mGuiStage.addActor(table);
    }
    
    private InputListener getStageInputListener() {
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    Gdx.app.exit();
                    return true;
                }
                
                return false;
            }
        };
    }
    
    private InputListener getStartInputListener(final Actor actor) {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    mGame.setScreen(SuchyBlocks.PLAY_SCREEN_NAME);
                }
            }
        };
    }
    
    private InputListener getHighScoreListener(final Actor actor) {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    mGame.setScreen(SuchyBlocks.HIGH_SCORE_SCREEN_NAME);
                }
            }
        };
    }
    
    private InputListener getCreditsListener(final Actor actor) {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    mGame.setScreen(SuchyBlocks.INFO_SCREEN_NAME);
                }
            }
        };
    }
}