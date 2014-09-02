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

package de.mtrstudios.nflpickem.API.Responses;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.mtrstudios.nflpickem.API.Data.Score;

/**
 * Stores appData about a users scores - received as a response from the server
 */
public class Scores {
    private List<Score> scores;

    public List<Score> getScores() {
        return scores;
    }

    /**
     * Returns the scores as a Map
     * The week number acts as the key and references to the score appData
     */
    public Map<Integer, Score> getScoresAsMap() {
        Map<Integer, Score> scoreMap = new TreeMap<Integer, Score>();

        if (scores != null) {
            for (Score score : scores) {
                scoreMap.put(score.getWeek(), score);
            }
        }

        return scoreMap;
    }
}
