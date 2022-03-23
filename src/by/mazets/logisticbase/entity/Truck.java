package by.mazets.logisticbase.entity;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.mazets.logisticbase.exception.LogisticBaseException;

public class Truck extends Thread {
    public static Logger logger = LogManager.getLogger();
    private static final int UNLOAD_TIME = 200;
    private int numberTruck;
    private TypeGoods type;
    private int arrival;

    public Truck(int numberTruck, TypeGoods type, int arrival) {
        this.numberTruck = numberTruck;
        this.type = type;
        this.arrival = arrival;
    }

    public int getNumberTruck() {
        return numberTruck;
    }

    public void setNumberTruck(int numberTruck) {
        this.numberTruck = numberTruck;
    }

    public TypeGoods getType() {
        return type;
    }

    public void setType(TypeGoods type) {
        this.type = type;
    }

    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    @Override
    public void run() {
        LogisticBase logisticsBase = LogisticBase.getLogisticBase();
        try {
            TimeUnit.MILLISECONDS.sleep(arrival);
            if (type == TypeGoods.USUAL) {
                Thread.yield();
            }
            Ramp ramp = logisticsBase.getRamp(this);
            unloadGoods();
            logisticsBase.releaseRamp(this, ramp);
        } catch (LogisticBaseException e) {
            logger.error("Current thread " + Thread.currentThread().getName() + " was interrupted");
        } catch (InterruptedException e) {
            this.interrupt();
            logger.error("Current thread " + Thread.currentThread().getName() + " was interrupted");
        }
    }

    private void unloadGoods() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(UNLOAD_TIME);
        logger.info("Unloaded goods: " + this.toString());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + arrival;
        result = prime * result + numberTruck;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Truck other = (Truck) obj;
        if (arrival != other.arrival)
            return false;
        if (numberTruck != other.numberTruck)
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("truck number ").append(numberTruck);
        sb.append(" arrived at ").append(arrival);
        sb.append(" with ").append(type.toString().toLowerCase());
        sb.append(" goods.\n");
        return sb.toString();
    }
}