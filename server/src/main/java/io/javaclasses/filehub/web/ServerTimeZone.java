package io.javaclasses.filehub.web;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.time.Clock;
import java.time.ZoneId;

/**
 * Provide specific time zone for FileHub application.
 */
@Immutable
public final class ServerTimeZone {
    /**
     * Returns specific time zone.
     *
     * @return time zone.
     */
    public static Clock get() {
        return Clock.system(ZoneId.of("Europe/London"));
    }


}
