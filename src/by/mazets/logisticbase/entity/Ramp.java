package by.mazets.logisticbase.entity;
import java.util.concurrent.atomic.AtomicBoolean;

public class Ramp {
    private int id;
    private AtomicBoolean busy;

    public Ramp() {
    }

    public Ramp(int id) {
        this.id = id;
        this.busy = new AtomicBoolean(false);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBusy() {
        return busy.get();
    }

    public void setBusy(boolean busy) {
        this.busy.set(busy);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((busy == null) ? 0 : busy.hashCode());
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ramp other = (Ramp) obj;
        if (busy == null) {
            if (other.busy != null)
                return false;
        } else if (!busy.equals(other.busy))
            return false;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nRamp number  ").append(id);
        return sb.toString();
    }
}