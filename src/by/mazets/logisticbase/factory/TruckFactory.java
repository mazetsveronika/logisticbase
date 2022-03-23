package by.mazets.logisticbase.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.mazets.logisticbase.entity.TypeGoods;
import by.mazets.logisticbase.entity.Truck;

public class TruckFactory {
    public static Logger logger = LogManager.getLogger();
    private static TruckFactory truckFactory = new TruckFactory();

    private TruckFactory() {
    }

    public static TruckFactory getTruckFactory() {
        return truckFactory;
    }

    public Truck createTruck(String numberTruckData, String typeData, String arrivalData) {
        int numberTruck = Integer.valueOf(numberTruckData);
        TypeGoods type = TypeGoods.valueOf(typeData.toUpperCase());
        int arrival = Integer.valueOf(arrivalData);
        Truck truck = new Truck(numberTruck, type, arrival);
        logger.info("Truck number " + numberTruck + " was created");
        return truck;
    }
}