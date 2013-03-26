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
/*
 * Copyright 2012 Gustavo Steigert
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// I made modifications to Gustavo Steigert's AbstractScreen class
// http://steigert.blogspot.com/2012/02/2-libgdx-tutorial-game-screens.html
package com.turbogerm.suchyblocks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.turbogerm.suchyblocks.GameData;
import com.turbogerm.suchyblocks.Resources;
import com.turbogerm.suchyblocks.SuchyBlocks;

public abstract class ScreenBase implements Screen {
    
    protected final SuchyBlocks mGame;
    protected final Resources mResources;
    protected final AssetManager mAssetManager;
    protected final Skin mGuiSkin;
    protected final GameData mGameData;
    
    protected final SpriteBatch mBatch;
    protected final Stage mGuiStage;
    
    protected Color mClearColor;
    
    public ScreenBase(SuchyBlocks game) {
        mGame = game;
        mResources = mGame.getResources();
        mAssetManager = mResources.getAssetManager();
        mGuiSkin = mResources.getGuiSkin();
        mGameData = mGame.getGameData();
        
        mBatch = new SpriteBatch();
        mBatch.getProjectionMatrix().setToOrtho2D(0.0f, 0.0f,
                SuchyBlocks.VIEWPORT_WIDTH, SuchyBlocks.VIEWPORT_HEIGHT);
        
        mGuiStage = new Stage(SuchyBlocks.VIEWPORT_WIDTH, SuchyBlocks.VIEWPORT_HEIGHT, false);
    }
    
    protected String getName() {
        return getClass().getSimpleName();
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(mGuiStage);
    }
    
    @Override
    public void resize(int width, int height) {
    }
    
    @Override
    public void render(float delta) {
        
        if (mClearColor != null) {
            Gdx.gl.glClearColor(mClearColor.r, mClearColor.g, mClearColor.b, mClearColor.a);
        } else {
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        }
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        renderImpl(delta);
        
        mGuiStage.act(delta);
        mGuiStage.draw();
    }
    
    public void renderImpl(float delta) {
    }
    
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    
    @Override
    public void pause() {
    }
    
    @Override
    public void resume() {
    }
    
    @Override
    public void dispose() {
        mGuiStage.dispose();
        mBatch.dispose();
    }
    
}