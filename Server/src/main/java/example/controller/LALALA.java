package example.controller;

import example.data.AirplaneGeneratorLargeInstance;
import example.data.FlightGeneratorLargeInstance;
import example.solution.FlightEngineerDistribution;
import example.solution.PlaneAllocator;
import example.solution.PlaneAllocatorGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LALALA {

    @Autowired
    private PlaneAllocator planeAllocator;

    @Autowired
    private FlightGeneratorLargeInstance flightGeneratorLargeInstance;
    @Autowired
    private PlaneAllocatorGraph planeAllocator2;

    @Autowired
    private AirplaneGeneratorLargeInstance airplaneGeneratorLargeInstance;

    @Autowired
    private FlightEngineerDistribution flightEngineerDistribution;

    @PostMapping("/lalala")
    public String lalala() {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore =
                runtime.totalMemory() - runtime.freeMemory();
        long initialTime = System.currentTimeMillis();

        System.out.println("lalala");
        planeAllocator.allocatePlanes();
        long runningTime = System.currentTimeMillis() - initialTime;
        long usedMemoryAfter =
                runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = usedMemoryAfter - usedMemoryBefore;
        System.out.println("Running time: " + runningTime + " ms");
        System.out.println("Memory increase: " + memoryIncrease + " bytes");
        return "lalala";
    }

    @PostMapping("/lilili")
    public String lilili() {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore =
                runtime.totalMemory() - runtime.freeMemory();
        long initialTime = System.currentTimeMillis();

        System.out.println("lilili");
        planeAllocator2.assignments();
        long runningTime = System.currentTimeMillis() - initialTime;
        long usedMemoryAfter =
                runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = usedMemoryAfter - usedMemoryBefore;
        System.out.println("Running time: " + runningTime + " ms");
        System.out.println("Memory increase: " + memoryIncrease + " bytes");
        return "lilili";
    }

    @PostMapping("/lululu")
    public String lululu() {
        System.out.println("lululu");
        System.out.println("alocare avioane");
        flightGeneratorLargeInstance.main(null);
        return "lululu";
    }

    @PostMapping("/lelele")
    public String lelele() {
        System.out.println("lelele");
        System.out.println("alocare avioane");
        airplaneGeneratorLargeInstance.main();
        return "lelele";
    }

    @PostMapping("/olala")
    public String olala() {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore =
                runtime.totalMemory() - runtime.freeMemory();
        long initialTime = System.currentTimeMillis();
        System.out.println("olala");
        flightEngineerDistribution.initial();
        long runningTime = System.currentTimeMillis() - initialTime;
        long usedMemoryAfter =
                runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = usedMemoryAfter - usedMemoryBefore;
        System.out.println("Running time: " + runningTime + " ms");
        System.out.println("Memory increase: " + memoryIncrease + " bytes");
        return "olala";
    }


}
