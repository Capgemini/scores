package com.capgemini.scores.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.capgemini.scores.message.Command;
import com.capgemini.scores.message.Event;

public class ReflectiveAggregate implements Aggregate {
    
    public List<Event> process(Command command) {

        final Method commandHandlerMethod = getMethodWithCommandTypeAsArgument(command);

        try {
            commandHandlerMethod.setAccessible(true);
            return (List<Event>) commandHandlerMethod.invoke(this, command);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
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
