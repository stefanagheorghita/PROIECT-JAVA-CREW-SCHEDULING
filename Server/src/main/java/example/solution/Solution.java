//package example.solution;
//
//import example.model.entity.Flight;
//import example.repository.FlightRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.List;
//public class Solution{
//    @Autowired
//    FlightRepository flightRepository;
//
//
//    private Schedule generateSchedule(){
//        List<Flight> flights = new ArrayList<>();
//        flights.addAll(flightRepository.findAll());
//        Schedule schedule = new Schedule(flights);
//        Boolean masterAllocation = true;
//        Boolean match = true;
//        List<Flight> unallocatedFlights = new ArrayList<>();
//        while(masterAllocation){
//            match = false;
//            unallocatedFlights=schedule.getUnallocatedFlights();
//
//        }
//
//    }
//}
