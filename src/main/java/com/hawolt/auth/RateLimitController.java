package com.hawolt.auth;

import com.hawolt.logger.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created: 20/05/2022 12:08
 * Author: Twitter @hawolt
 **/

public class RateLimitController {
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final List<Runnable> RUNNABLE_LIST = new LinkedList<>();
    private static final Object LOCK = new Object();

    static {
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            synchronized (LOCK) {
                if (RUNNABLE_LIST.isEmpty()) return;
                Runnable runnable = RUNNABLE_LIST.remove(0);
                try {
                    runnable.run();
                } catch (Exception e) {
                    Logger.error(e);
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public static void add(Runnable runnable) {
        synchronized (LOCK) {
            RUNNABLE_LIST.add(runnable);
        }
    }

    public static void kill() {
        SCHEDULED_EXECUTOR_SERVICE.shutdown();
    }
}
