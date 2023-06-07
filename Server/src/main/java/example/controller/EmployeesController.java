package example.controller;


import example.mapper.EmployeeMapper;
import example.model.dto.EmployeeDto;
import example.model.entity.Employee;
import example.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Tag(name = "Employee", description = "The Employee API")
public class EmployeesController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees")
    public String showEmployeesPage(HttpSession session) {
        if (session.getAttribute("authenticated") != null && (boolean) session.getAttribute("authenticated")) {

            return "employees";
        } else {
            return "redirect:/login";
        }

    }

    @PostMapping("/employees")
    @ResponseBody
    @Operation(summary = "Get all employees")
    public List<EmployeeDto> allEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/pilots")
    @Operation(summary = "Get all pilots")
    public String showPilotsPage(HttpSession session) {
        if (session.getAttribute("authenticated") != null && (boolean) session.getAttribute("authenticated")) {
            return "pilots";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/employees/pilots")
    @ResponseBody
    @Operation(summary = "Get all pilots")
    public List<EmployeeDto> getPilots() {
        return employeeService.findEmployeesByCrewName("Pilot");
    }

    @GetMapping("/employees/flight_attendants")
    @Operation(summary = "Get all flight attendants")
    public String showFlightAttendantsPage(HttpSession session) {
        if (session.getAttribute("authenticated") != null && (boolean) session.getAttribute("authenticated")) {
            return "flight_attendants";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/employees/flight_attendants")
    @ResponseBody
    @Operation(summary = "Get all flight attendants")
    public List<EmployeeDto> getFlightAttendants() {
        return employeeService.findEmployeesByCrewName("Flight Attendant");
    }

    @GetMapping("/employees/flight_engineers")
    @Operation(summary = "Get all flight engineers")
    public String showFlightEngineersPage(HttpSession session) {
        if (session.getAttribute("authenticated") != null && (boolean) session.getAttribute("authenticated")) {
            return "flight_engineers";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/employees/flight_engineers")
    @ResponseBody
    @Operation(summary = "Get all flight engineers")
    public List<EmployeeDto> getFlightEngineers() {
        return employeeService.findEmployeesByCrewName("Flight Engineer");
    }

    @GetMapping("/employees/copilots")
    @Operation(summary = "Get all copilots")
    public String showCopilotsPage(HttpSession session) {
        if (session.getAttribute("authenticated") != null && (boolean) session.getAttribute("authenticated")) {
            return "copilots";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/employees/copilots")
    @ResponseBody
    @Operation(summary = "Get all copilots")
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
