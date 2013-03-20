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
package com.turbogerm.suchyblocks;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public final class GameArea {
    
    private static final float SQUARE_SIZE = 40.0f;
    private static final int GAME_AREA_ROWS = 20;
    private static final int GAME_AREA_COLUMNS = 10;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    
    private final Texture[] mSquareTextures;
    private final int[][] mGameAreaSquares;
    private Rectangle mGameAreaRect;
    
    public GameArea(AssetManager assetManager, SpriteBatch batch) {
        
        mAssetManager = assetManager;
        
        mBatch = batch;
        
        mSquareTextures = new Texture[7];
        mSquareTextures[0] = mAssetManager.get(ResourceNames.SQUARES_CYAN_TEXTURE);
        mSquareTextures[1] = mAssetManager.get(ResourceNames.SQUARES_PURPLE_TEXTURE);
        mSquareTextures[2] = mAssetManager.get(ResourceNames.SQUARES_ORANGE_TEXTURE);
        mSquareTextures[3] = mAssetManager.get(ResourceNames.SQUARES_BLUE_TEXTURE);
        mSquareTextures[4] = mAssetManager.get(ResourceNames.SQUARES_RED_TEXTURE);
        mSquareTextures[5] = mAssetManager.get(ResourceNames.SQUARES_GREEN_TEXTURE);
        mSquareTextures[6] = mAssetManager.get(ResourceNames.SQUARES_YELLOW_TEXTURE);
        
        mGameAreaSquares = new int[GAME_AREA_ROWS][GAME_AREA_COLUMNS];
        reset();
    }
    
    public void reset() {
        for (int i = 0; i < GAME_AREA_ROWS; i++) {
            for (int j = 0; j < GAME_AREA_COLUMNS; j++) {
                mGameAreaSquares[i][j] = MathUtils.random(6);
            }
        }
    }
    
    public void update(float delta) {
    }
    
    public void render() {
        mBatch.begin();
        
        for (int i = 0; i < GAME_AREA_ROWS; i++) {
            float squareY = i * SQUARE_SIZE;
            for (int j = 0; j < GAME_AREA_COLUMNS; j++) {
                float squareX = j * SQUARE_SIZE; 
                int squareIndex = mGameAreaSquares[i][j];
                mBatch.draw(mSquareTextures[squareIndex],
                        squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
        
        mBatch.end();
    }
}
