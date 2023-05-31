package example.controller;

import example.solution.PlaneAllocator;
import example.solution.PlaneAllocatorGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LALALA {

    @Autowired
    private PlaneAllocator planeAllocator;

    @Autowired
    private PlaneAllocatorGraph planeAllocator2;
    @PostMapping("/lalala")
    public String lalala() {
        System.out.println("lalala");
        System.out.println("alocare avioane");
        System.out.println(planeAllocator.allocatePlanes());
        return "lalala";
    }

    @PostMapping("/lilili")
    public String lilili() {
        System.out.println("lilili");
        System.out.println("alocare avioane");
        planeAllocator2.assignments();
        return "lilili";
    }


}
