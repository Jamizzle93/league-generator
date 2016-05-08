package com.mysticwater.leaguegenerator;

import android.provider.BaseColumns;

public final class LeagueContract {

    public LeagueContract() {}

    public static abstract class LeagueEntry implements BaseColumns {
        public static final String TABLE_NAME = "league";
        public static final String COLUMN_NAME_LEAGUE_ID = "leagieid";
        public static final String COLUMN_NAME_LEAGUE_NAME = "name";
        public static final String COLUMN_NAME_TEAM_TABLE_ID = "teamtable";
    }

    public static abstract class TeamEntry implements BaseColumns {
        public static final String TABLE_NAME = "team";
        public static final String COLUMN_NAME_TEAM_ID = "teamid";
        public static final String COLUMN_NAME_TEAM_NAME = "teamname";
        public static final String COLUMN_NAME_TEAM_POINTS = "points";
        public static final String COLUMN_NAME_TEAM_WIN = "win";
        public static final String COLUMN_NAME_TEAM_DRAW = "draw";
        public static final String COLUMN_NAME_TEAM_LOSS = "loss";
        public static final String COLUMN_NAME_TEAM_SCORE_FOR = "scorefor";
        public static final String COLUMN_NAME_TEAM_SCORE_AGAINST = "scoreagainst";
    }
}
