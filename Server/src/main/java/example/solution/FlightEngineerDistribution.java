package example.solution;

import example.model.entity.Employee;
import example.model.entity.Flight;
import example.repository.EmployeeRepository;
import example.repository.FlightRepository;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;


@Component
public class FlightEngineerDistribution {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private FlightRepository flightRepository;

    private int[][] availabilityMatrix;

    List<Employee> flightEngineers;
    List<Flight> flights;

    public void initial() {
        flightEngineers = employeeRepository.findEmployeesByCrewName("Flight Engineer");
        flights = flightRepository.findAll();
        availabilityMatrix = new int[flights.size()][flightEngineers.size()];
        for (int i = 0; i < availabilityMatrix.length; i++) {
            for (int j = 0; j < availabilityMatrix[0].length; j++) {
                availabilityMatrix[i][j] = 1;
            }
        }
        main(null);
    }

    public void main(String[] args) {
        Model model = new Model("Flight Engineer Assignment");
        int numEngineers = flightEngineers.size();
        int numFlights = flights.size();
        IntVar[][] assignments = model.intVarMatrix(numFlights, numEngineers, 0, 1);
        for (int i = 0; i < numFlights; i++) {
            for (int j = 0; j < numEngineers; j++) {
                model.ifThen(model.arithm(assignments[i][j], "=", 1), model.arithm(model.intVar(availabilityMatrix[i][j]), "=", 1));
            }
            model.sum(assignments[i], "=", 1).post();
        }

        Solver solver = model.getSolver();

        if (solver.solve()) {
            for (int i = 0; i < numFlights; i++) {
                System.out.println("Flight " + (i + 1) + ":");
                int ok = 0;
                for (int j = 0; j < numEngineers; j++) {
                    if (assignments[i][j].getValue() == 1 && availabilityMatrix[i][j] == 1) {
                        ok = 1;
                        System.out.println("  - Engineer assigned: " + flightEngineers.get(j).getFirstName() + " " + flightEngineers.get(j).getLastName());
                        updateMatrix(i, j);

                    }
                }
                if (ok == 0) {
                    System.out.println("  - No engineer assigned");
                    System.out.println("No solution found.");
                }

            }
        } else {
            System.out.println("No solution found.");
        }
    }


    public void updateMatrix(int flightIndex, int engineerIndex) {
        Flight flight = flights.get(flightIndex);
        DayOfWeek day = flight.getDepartureDay();
        LocalTime departureTime = flight.getDepartureTime();
        LocalTime arrivalTime = flight.getArrivalTime();
        for (int i = 0; i < availabilityMatrix.length; i++) {
            Flight flightVer = flights.get(i);
            if (i == flightIndex) {

                continue;
            }
            if (flightVer.getDepartureDay() == day && ((flightVer.getDepartureTime().isBefore(arrivalTime) && flightVer.getDepartureTime().isAfter(departureTime)) || (flightVer.getArrivalTime().isBefore(arrivalTime) && flightVer.getArrivalTime().isAfter(departureTime)) || (flightVer.getDepartureTime().isBefore(departureTime) && flightVer.getArrivalTime().isAfter(arrivalTime)))) {
                availabilityMatrix[i][engineerIndex] = 0;
            }
        }

    }

    public void setEmployeeRepository(EmployeeRepository employeeRepositoryMock) {
        this.employeeRepository = employeeRepositoryMock;
    }

    public void setFlightRepository(FlightRepository flightRepositoryMock) {
        this.flightRepository = flightRepositoryMock;
    }
}
