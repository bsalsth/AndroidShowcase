package com.softlica.bishal.triplescale.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by bishal on 7/10/2017.
 */

public class CustomScope {
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AppModScope {
    }

    @Documented
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface activityScope {
    }

    @Documented
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface custom {
    }
}
