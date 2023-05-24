package example.controller;


import example.mapper.EmployeeMapper;
import example.model.dto.EmployeeDto;
import example.model.entity.Employee;
import example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
@Controller
public class EmployeesController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees")
    public String showEmployeesPage() {
        return "employees";
    }

    @PostMapping("/employees")
    @ResponseBody
    public List<EmployeeDto> allEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/pilots")
    public String showPilotsPage() {
        return "pilots";
    }

    @PostMapping("/employees/pilots")
    @ResponseBody
    public List<EmployeeDto> getPilots() {
        return employeeService.findEmployeesByCrewName("Pilot");
    }

    @GetMapping("/employees/flight_attendants")
    public String showFlightAttendantsPage() {
        return "flight_attendants";
    }

    @PostMapping("/employees/flight_attendants")
    @ResponseBody
    public List<EmployeeDto> getFlightAttendants() {
        return employeeService.findEmployeesByCrewName("Flight Attendant");
    }

    @GetMapping("/employees/flight_engineers")
    public String showFlightEngineersPage() {
        return "flight_engineers";
    }

    @PostMapping("/employees/flight_engineers")
    @ResponseBody
    public List<EmployeeDto> getFlightEngineers() {
        return employeeService.findEmployeesByCrewName("Flight Engineer");
    }

    @GetMapping("/employees/copilots")
    public String showCopilotsPage() {
        return "copilots";
    }

    @PostMapping("/employees/copilots")
    @ResponseBody
    public List<EmployeeDto> getCopilots() {
        return employeeService.findEmployeesByCrewName("Copilot");
    }


    @GetMapping("/employees/pilots/count")
    @ResponseBody
    public int getPilotsCount() {
        return employeeService.findEmployeesByCrewName("Pilot").size();
    }

    @GetMapping("/employees/flight_attendants/count")
    @ResponseBody
    public int getFlightAttendantsCount() {
        return employeeService.findEmployeesByCrewName("Flight Attendant").size();
    }

    @GetMapping("/employees/flight_engineers/count")
    @ResponseBody
    public int getFlightEngineersCount() {
        return employeeService.findEmployeesByCrewName("Flight Engineer").size();
    }

    @GetMapping("/employees/copilots/count")
    @ResponseBody
    public int getCopilotsCount() {
        return employeeService.findEmployeesByCrewName("Copilot").size();
    }
}
