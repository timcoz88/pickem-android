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

package de.mtrstudios.nflpickem.UI.PlayerStatistics;

import android.app.ActionBar;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.mtrstudios.nflpickem.API.Data.Score;
import de.mtrstudios.nflpickem.Events.Error.ApiErrorEvent;
import de.mtrstudios.nflpickem.Events.Outgoing.LoadPlayerScoresEvent;
import de.mtrstudios.nflpickem.Events.Return.PlayerScoresLoadedEvent;
import de.mtrstudios.nflpickem.R;
import de.mtrstudios.nflpickem.UI.BaseFragment;

/**
 * Fragment showing detailed player statistics using a ListView with a custom adapter
 * Also handles the downloads necessary to display those statistics
 */
public class PlayerStatisticsFragment extends BaseFragment {
    private OnFragmentInteractionListener mListener;

    private PlayerStatisticsListAdapter mAdapter;
    private String mPlayerName;

    @InjectView(R.id.statsListView) ListView listView;
    @InjectView(R.id.username) TextView viewUsername;
    @InjectView(R.id.userScore) TextView viewScore;
    @InjectView(R.id.possibleMaxScore) TextView viewMaxScore;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayerStatisticsFragment.
     */
    public static PlayerStatisticsFragment newInstance(String userName) {
        PlayerStatisticsFragment fragment = new PlayerStatisticsFragment();

        // Set bundle with username
        Bundle bundle = new Bundle();
        bundle.putString(PlayerStatisticsActivity.EXTRA_USER_NAME, userName);
        fragment.setArguments(bundle);

        return fragment;
    }

    public PlayerStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPlayerName = getString(R.string.stats_username);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mPlayerName = bundle.getString(PlayerStatisticsActivity.EXTRA_USER_NAME);
        }

        // Change ActionBar title to the userName
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mPlayerName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_player_statistics, container, false);

        ButterKnife.inject(this, rootView);

        viewUsername.setText(mPlayerName);
        mAdapter = new PlayerStatisticsListAdapter((PlayerStatisticsActivity) getActivity());
        listView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        getUserScoresData();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    /**
     * Posts the event to retrieve the necessary score data that should be displayed
     */
    private void getUserScoresData() {
        mBus.post(new LoadPlayerScoresEvent(mPlayerName));
    }

    /**
     * Receives the event when player scores are downloaded
     * Adds the scores entries to the List adapter
     * Fills the UI with the data
     */
    @Subscribe
    public void onPlayerScoresLoaded(PlayerScoresLoadedEvent event) {
        Map<Integer, Score> scores = event.getScores().getScoresAsMap();

        // Set adapter data and notify data changed
        mAdapter.setSeasonInfo(event.getSeasonInfo());
        mAdapter.setPlayerName(event.getPlayerName());
        mAdapter.setScores(scores);
        mAdapter.setGamesPerWeek(event.getGamesPerWeek());

        // Set UI components
        viewScore.setText(String.valueOf(event.getScores().getTotalScore()));
        viewMaxScore.setText(String.valueOf(event.getTotalGamesPlayed()));
    }

    /**
     * Receives the event when an error occurred during an api call
     */
    @Subscribe
    public void onApiError(ApiErrorEvent event) {
        Log.e("GamesFragment", "Error with Picks");
        Log.e("ERROR", event.getError().toString());
    }

}
