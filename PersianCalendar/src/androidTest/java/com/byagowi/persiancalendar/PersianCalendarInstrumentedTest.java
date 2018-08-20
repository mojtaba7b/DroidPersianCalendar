package com.byagowi.persiancalendar;

import android.content.ClipboardManager;
import android.content.Context;

import com.byagowi.persiancalendar.util.CalendarUtils;
import com.byagowi.persiancalendar.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PersianCalendarInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity;

    @Before
    public void setActivity() {
        mainActivity = mainActivityTestRule.getActivity();
    }

    @Test
    public void test_if_date_copy_works() throws ExecutionException, InterruptedException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.byagowi.persiancalendar", appContext.getPackageName());

        FutureTask<ClipboardManager> futureResult = new FutureTask<>(() -> (ClipboardManager)
                getInstrumentation().getTargetContext().getApplicationContext()
                        .getSystemService(Context.CLIPBOARD_SERVICE));

        mainActivity.runOnUiThread(futureResult);

        ClipboardManager clipboardManager = futureResult.get();

        onView(withId(R.id.first_calendar_date)).perform(click());
        assertEquals(CalendarUtils.dateToString(CalendarUtils.getPersianToday()),
                clipboardManager.getPrimaryClip().getItemAt(0).getText());
    }
}
