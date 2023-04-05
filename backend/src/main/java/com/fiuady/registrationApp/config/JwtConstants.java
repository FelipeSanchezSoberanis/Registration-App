package com.fiuady.registrationApp.config;

import java.util.concurrent.TimeUnit;

public class JwtConstants {
    public static final String SECRET = "tDh2HrxzBIUvm9THEbry";
    public static final long ACCESS_TOKEN_LIFETIME = TimeUnit.MINUTES.toMillis(15);
    public static final long REFRESH_TOKEN_LIFETIME = TimeUnit.DAYS.toMillis(15);
    public static final String TOKEN_PREFIX = "Bearer ";
}
