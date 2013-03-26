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
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;

public final class HighScores {
    
    private static final String PREFERENCES_NAME = "SuchyBlocks_HighScores";
    
    private static final int HIGH_SCORES_CAPACITY = 16;
    private static final int NUM_HIGH_SCORES = 10;
    
    private static final String DEFAULT_NAME = "<No_Name>";
    
    private final Array<HighScore> mHighScores;
    private final Preferences mPreferences;
    
    public HighScores() {
        mHighScores = new Array<HighScore>(true, HIGH_SCORES_CAPACITY);
        
        mPreferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        loadHighScores();
    }
    
    public void save() {
        saveHighScores();
        mPreferences.flush();
    }
    
    private void loadHighScores() {
        mHighScores.clear();
        
        for (int i = 0; i < NUM_HIGH_SCORES; i++) {
            String name = getScoreName(i);
            if (name == null || name.equals("")) {
                name = DEFAULT_NAME;
            }
            int score = getScoreValue(i);
            
            HighScore highScore = new HighScore(name, score, 0l);
            mHighScores.add(highScore);
        }
    }
    
    private void saveHighScores() {
        for (int i = 0; i < NUM_HIGH_SCORES; i++) {
            HighScore highScore = mHighScores.get(i);
            putScoreName(i, highScore.getName());
            putScoreValue(i, highScore.getScore());
        }
    }
    
    private String getScoreName(int scoreIndex) {
        String key = getScoreNameKey(scoreIndex);
        return mPreferences.getString(key);
    }
    
    private void putScoreName(int scoreIndex, String scoreName) {
        String key = getScoreNameKey(scoreIndex);
        mPreferences.putString(key, scoreName);
    }
    
    private String getScoreNameKey(int scoreIndex) {
        return String.format("ScoreName%02d", scoreIndex + 1);
    }
    
    private int getScoreValue(int scoreIndex) {
        String key = getScoreValueKey(scoreIndex);
        return mPreferences.getInteger(key);
    }
    
    private void putScoreValue(int scoreIndex, int scoreValue) {
        String key = getScoreValueKey(scoreIndex);
        mPreferences.putInteger(key, scoreValue);
    }
    
    private String getScoreValueKey(int scoreIndex) {
        return String.format("ScoreValue%02d", scoreIndex + 1);
    }
}
