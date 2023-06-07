package example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.data.AirplaneGeneratorLargeInstance;
import example.data.FlightGeneratorLargeInstance;
import example.model.entity.Airplane;
import example.model.entity.Flight;
import example.repository.EmployeeRepository;
import example.repository.FlightRepository;
import example.repository.PilotRepository;
import example.solution.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@Tag(name = "Algorithms", description = "Algorithms for solving the problem")
public class AlgorithmsController {

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

    @GetMapping("/flights")
    @Operation(summary = "Get flights with airplanes assigned")
    public String flightsGet() {
        return "flights";
    }

    @PostMapping("/flights")
    @ResponseBody
    @Operation(summary = "Get flights with airplanes assigned")
    public String flights() {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore =
                runtime.totalMemory() - runtime.freeMemory();
        long initialTime = System.currentTimeMillis();

        HashMap<Flight, Airplane> map = planeAllocator.allocatePlanes();
        long runningTime = System.currentTimeMillis() - initialTime;
        long usedMemoryAfter =
                runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = usedMemoryAfter - usedMemoryBefore;
        System.out.println("Running time: " + runningTime + " ms");
        System.out.println("Memory increase: " + memoryIncrease + " bytes");
        ObjectMapper objectMapper = new ObjectMapper();
        String flightMapJson;
        try {
            flightMapJson = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {

            flightMapJson = "{}";
        }
        return flightMapJson;
    }

    @PostMapping("/lilili")
    @Operation(summary = "Get flights with airplanes assigned graph")
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
    @Operation(summary = "flight generator")
    public String lululu() {
        System.out.println("lululu");
        flightGeneratorLargeInstance.main(null);
        return "lululu";
    }

    @PostMapping("/lelele")
    @Operation(summary = "airplane generator")
    public String lelele() {
        airplaneGeneratorLargeInstance.main();
        return "lelele";
    }

    @PostMapping("/olala")
    @Operation(summary = "flight engineer distribution")
    public String olala() {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore =
                runtime.totalMemory() - runtime.freeMemory();
        long initialTime = System.currentTimeMillis();
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
    @Operation(summary = "pilots distribution")
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
    @Operation(summary = "copilots distribution")
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
