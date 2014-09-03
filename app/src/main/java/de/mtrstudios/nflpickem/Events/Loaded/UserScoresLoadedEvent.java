/*
 * Copyright 2014 MTRamin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mtrstudios.nflpickem.Events.Loaded;

/**
 * TODO Change class comment
 */
public class UserScoresLoadedEvent {
    private final String playerName;
    private final int score;
    private final int maxScore;

    public UserScoresLoadedEvent(String playerName, int score, int maxScore) {
        this.playerName = playerName;
        this.score = score;
        this.maxScore = maxScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public int getMaxScore() {
        return maxScore;
    }
}
