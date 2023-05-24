package example.repository;

import example.model.entity.Crew;
import example.model.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Repository
public class EmployeeRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private  CrewRepository crewRepository;


    public List<Employee> getPilots(int occupationId) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("findPilots");
        query.setParameter("occupationId", occupationId);
        query.registerStoredProcedureParameter("result", ResultSet.class, ParameterMode.REF_CURSOR);

        query.execute();

        ResultSet resultSet = (ResultSet) query.getOutputParameterValue("result");

        List<Employee> pilots = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Timestamp birthdate = resultSet.getTimestamp("birthdate");
                String gender = resultSet.getString("gender");
                Crew crew=crewRepository.findById(resultSet.getInt("crew_id")).get();

                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Timestamp updatedAt = resultSet.getTimestamp("updated_at");
                Employee pilot = new Employee(id, firstName, lastName,birthdate,gender,crew,createdAt,updatedAt);
                pilots.add(pilot);
            }
        } catch (SQLException e) {

        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // Handle any potential exceptions
            }
        }

        return pilots;
    }
}
