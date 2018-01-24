package com.rodvar.kotlinbase.base.business;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.Relay;

import com.rodvar.kotlinbase.base.presentation.AppModel;
import com.rodvar.kotlinbase.base.utils.android.Logger;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by rodvar on 31/3/17.
 * A use case it's basically an executable business case to which observers can subscribe and get
 * updates in the form of Output post execution related to a certain Input
 * Output and Input could be the same type.
 */
public abstract class SubscribableUseCase<Input, Output extends AppModel> {

    private final Relay<Output> relay = this.constructRelay();

    /**
     * @param subscriber
     * @return
     */
    public final Disposable register(Consumer<Output> subscriber) {
        Logger.debug(getClass().getSimpleName(), String.format("RxRelay registration: %s to %s",
                subscriber.getClass().getSimpleName(), this.getClass().getSimpleName()));
        return this.relay.subscribe(subscriber);
    }

    /**
     * @param subscription
     */
    public final void unregister(Disposable subscription) {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
            Logger.debug(getClass().getSimpleName(), String.format("RxRelay subscription disposed"));
        }
    }

    /**
     * Subscribes
     *
     * @param event input event
     */
    public final void execute(Input event) throws CannotExecuteUseCaseException {
        try {
            Logger.debug(getClass().getSimpleName(), String.format("RxRelay executing: %s with event %s",
                    this.getClass().getSimpleName(), event));
            this.buildObservable(event).subscribe(relay);
        } catch (Exception e) {
            Logger.error(getClass().getSimpleName(), "Failed to execute Use Case", e);
            throw new CannotExecuteUseCaseException(e);
        }
    }

    /**
     * @param event the input event. May be used or not at all in the construction of the observable
     * @return an buildObservable responsible of handling its own errors.
     */
    protected abstract Observable<Output> buildObservable(Input event);

    /**
     * @return Relay implementation to use. Allow use cases to change if needed
     */
    protected Relay<Output> constructRelay() {
        return this.defaultValue() == null ? BehaviorRelay.<Output>create() :
                BehaviorRelay.createDefault(defaultValue());
    }

    /**
     * Override in your concrete use case
     *
     * @return default value for the relay
     */
    protected Output defaultValue() {
        return null;
    }

    /**
     *
     */
    public static class CannotExecuteUseCaseException extends Exception {
        public CannotExecuteUseCaseException(Exception e) {
            super("Failed to execute request. Please try again.", e);
        }

    }
}