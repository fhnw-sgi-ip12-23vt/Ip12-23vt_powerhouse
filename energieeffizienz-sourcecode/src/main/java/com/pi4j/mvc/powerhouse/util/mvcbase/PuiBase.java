package com.pi4j.mvc.powerhouse.util.mvcbase;

import com.pi4j.context.Context;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Base class for all PUIs.
 * <p>
 * In our scenario we also have a GUI.
 * <p>
 * We have to avoid that one of the UIs is blocked because the other UI has to perform a long-running task.
 * <p>
 * Therefore, we need an additional "worker-thread" in both UIs.
 * <p>
 * For JavaFX-based GUIs that's already available (the JavaFX Application Thread).
 * <p>
 * For PUIs we need to do that ourselves. It's implemented as a provider/consumer-pattern (see {@link ConcurrentTaskQueue}).
 */
public abstract class PuiBase<M, C extends ControllerBase<M>> implements Projector<M, C>{

    // all PUI actions should be done asynchronously (to avoid UI freezing)
    private final ConcurrentTaskQueue<Void> queue = new ConcurrentTaskQueue<>();

    protected final Context pi4J;

    public PuiBase(C controller, Context pi4J) {
        Objects.requireNonNull(pi4J);

        this.pi4J = pi4J;

        init(controller);
    }

    public void shutdown(){
        pi4J.shutdown();
        queue.shutdown();
    }

    protected void async(Supplier<Void> todo, Consumer<Void> onDone) {
        queue.submit(todo, onDone);
    }

    public void runLater(Consumer<Void> todo) {
        async(() -> null, todo);
    }

    /**
     * Intermediate solution for TestCase support.
     * <p>
     * Best solution would be that 'action' of 'runLater' is executed on calling thread.
     * <p>
     * Waits until all current actions in actionQueue are completed.
     *
     */
    public void awaitCompletion(){
        final ExecutorService waitForFinishedService = Executors.newFixedThreadPool(1);
        // would be nice if this could just be a method reference
        async(() -> {
                  waitForFinishedService.shutdown();
                  return null;
              },
              unused -> {
              });
        try {
            //noinspection ResultOfMethodCallIgnored
            waitForFinishedService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IllegalThreadStateException(); // very unlikely to happen
        }
    }

    /**
     * First step to register an observer.
     *
     * @param observableValue the value that should trigger some PUI-updates
     * @return an Updater to specify what needs to be done whenever observableValue changes
     */
    protected <V> Updater<V> onChangeOf(ObservableValue<V> observableValue) {
        return new Updater<>(observableValue);
    }

    /**
     * First step to register an observer.
     *
     * @param observableArray the value that should trigger some PUI-updates
     * @return an Updater to specify what needs to be done whenever observableValue changes
     */
    protected <V> ArrayUpdater<V> onChangeOf(ObservableArray<V> observableArray) {
        return new ArrayUpdater<>(observableArray);
    }

    /**
     * Second step to specify an observer.
     * <p>
     * Use 'triggerPUIAction' to specify what needs to be done whenever the observed value changes
     */
    public class Updater<V> {
        private final ObservableValue<V> observableValue;

        Updater(ObservableValue<V> observableValue) {
            this.observableValue = observableValue;
        }

        public void execute(ObservableValue.ValueChangeListener<V> action) {
            observableValue.onChange((oldValue, newValue) -> queue.submit(() -> {
                action.update(oldValue, newValue);
                return null;
            }));
        }

        public void execute(Consumer<V> action){
            observableValue.onChange((oldValue, newValue) ->
                    queue.submit(() -> {
                       action.accept(newValue);
                       return null;
                    }));
        }
    }

    /**
     * Second step to specify an observer.
     * <p>
     * Use 'triggerPUIAction' to specify what needs to be done whenever the observed array changes
     */
    public class ArrayUpdater<V> {
        private final ObservableArray<V> observableArray;

        ArrayUpdater(ObservableArray<V> observableArray) { this.observableArray = observableArray; }

        public void execute(ObservableArray.ValueChangeListener<V> action) {
            observableArray.onChange((oldValue, newValue) -> queue.submit(() -> {
                action.update(oldValue, newValue);
                return null;
            }));
        }
    }
}


