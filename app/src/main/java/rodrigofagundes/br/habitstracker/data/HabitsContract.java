package rodrigofagundes.br.habitstracker.data;

import android.provider.BaseColumns;

/**
 * Created by rmfagundes on 21/11/2017.
 */

public final class HabitsContract {
    private HabitsContract() {}

    public static final class HabitsEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";

        public static final String _ID = "_id";
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_DURATION_MINUTES = "duration_minutes";
    }
}
