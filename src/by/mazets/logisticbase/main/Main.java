package by.mazets.logisticbase.main;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.mazets.logisticbase.entity.Truck;
import  by.mazets.logisticbase.exception.LogisticBaseException;
import  by.mazets.logisticbase.factory.TruckFactory;
import by.mazets.logisticbase.parser.TruckParser;
import by.mazets.logisticbase.reader.TruckFileReader;

public class Main {
    public static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String filePath = "data/trucks.txt";
        TruckFileReader reader = new TruckFileReader();
        TruckParser parser = new TruckParser();
        TruckFactory factory = TruckFactory.getTruckFactory();
        List<String> trucksData;
        List<String[]> trucksSpecification;
        try {
            trucksData = reader.readFromFile(filePath);
            trucksSpecification = parser.parseTruck(trucksData);
            for (String[] truckData : trucksSpecification) {
                Truck truck = factory.createTruck(truckData[0], truckData[1], truckData[2]);
                truck.start();
            }
        } catch (LogisticBaseException e) {
            logger.error("Exception in Main " + e.getMessage());
        }
    }
}