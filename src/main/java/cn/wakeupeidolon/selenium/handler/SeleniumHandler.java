package cn.wakeupeidolon.selenium.handler;

import java.util.function.Function;

/**
 * @author Wang Yu
 */
@FunctionalInterface
public interface SeleniumHandler<T, R> extends Function<T, R> {
    R apply(T t);
}
