package by.mazets.logisticbase.entity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.mazets.logisticbase.exception.LogisticBaseException;

public class LogisticBase {
    public static Logger logger = LogManager.getLogger();
    private static final int NUMBER_OF_RAMPS = 5;
    private static LogisticBase logisticBase;
    private static ReentrantLock lock = new ReentrantLock();
    private Semaphore semaphore = new Semaphore(NUMBER_OF_RAMPS);
    private List<Ramp> ramps = new ArrayList<Ramp>();

    private LogisticBase() {
        for (int i = 0; i < NUMBER_OF_RAMPS; i++) {
            Ramp ramp = new Ramp(i);
            ramps.add(ramp);
        }
    }

    public static LogisticBase getLogisticBase() {
        if (logisticBase == null) {
            try {
                lock.lock();
                if (logisticBase == null) {
                    logisticBase = new LogisticBase();
                    logger.info("Logistic Base was created");
                }
            } finally {
                lock.unlock();
            }
        }
        return logisticBase;
    }

    public Ramp getRamp(Truck truck) throws LogisticBaseException {
        try {
            logger.info("Arrived at the logistics base " + truck.toString());
            semaphore.acquire();
            lock.lock();
            for (Ramp ramp : ramps) {
                if (!ramp.isBusy()) {
                    ramp.setBusy(true);
                    logger.info("There is a " + truck.toString() + " on the ramp number " + ramp.getId());
                    return ramp;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LogisticBaseException("Current thread " + Thread.currentThread().getName() + " was interrupted");
        } finally {
            lock.unlock();
        }
        throw new LogisticBaseException("the number of ramps does not match the number of semaphore permits");
    }

    public void releaseRamp(Truck truck, Ramp ramp) {
        ramp.setBusy(false);
        semaphore.release();
        logger.info("Truck " + truck.getNumberTruck() + " left the ramp number " + ramp.getId());
    }
}
