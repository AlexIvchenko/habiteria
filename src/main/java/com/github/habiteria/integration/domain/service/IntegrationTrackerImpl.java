package com.github.habiteria.integration.domain.service;

import com.github.habiteria.core.domain.habit.ScheduledHabit;
import com.github.habiteria.core.domain.habit.Tracker;
import com.github.habiteria.integration.domain.assembler.ScheduledHabitResourceAssembler;
import com.github.habiteria.integration.domain.resources.ScheduledHabitResource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Alex Ivchenko
 */
@Service
public class IntegrationTrackerImpl implements IntegrationTracker {
    private final Tracker service;
    private final ScheduledHabitResourceAssembler habitAsm;

    public IntegrationTrackerImpl(Tracker service,
                                  ScheduledHabitResourceAssembler habitAsm) {
        this.service = service;
        this.habitAsm = habitAsm;
    }

    @Override
    public Resources<ScheduledHabitResource> getCurrentHabitList(UUID userId) {
        Set<ScheduledHabitResource> habits = service.getCurrentHabitList(userId)
                .stream()
                .map(habitAsm::toResource)
                .collect(Collectors.toSet());
        Resources<ScheduledHabitResource> resource = new Resources<>(habits);
        return resource;
    }

    @Override
    public ScheduledHabitResource perform(UUID habitId, int repeats) {
        ScheduledHabit habit = service.perform(habitId, repeats);
        return habitAsm.toResource(habit);
    }

    @Override
    public ScheduledHabitResource fail(UUID habitId, int repeats) {
        ScheduledHabit habit = service.fail(habitId, repeats);
        return habitAsm.toResource(habit);
    }

    @Override
    public ScheduledHabitResource undo(UUID habitId, int repeats) {
        return habitAsm.toResource(service.undo(habitId, repeats));
    }
}