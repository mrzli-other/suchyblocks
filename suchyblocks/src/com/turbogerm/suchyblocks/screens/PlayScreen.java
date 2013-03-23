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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.turbogerm.suchyblocks.GameArea;
import com.turbogerm.suchyblocks.SuchyBlocks;

public final class PlayScreen extends ScreenBase {
    
    private final GameArea mGameArea;
    
    public PlayScreen(SuchyBlocks game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener(this));
        
        mGameArea = new GameArea(mAssetManager, mBatch);
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
        mGameArea.render();
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
                    //mGameArea.moveHorizontal(-1);
                    return true;
                }
                
                return false;
            }
//            
//            @Override
//            public void touchDragged(InputEvent event, float x, float y, int pointer) {
//                if (mGameAreaLeftPressed) {
//                    playScreen.mGameMap.changeMapOffset(mLastTouchPoint.x - x, mLastTouchPoint.y - y);
//                    mLastTouchPoint.x = x;
//                    mLastTouchPoint.y = y;
//                    return;
//                }
//            }
//            
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                // map movement and tile select should be distinct actions,
//                // so select tile only if there was no significant map movement
//                if (Math.abs(x - mInitialTouchPoint.x) < MAP_MOVE_THRESHOLD &&
//                        Math.abs(y - mInitialTouchPoint.y) < MAP_MOVE_THRESHOLD) {
//                    playScreen.mGameMap.selectTile(x, y);
//                }
//                
//                mGameAreaLeftPressed = false;
//            }
        };
    }
}