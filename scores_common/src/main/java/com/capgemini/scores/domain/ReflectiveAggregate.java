package com.capgemini.scores.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.capgemini.scores.message.Command;
import com.capgemini.scores.message.Event;

public class ReflectiveAggregate implements Aggregate {

    private AtomicLong version = new AtomicLong(0l);
    
    public List<Event> process(Command command) {

        final Method commandHandlerMethod = getMethodWithCommandTypeAsArgument(command);

        try {
            commandHandlerMethod.setAccessible(true);
            final List<Event> resultantEvents = (List<Event>) commandHandlerMethod.invoke(this, command);

            version.incrementAndGet();

            return resultantEvents;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Long getVersion() {
        return version.get();
    }

    private Method getMethodWithCommandTypeAsArgument(Command command) {
        for (Method method : this.getClass().getDeclaredMethods()) {
            for (Class<?> parameterType : method.getParameterTypes()) {
                if (parameterType.equals(command.getClass())) {
                    return method;
                }
            }
        }

        return null;
    }
}
