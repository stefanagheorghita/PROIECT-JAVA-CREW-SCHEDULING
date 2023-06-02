package example.data;

import example.model.entity.Airplane;
import example.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class AirplaneGeneratorLargeInstance {

    @Autowired
    AirplaneRepository airplaneRepository;

    public void main(){
        Random r = new Random();
        for(int i=0;i<500;i++)
        {
            Airplane airplane = new Airplane();
            airplane.setCapacity(r.nextInt(20,600));
            airplaneRepository.save(airplane);
        }
    }
}
