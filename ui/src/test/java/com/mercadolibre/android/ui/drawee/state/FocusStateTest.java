package com.mercadolibre.android.ui.drawee.state;

import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.mercadolibre.android.testing.AbstractRobolectricTest;
import com.mercadolibre.android.ui.R;
import com.mercadolibre.android.ui.drawee.StateDraweeView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.fail;

@SuppressWarnings("PMD.EmptyCatchBlock")
public class FocusStateTest extends AbstractRobolectricTest{

    @Before
    public void setUp() {
        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(RuntimeEnvironment.application);
        }
    }

    @Test
    public void test_IsValidKeyIsOnlyForPositiveNumbers() {
        try {
            new FocusState()
                .add(BinaryState.STATE_ON, 0)
                .add(BinaryState.STATE_OFF, 0);
        } catch (Exception e) {
            fail();
        }

        try {
            new FocusState()
                .add(-1, 0);
            fail();
        } catch (Exception e) {
            // Silent
        }
    }

    @Test
    public void test_Attach() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        FocusState state = new FocusState();
        state.add(BinaryState.STATE_OFF, R.drawable.ui_ic_clear);

        state.attach(view);

        Mockito.verify(view).setOnFocusChangeListener(Mockito.any(View.OnFocusChangeListener.class));
    }

    @Test
    public void test_Detach() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        FocusState state = new FocusState();

        state.detach(view);

        Mockito.verify(view).setOnFocusChangeListener(null);
    }

    @Test
    public void test_SetDrawable() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        FocusState state = new FocusState();
        state.add(BinaryState.STATE_OFF, R.drawable.ui_ic_clear);
        state.add(BinaryState.STATE_ON, R.drawable.ui_ic_clear);

        state.setDrawable(view);

        Mockito.verify(view).setController(Mockito.any(DraweeController.class));
    }

}