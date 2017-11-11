package com.github.habiteria.core.repository;

import com.github.habiteria.core.model.Habit;
import com.github.habiteria.core.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * @author Alex Ivchenko
 */
public interface HabitRepository extends CrudRepository<Habit, String> {
    Set<Habit> findByOwner(User owner);
}