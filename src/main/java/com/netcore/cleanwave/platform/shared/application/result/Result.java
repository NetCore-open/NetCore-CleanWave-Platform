package com.netcore.cleanwave.platform.shared.application.result;

import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.function.Function;

/**
 * Generic application-layer outcome wrapper.
 *
 * @param <T> success value type
 * @param <E> failure value type
 */
@NullMarked
public sealed interface Result<T, E> permits Result.Success, Result.Failure {

    /**
     * Creates a successful result.
     *
     * @param value success value
     * @param <T> success value type
     * @param <E> failure value type
     * @return success result
     */
    static <T, E> Result<T, E> success(T value) {
        return new Success<>(value);
    }

    /**
     * Creates a failed result.
     *
     * @param error failure value
     * @param <T> success value type
     * @param <E> failure value type
     * @return failure result
     */
    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(error);
    }
    /**
     * @return {@code true} when this result is successful
     */
    default boolean isSuccess() { return this instanceof  Success<?, ?>;}
    /**
     * @return {@code true} when this result is failed
     */
    default boolean isFailure() { return this instanceof  Failure<?, ?>; };

    /**
     * @return success value when present
     */
    default Optional<T> success() {
        if (this instanceof Success<?, ?> success) {
            @SuppressWarnings("unchecked")
            T value = ((Success<T, E>) success).value();
            return Optional.of(value);
        }
        return Optional.empty();
    }

    /**
     * Returns an Optional containing the value if this is a success, otherwise an empty Optional.
     */
    default Optional<T> toOptional() {
        return switch (this) {
            case Success<T, E> s -> Optional.of(s.value);
            case Failure<T, E> f -> Optional.empty();
        };
    }

    /**
     * Extracts the value if successful, or returns the given default if failed.
     */
    default T getOrElse(T defaultValue) {
        return switch (this) {
            case Success<T, E> s -> s.value;
            case Failure<T, E> f -> defaultValue;
        };
    }

    /**
     * @return failure value when present
     */
    default Optional<E> failure() {
        if (this instanceof Failure<?, ?> failure) {
            @SuppressWarnings("unchecked")
            E error = ((Failure<T, E>) failure).error();
            return Optional.of(error);
        }
        return Optional.empty();
    }

    /**
     * Successful result.
     *
     * @param value success value
     * @param <T> success value type
     * @param <E> failure value type
     */
    record  Success<T, E>(T value) implements Result<T, E> {}

    /**
     * Failed result.
     *
     * @param error failure value
     * @param <T> success value type
     * @param <E> failure value type
     */
    record  Failure<T, E>(E error) implements Result<T, E> {}

    /**
     * Folds this result into a single value.
     *
     * @param onSuccess mapper for a successful value
     * @param onFailure mapper for a failure value
     * @param <R> folded return type
     * @return folded value
     */
    default <R> R fold(Function<? super T, ? extends R> onSuccess, Function<? super E, ? extends R> onFailure) {
     if (this instanceof  Success<?, ?> success) {
         @SuppressWarnings("unchecked")
         T value = ((Success<T, E>) success).value();
         return onSuccess.apply(value);
     }
     @SuppressWarnings("uncheked")
     E error = ((Failure<T, E>) this).error();
     return onFailure.apply(error);
    }

    /**
     * Applies a function to the error if this is a failure, otherwise returns this unchanged.
     */
    default <E2> Result<T, E2> mapError(Function<E, E2> f) {
        return switch (this) {
            case Success<T, E> s -> Result.success(s.value);
            case Failure<T, E> failure -> Result.failure(f.apply(failure.error));
        };
    }

    /**
     * Applies a function to the value if this is a success, producing a new Result.
     */
    default <T2> Result<T2, E> flatMap(Function<T, Result<T2, E>> f) {
        return switch (this) {
            case Success<T, E> s -> f.apply(s.value);
            case Failure<T, E> failure -> Result.failure(failure.error);
        };
    }

    /**
     * Applies a function to the value if this is a success.
     */
    default <T2> Result<T2, E> map(Function<T, T2> f) {
        return switch (this) {
            case Success<T, E> s -> Result.success(f.apply(s.value));
            case Failure<T, E> failure -> Result.failure(failure.error);
        };
    }

    /**
     * Applies a function to the error if this is a failure.
     * Unlike mapError, this takes a Result, allowing fallback recovery.
     */
    default Result<T, E> recover(Function<E, Result<T, E>> f) {
        return switch (this) {
            case Success<T, E> s -> this;
            case Failure<T, E> failure -> f.apply(failure.error);
        };
    }
}
