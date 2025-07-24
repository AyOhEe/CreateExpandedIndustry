package io.github.ayohee.expandedindustry.util;

import java.util.function.Supplier;

public class ConstSupplier<T> implements Supplier<T> {
    protected T val = null;
    protected Supplier<T> eval;

    public ConstSupplier(Supplier<T> eval) {
        this.eval = eval;
    }

    @Override
    public T get() {
        if (val != null) {
            return val;
        }

        val = eval.get();
        return val;
    }
}
