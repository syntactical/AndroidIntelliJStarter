package com.example.android.sampleapp.test.support;

import com.example.android.sampleapp.MySampleApplication;
import com.example.android.sampleapp.StarterActivity;
import com.example.android.sampleapp.util.CurrentTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class InjectMockAnnotationsTest {
    @InjectMock
    CurrentTime mockCurrentTime;

    private void injectMocks() {
        MySampleApplication application = (MySampleApplication) Robolectric.application;
        InjectMockModule module = new InjectMockModule();
        InjectMockAnnotations.initInjectMocks(this.getClass(), module, this);

        RoboGuice.setBaseApplicationInjector(application, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(application),
                module);

        RoboGuice.getInjector(application).injectMembers(this);
    }

    @Test
    public void fieldAnnotatedWith_InjectMock_shouldBeSetToMocksOfTheCorrectType() throws Exception {
        injectMocks();
        StarterActivity myActivity = new StarterActivity();

        myActivity.onCreate(null);

        verify(mockCurrentTime).currentTimeMillis();

        assertThat(myActivity.getCurrentTime()).isEqualTo(mockCurrentTime);
    }
}
