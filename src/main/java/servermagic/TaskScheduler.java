package servermagic;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class TaskScheduler {
    private static final List<ScheduledTask> tasks = new ArrayList<>();

    static {
        // Register tick handler once
        ServerTickEvents.END_SERVER_TICK.register(TaskScheduler::tick);
    }

    public static void scheduleTask(Runnable task, int delayTicks) {
        tasks.add(new ScheduledTask(task, delayTicks));
    }

    private static void tick(MinecraftServer server) {
        List<ScheduledTask> toRemove = new ArrayList<>();

        for (ScheduledTask task : tasks) {
            task.ticksRemaining--;
            if (task.ticksRemaining <= 0) {
                task.task.run();
                toRemove.add(task);
            }
        }

        tasks.removeAll(toRemove);
    }

    private static class ScheduledTask {
        final Runnable task;
        int ticksRemaining;

        ScheduledTask(Runnable task, int ticks) {
            this.task = task;
            this.ticksRemaining = ticks;
        }
    }
}