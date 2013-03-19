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

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.turbogerm.suchyblocks.ResourceNames;
import com.turbogerm.suchyblocks.SuchyBlocks;

public final class SplashScreen extends ScreenBase {
    
    public SplashScreen(SuchyBlocks game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener());
        
        Texture splashTexture = mAssetManager.get(ResourceNames.GUI_SPLASH_TEXTURE);
        
        Image splashImage = new Image(splashTexture);
        splashImage.setWidth(SuchyBlocks.VIEWPORT_WIDTH);
        splashImage.setHeight(SuchyBlocks.VIEWPORT_HEIGHT);
        splashImage.setColor(1.0f, 1.0f, 1.0f, 0.0f);
        SequenceAction action = Actions.sequence(
                Actions.fadeIn(1.5f), Actions.delay(1.5f), Actions.fadeOut(1.5f), getCompletedAction());
        splashImage.addAction(action);
        
        mGuiStage.addActor(splashImage);
    }
    
    private Action getCompletedAction() {
        return new Action() {
            
            @Override
            public boolean act(float delta) {
                mGame.setScreen(SuchyBlocks.MAIN_MENU_SCREEN_NAME);
                return true;
            }
        };
    }
    
    private InputListener getStageInputListener() {
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ENTER || keycode == Keys.SPACE || keycode == Keys.ESCAPE) {
                    mGame.setScreen(SuchyBlocks.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Buttons.LEFT || button == Buttons.RIGHT) {
                    mGame.setScreen(SuchyBlocks.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
        };
    }
}