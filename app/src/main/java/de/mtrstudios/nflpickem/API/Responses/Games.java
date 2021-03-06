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

import java.util.Collections;
import java.util.List;

import de.mtrstudios.nflpickem.API.Data.Game;
import de.mtrstudios.nflpickem.API.Data.Comparators.GamesComparator;

/**
 * Stores the appData about games - received as a response from the server
 */
public class Games {
    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    /**
     * Sorts the list of games and returns it
     */
    public List<Game> getSortedGames() {
        List<Game> sortedGames = games;

        Collections.sort(sortedGames, new GamesComparator());

        return sortedGames;
    }
}
