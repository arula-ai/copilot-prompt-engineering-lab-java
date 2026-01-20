package com.fidelity.promptlab.models;

import java.util.Optional;
import java.util.function.Function;

/**
 * Generic result type for success/failure operations using Java 17 sealed interfaces.
 * This is a functional programming pattern similar to Either/Result in other languages.
 *
 * Usage:
 *   Result<User, ApiError> result = userService.findById(id);
 *   if (result.isSuccess()) {
 *       return ResponseEntity.ok(result.getData().get());
 *   } else {
 *       ApiError error = result.getError().get();
 *       return ResponseEntity.status(error.getCode()).body(error);
 *   }
 *
 * @param <T> The success value type
 * @param <E> The error type
 */
public sealed interface Result<T, E> permits Result.Success, Result.Failure {

    record Success<T, E>(T data) implements Result<T, E> {
        public Success {
            if (data == null) {
                throw new IllegalArgumentException("Success data cannot be null");
            }
        }
    }

    record Failure<T, E>(E error) implements Result<T, E> {
        public Failure {
            if (error == null) {
                throw new IllegalArgumentException("Failure error cannot be null");
            }
        }
    }

    // Factory methods
    static <T, E> Result<T, E> success(T data) {
        return new Success<>(data);
    }

    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(error);
    }

    // Query methods
    default boolean isSuccess() {
        return this instanceof Success;
    }

    default boolean isFailure() {
        return this instanceof Failure;
    }

    @SuppressWarnings("unchecked")
    default Optional<T> getData() {
        if (this instanceof Success) {
            return Optional.of(((Success<T, E>) this).data());
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    default Optional<E> getError() {
        if (this instanceof Failure) {
            return Optional.of(((Failure<T, E>) this).error());
        }
        return Optional.empty();
    }

    // Transformation methods
    @SuppressWarnings("unchecked")
    default <U> Result<U, E> map(Function<T, U> mapper) {
        if (this instanceof Success) {
            return Result.success(mapper.apply(((Success<T, E>) this).data()));
        }
        return Result.failure(((Failure<T, E>) this).error());
    }

    @SuppressWarnings("unchecked")
    default <U> Result<U, E> flatMap(Function<T, Result<U, E>> mapper) {
        if (this instanceof Success) {
            return mapper.apply(((Success<T, E>) this).data());
        }
        return Result.failure(((Failure<T, E>) this).error());
    }

    @SuppressWarnings("unchecked")
    default T getOrElse(T defaultValue) {
        if (this instanceof Success) {
            return ((Success<T, E>) this).data();
        }
        return defaultValue;
    }

    @SuppressWarnings("unchecked")
    default T getOrThrow() {
        if (this instanceof Success) {
            return ((Success<T, E>) this).data();
        }
        throw new IllegalStateException("Result is failure: " + ((Failure<T, E>) this).error());
    }
}
