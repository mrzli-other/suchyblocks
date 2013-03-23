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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.suchyblocks.tetrominos.Tetromino;
import com.turbogerm.suchyblocks.tetrominos.TetrominoRotationsReader;
import com.turbogerm.suchyblocks.util.IntPair;

public final class GameArea {
    
    public static final float SQUARE_SIZE = 40.0f;
    public static final int GAME_AREA_ROWS = 20;
    public static final int GAME_AREA_COLUMNS = 10;
    
    private static final float SOFT_DROP_SPEED = 20.0f;
    private static final float REPEAT_START_OFFSET = 0.5f;
    private static final float REPEAT_INTERVAL = 0.05f;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    
    private final Texture[] mSquareTextures;
    private final Tetromino[] mTetrominos;
    
    private int mActiveTetromino;
    
    private final int[][] mGameAreaSquares;
    private Rectangle mGameAreaRect;
    
    private int mScore;
    private int mLines;
    private int mLevel;
    private int mNextTetromino;
    
    private float mSpeed;
    private float mDistanceRemainder;
    
    private boolean mIsSoftDrop;
    
    private boolean mIsRotating;
    private float mRotatingCountdown;
    private boolean mIsMovingLeft;
    private float mMovingLeftCountdown; 
    private boolean mIsMovingRight;
    private float mMovingRightCountdown;
    
    public GameArea(AssetManager assetManager, SpriteBatch batch) {
        
        mAssetManager = assetManager;
        
        mBatch = batch;
        
        mSquareTextures = new Texture[Tetromino.COUNT];
        for (int i = 0; i < Tetromino.COUNT; i++) {
            mSquareTextures[i] = mAssetManager.get(Tetromino.getTexturePath(i));
        }
        
        IntPair[][][] tetrominoRotations = TetrominoRotationsReader.read(
                Gdx.files.internal(ResourceNames.TETROMINO_ROTATIONS_DATA));
        mTetrominos = new Tetromino[Tetromino.COUNT];
        for (int i = 0; i < Tetromino.COUNT; i++) {
            mTetrominos[i] = new Tetromino(tetrominoRotations[i], mSquareTextures[i], i);
        }
        
        mGameAreaSquares = new int[GAME_AREA_ROWS][GAME_AREA_COLUMNS];
        reset();
    }
    
    public void reset() {
        
        for (int i = 0; i < GAME_AREA_ROWS; i++) {
            for (int j = 0; j < GAME_AREA_COLUMNS; j++) {
                mGameAreaSquares[i][j] = -1;
            }
        }
        
        mScore = 0;
        setLines(0);
        
        mNextTetromino = MathUtils.random(Tetromino.COUNT - 1);
        mActiveTetromino = -1;
        
        nextTetromino();
    }
    
    public void update(float delta) {
        
        if (mIsRotating) {
            mRotatingCountdown -= delta;
            if (mRotatingCountdown <= 0.0f) {
                rotate();
                mRotatingCountdown += REPEAT_INTERVAL;
            }
        }
        
        if (mIsMovingLeft && !mIsMovingRight) {
            mMovingLeftCountdown -= delta;
            if (mMovingLeftCountdown <= 0.0f) {
                moveHorizontal(-1);
                mMovingLeftCountdown += REPEAT_INTERVAL;
            }
        }
        
        if (mIsMovingRight && !mIsMovingLeft) {
            mMovingRightCountdown -= delta;
            if (mMovingRightCountdown <= 0.0f) {
                moveHorizontal(1);
                mMovingRightCountdown += REPEAT_INTERVAL;
            }
        }
        
        float speed = mIsSoftDrop ? SOFT_DROP_SPEED : mSpeed;
        mDistanceRemainder += delta * speed;
        if (mDistanceRemainder >= 1.0f) {
            int distance = (int) mDistanceRemainder;
            
            if (!getActiveTetromino().moveDown(distance, mGameAreaSquares)) {
                nextTetromino();
            }
            
            mDistanceRemainder -= distance;
        }
    }
    
    public void render() {
        mBatch.begin();
        
        for (int i = 0; i < GAME_AREA_ROWS; i++) {
            float squareY = i * SQUARE_SIZE;
            for (int j = 0; j < GAME_AREA_COLUMNS; j++) {
                int squareIndex = mGameAreaSquares[i][j];
                if (squareIndex >= 0) {
                    float squareX = j * SQUARE_SIZE;
                    mBatch.draw(mSquareTextures[squareIndex],
                            squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }
        
        if (mActiveTetromino >= 0) {
            getActiveTetromino().render(mBatch);
        }
        
        mBatch.end();
    }
    
    public void startRotate() {
        rotate();
        mIsRotating = true;
        mRotatingCountdown = REPEAT_START_OFFSET;
    }
    
    public void endRotate() {
        mIsRotating = false;
        mRotatingCountdown = 0.0f;
    }
    
    private void rotate() {
        if (mActiveTetromino >= 0) {
            getActiveTetromino().rotate(mGameAreaSquares);
        }
    }
    
    public void startMoveHorizontal(boolean isLeft) {
        moveHorizontal(isLeft ? -1 : 1);
        if (isLeft) {
            mIsMovingLeft = true;
            mMovingLeftCountdown = REPEAT_START_OFFSET;
        } else {
            mIsMovingRight = true;
            mMovingRightCountdown = REPEAT_START_OFFSET;
        }
    }
    
    public void endMoveHorizontal(boolean isLeft) {
        if (isLeft) {
            mIsMovingLeft = false;
            mMovingLeftCountdown = 0.0f;
        } else {
            mIsMovingRight = false;
            mMovingRightCountdown = 0.0f;
        }
    }
    
    private void moveHorizontal(int distance) {
        if (mActiveTetromino >= 0) {
            getActiveTetromino().moveHorizontal(distance, mGameAreaSquares);
        }
    }
    
    public void setSoftDrop(boolean isSoftDrop) {
        mIsSoftDrop = isSoftDrop;
    }
    
    private void setLines(int lines) {
        mLines = lines;
        mLevel = 1 + mLines / 10;
        mSpeed = 2.0f + (mLevel - 1) * 0.5f;
    }
    
    public void nextTetromino() {
        mIsSoftDrop = false;
        
        endRotate();
        endMoveHorizontal(true);
        endMoveHorizontal(false);
        
        if (mActiveTetromino >= 0) {
            getActiveTetromino().applySquares(mGameAreaSquares);
            getActiveTetromino().reset();
        }
        
        mActiveTetromino = mNextTetromino;
        mDistanceRemainder = 0.0f;
        mNextTetromino = MathUtils.random(Tetromino.COUNT - 1);
    }
    
    private Tetromino getActiveTetromino() {
        return mTetrominos[mActiveTetromino];
    }
}
