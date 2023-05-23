package example.model.entity;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class Person {
    private String name;

    private Date birthDate;


}
