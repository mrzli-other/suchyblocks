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
import com.badlogic.gdx.utils.Array;
import com.turbogerm.suchyblocks.HighScoreData;
import com.turbogerm.suchyblocks.SuchyBlocks;

public final class HighScoreScreen extends ScreenBase {
    
    private static final float HIGH_SCORE_PADDING = 10.0f;
    private static final float HIGH_SCORE_HEIGHT = 36.0f;
    private static final float HIGH_SCORE_INDEX_WIDTH = 30.0f;
    private static final float HIGH_SCORE_NAME_WIDTH = 240.0f;
    private static final float HIGH_SCORE_VALUE_WIDTH =
            SuchyBlocks.VIEWPORT_WIDTH - HIGH_SCORE_INDEX_WIDTH - HIGH_SCORE_NAME_WIDTH - 4.0f * HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_INDEX_X = HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_NAME_X =
            HIGH_SCORE_INDEX_X + HIGH_SCORE_INDEX_WIDTH + HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_VALUE_X =
            HIGH_SCORE_NAME_X + HIGH_SCORE_NAME_WIDTH + HIGH_SCORE_PADDING;
    
    public HighScoreScreen(SuchyBlocks game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener(this));
        
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.get("large-font", BitmapFont.class); // mResources.getFont("medium");
        
        // Label highScoreLabel = new Label("HIGH SCORE", mGuiSkin);
        // highScoreLabel.setBounds(0.0f, 0.0f, SuchyBlocks.VIEWPORT_WIDTH, SuchyBlocks.VIEWPORT_HEIGHT);
        // highScoreLabel.setStyle(labelStyle);
        // highScoreLabel.setAlignment(Align.center);
        // mGuiStage.addActor(highScoreLabel);
    }
    
    @Override
    public void show() {
        super.show();
        
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.get("large-font", BitmapFont.class); // mResources.getFont("medium");
        
        Array<HighScoreData> highScores = mGameData.getHighScoresData().getHighScores();
        for (int i = 0; i < highScores.size; i++) {
            float highScoreY = SuchyBlocks.VIEWPORT_HEIGHT - (i + 1) * HIGH_SCORE_HEIGHT;
            HighScoreData highScore = highScores.get(i);
            
            Label highScoreIndexLabel = new Label(String.valueOf(i + 1) + ".", mGuiSkin);
            highScoreIndexLabel.setBounds(HIGH_SCORE_INDEX_X, highScoreY, HIGH_SCORE_INDEX_WIDTH, HIGH_SCORE_HEIGHT);
            highScoreIndexLabel.setStyle(labelStyle);
            highScoreIndexLabel.setAlignment(Align.right);
            mGuiStage.addActor(highScoreIndexLabel);
            
            Label highScoreNameLabel = new Label(highScore.getName(), mGuiSkin);
            highScoreNameLabel.setBounds(HIGH_SCORE_NAME_X, highScoreY, HIGH_SCORE_NAME_WIDTH, HIGH_SCORE_HEIGHT);
            highScoreNameLabel.setStyle(labelStyle);
            highScoreNameLabel.setAlignment(Align.left);
            mGuiStage.addActor(highScoreNameLabel);
            
            Label highScoreValueLabel = new Label(String.valueOf(highScore.getScore()), mGuiSkin);
            highScoreValueLabel.setBounds(HIGH_SCORE_VALUE_X, highScoreY, HIGH_SCORE_VALUE_WIDTH, HIGH_SCORE_HEIGHT);
            highScoreValueLabel.setStyle(labelStyle);
            highScoreValueLabel.setAlignment(Align.right);
            mGuiStage.addActor(highScoreValueLabel);
        }
    }
    
    private static InputListener getStageInputListener(final HighScoreScreen screen) {
        
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    screen.mGame.setScreen(SuchyBlocks.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
        };
    }
}
