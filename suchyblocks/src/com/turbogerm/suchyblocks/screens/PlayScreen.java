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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.turbogerm.suchyblocks.GameArea;
import com.turbogerm.suchyblocks.ResourceNames;
import com.turbogerm.suchyblocks.SuchyBlocks;

public final class PlayScreen extends ScreenBase {
    
    private static final float GAME_AREA_BORDER_SIZE = 10.0f;
    private static final float NEXT_SQUARE_SIZE = 20.0f;
    private static final float NEXT_DISPLAY_SIZE = NEXT_SQUARE_SIZE * 4.0f;
    
    private final GameArea mGameArea;
    private final Rectangle mGameAreaRectangle;
    
    private final Texture mBackgroundTexture;
    private final Texture mGameAreaBorderTexture;
    private final Texture mGameAreaBackgroundTexture;
    
    private final Vector2 mNextDisplayPosition;
    
    public PlayScreen(SuchyBlocks game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener(this));
        
        Vector2 gameAreaPosition = new Vector2(GAME_AREA_BORDER_SIZE, 100.0f - GAME_AREA_BORDER_SIZE);
        mGameArea = new GameArea(mAssetManager, mBatch, gameAreaPosition);
        mGameAreaRectangle = mGameArea.getGameAreaRectangle();
        
        mBackgroundTexture = mAssetManager.get(ResourceNames.GUI_BACKGROUND_TEXTURE);
        mGameAreaBorderTexture = mAssetManager.get(ResourceNames.GUI_GAME_AREA_BORDER_TEXTURE);
        mGameAreaBackgroundTexture = mAssetManager.get(ResourceNames.GUI_GAME_AREA_BACKGROUND_TEXTURE);
        
        float leftHorizontalSpace = SuchyBlocks.VIEWPORT_WIDTH - mGameAreaRectangle.width -
                2.0f * GAME_AREA_BORDER_SIZE;
        float nextDisplayPositionX = SuchyBlocks.VIEWPORT_WIDTH - leftHorizontalSpace / 2.0f - NEXT_DISPLAY_SIZE / 2.0f;
        float nextDisplayPositionY = mGameAreaRectangle.y + mGameAreaRectangle.height - NEXT_DISPLAY_SIZE;
        mNextDisplayPosition = new Vector2(nextDisplayPositionX, nextDisplayPositionY);
    }
    
    @Override
    public void show() {
        super.show();
        mGameArea.reset();
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
    }
    
    @Override
    public void renderScene(float delta) {
        mGameArea.update(delta);
        
        mBatch.begin();
        
        mBatch.draw(mBackgroundTexture, 0.0f, 0.0f, SuchyBlocks.VIEWPORT_WIDTH, SuchyBlocks.VIEWPORT_HEIGHT);
        mBatch.draw(mGameAreaBorderTexture,
                mGameAreaRectangle.x - GAME_AREA_BORDER_SIZE,
                mGameAreaRectangle.y - GAME_AREA_BORDER_SIZE,
                mGameAreaRectangle.width + 2.0f * GAME_AREA_BORDER_SIZE,
                mGameAreaRectangle.height + 2.0f * GAME_AREA_BORDER_SIZE);
        mBatch.draw(mGameAreaBackgroundTexture, mGameAreaRectangle.x, mGameAreaRectangle.y,
                mGameAreaRectangle.width, mGameAreaRectangle.height);
        
        mGameArea.render();
        
        mBatch.draw(mGameAreaBorderTexture,
                mNextDisplayPosition.x - GAME_AREA_BORDER_SIZE,
                mNextDisplayPosition.y - GAME_AREA_BORDER_SIZE,
                NEXT_DISPLAY_SIZE + 2.0f * GAME_AREA_BORDER_SIZE,
                NEXT_DISPLAY_SIZE + 2.0f * GAME_AREA_BORDER_SIZE);
        mGameArea.renderNext(mNextDisplayPosition, NEXT_SQUARE_SIZE);
        
        mBatch.end();
    }
    
    @Override
    public void hide() {
        super.hide();
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }
    
    private InputListener getStageInputListener(final PlayScreen screen) {
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    screen.mGame.setScreen(SuchyBlocks.MAIN_MENU_SCREEN_NAME);
                    return true;
                } else if (keycode == Keys.UP) {
                    mGameArea.startRotate();
                    return true;
                } else if (keycode == Keys.LEFT) {
                    mGameArea.startMoveHorizontal(true);
                    return true;
                } else if (keycode == Keys.RIGHT) {
                    mGameArea.startMoveHorizontal(false);
                    return true;
                } else if (keycode == Keys.DOWN) {
                    mGameArea.setSoftDrop(true);
                    return true;
                }
                
                return false;
            }
            
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Keys.UP) {
                    mGameArea.endRotate();
                    return true;
                } else if (keycode == Keys.LEFT) {
                    mGameArea.endMoveHorizontal(true);
                    return true;
                } else if (keycode == Keys.RIGHT) {
                    mGameArea.endMoveHorizontal(false);
                    return true;
                } else if (keycode == Keys.DOWN) {
                    mGameArea.setSoftDrop(false);
                    return true;
                }
                
                return false;
            }
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Buttons.LEFT) {
                    // mGameArea.moveHorizontal(-1);
                    return true;
                }
                
                return false;
            }
            //
            // @Override
            // public void touchDragged(InputEvent event, float x, float y, int pointer) {
            // if (mGameAreaLeftPressed) {
            // playScreen.mGameMap.changeMapOffset(mLastTouchPoint.x - x, mLastTouchPoint.y - y);
            // mLastTouchPoint.x = x;
            // mLastTouchPoint.y = y;
            // return;
            // }
            // }
            //
            // @Override
            // public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            // // map movement and tile select should be distinct actions,
            // // so select tile only if there was no significant map movement
            // if (Math.abs(x - mInitialTouchPoint.x) < MAP_MOVE_THRESHOLD &&
            // Math.abs(y - mInitialTouchPoint.y) < MAP_MOVE_THRESHOLD) {
            // playScreen.mGameMap.selectTile(x, y);
            // }
            //
            // mGameAreaLeftPressed = false;
            // }
        };
    }
}