package example.controller;

import example.data.AirplaneGeneratorLargeInstance;
import example.data.FlightGeneratorLargeInstance;
import example.model.entity.Flight;
import example.repository.EmployeeRepository;
import example.repository.FlightRepository;
import example.repository.PilotRepository;
import example.solution.*;
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

    @Autowired
    private PilotsDistribution pilotsDistribution;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private PilotRepository pilotRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CopilotsDistribution copilotsDistribution;

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

    @PostMapping("/ululu")
    public String ululu() {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore =
                runtime.totalMemory() - runtime.freeMemory();
        long initialTime = System.currentTimeMillis();
        System.out.println("ululu");
        pilotsDistribution.initialize();
        System.out.println(employeeRepository.findEmployeesByCrewName("Pilot").size());
        pilotsDistribution.distributePilots(employeeRepository.findEmployeesByCrewName("Pilot"));
        long runningTime = System.currentTimeMillis() - initialTime;
        long usedMemoryAfter =
                runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = usedMemoryAfter - usedMemoryBefore;
        System.out.println("Running time: " + runningTime + " ms");
        System.out.println("Memory increase: " + memoryIncrease + " bytes");
        return "ululu";
    }

    @PostMapping("/alala")
    private String alala() {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore =
                runtime.totalMemory() - runtime.freeMemory();
        long initialTime = System.currentTimeMillis();
        System.out.println("alala");
       copilotsDistribution.initialize();
         copilotsDistribution.distributeCopilots(employeeRepository.findEmployeesByCrewName("Copilot"));
        long runningTime = System.currentTimeMillis() - initialTime;
        long usedMemoryAfter =
                runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = usedMemoryAfter - usedMemoryBefore;
        System.out.println("Running time: " + runningTime + " ms");
        System.out.println("Memory increase: " + memoryIncrease + " bytes");
        return "alala";
    }





}
