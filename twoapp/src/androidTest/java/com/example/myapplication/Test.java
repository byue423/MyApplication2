package com.example.myapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Administrator on 2017/5/24.
 */

public class Test{

    @org.junit.Test
    public void testContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.myapplication", appContext.getPackageName());
    }
}
