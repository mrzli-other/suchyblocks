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

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.turbogerm.suchyblocks.SuchyBlocks;

public final class GameOverScreen extends ScreenBase {
    
    private final Label mGameOverLabel;
    
    public GameOverScreen(SuchyBlocks game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener(this));
        
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.get("xxxl-font", BitmapFont.class);
        
        mGameOverLabel = new Label("", mGuiSkin);
        mGameOverLabel.setBounds(0.0f, 0.0f, SuchyBlocks.VIEWPORT_WIDTH, SuchyBlocks.VIEWPORT_HEIGHT);
        mGameOverLabel.setStyle(labelStyle);
        mGameOverLabel.setAlignment(Align.center);
        mGuiStage.addActor(mGameOverLabel);
    }
    
    @Override
    public void show() {
        super.show();
        
        String gameOverText = String.format("Game over!\nYour score is:\n%d", mGameData.getScore());
        mGameOverLabel.setText(gameOverText);
    }
    
    private static InputListener getStageInputListener(final GameOverScreen loseGameScreen) {
        
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    loseGameScreen.mGame.setScreen(SuchyBlocks.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
        };
    }
}
