package engine.framework.domain.model;

/**
 * @Description:
 * @Author: Noah Zhou
 */
public abstract class Entity<ID> {
    public abstract ID getId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> that = (Entity<?>) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
    }
}
