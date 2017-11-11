package com.github.habiteria.integration.domain.service;

import com.github.habiteria.core.domain.habit.HabitService;
import com.github.habiteria.core.model.Habit;
import com.github.habiteria.integration.domain.assembler.HabitResourceAssembler;
import com.github.habiteria.integration.domain.resources.HabitResource;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Alex Ivchenko
 */
@Service
public class HabitResourceServiceImpl implements HabitResourceService {
    private final HabitService service;
    private final HabitResourceAssembler habitAsm;

    public HabitResourceServiceImpl(HabitService service, HabitResourceAssembler habitAsm) {
        this.service = service;
        this.habitAsm = habitAsm;
    }

    @Override
    public HabitResource create(UUID userId, Habit habit) {
        return habitAsm.toResource(service.create(userId, habit));
    }
}
