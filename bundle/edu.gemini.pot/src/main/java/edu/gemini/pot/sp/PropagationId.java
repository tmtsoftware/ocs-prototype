package edu.gemini.pot.sp;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Handback from methods that mutate state to identify the event generated by
 * the mutation.  This is useful for cases in which a client wants to react
 * differently to events caused by its own actions.
 */
public final class PropagationId {
    private static final AtomicLong ID = new AtomicLong();

    public static final PropagationId EMPTY = new PropagationId(null);

    public static PropagationId next() {
        return new PropagationId(ID.getAndIncrement());
    }

    private final Long value;

    private PropagationId(Long value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PropagationId that = (PropagationId) o;
        return value == null ? that.value == null : value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PropagationId{");
        sb.append("value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
