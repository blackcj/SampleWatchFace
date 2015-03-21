package com.blackcj.drawinglibrary;

/**
 * Created by chris.black on 12/12/14.
 */
public final class Constants {
    public static final String KEY_PACKAGE_NAME = "com.blackcj.wb";
    public static final String PATH_WITH_FEATURE = "/watch_face_config/wbAnalog";

    public static final String KEY_BACKGROUND_COLOR = "wb_BACKGROUND_COLOR";
    public static final String KEY_RADIAL_COLOR = "wb_RADIAL_COLOR";
    public static final String KEY_SHOW_MINUTE_TICKS = "wb_SHOW_MINUTE_TICKS";
    public static final String KEY_SHOW_SECOND_HAND = "wb_SHOW_SECOND_HAND";
    public static final String KEY_SHOW_RADIAL_GRADIENT = "wb_SHOW_RADIAL_GRADIENT";


    /**
     * Suppress default constructor for non-instantiability
     */
    private Constants() {
        throw new AssertionError();
    }
}
