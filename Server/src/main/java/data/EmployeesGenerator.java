package data;
import com.github.javafaker.Faker;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class EmployeesGenerator {
    public static void main(String[] args) {
        Faker faker = new Faker(new Locale("en"));
        int numRecords = 500;
        String csvFilePath = "D:/employees.csv";
       // DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        GenderPrediction genderPrediction = new GenderPrediction();
        LocalDate minBirthdate = LocalDate.of(1958, 1, 1);
        LocalDate maxBirthdate = LocalDate.of(2004, 12, 31);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long minDay = minBirthdate.toEpochDay();
        long maxDay = maxBirthdate.toEpochDay();
        try {
            FileWriter writer = new FileWriter(csvFilePath);
            writer.append("id,first_name,last_name,birthdate,gender,crew_id,created_at,updated_at\n");
            for (int i = 1; i <= numRecords; i++) {
                String id = Integer.toString(i);
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
               // long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
              //  LocalDate birth = LocalDate.ofEpochDay(randomDay);
                Date birth = faker.date().birthday(18,65);
                String birthdate = dateFormat.format(birth);
                String gender = genderPrediction.predict(firstName);
                int crewIdNumber = getRandomNumberWithProbabilities(1, 4, new double[]{0.15, 0.2, 0.2, 0.45});
                String crewId = Integer.toString(crewIdNumber);
                String createdAt =faker.date().past(365, TimeUnit.DAYS).toInstant().toString() ;
                String updatedAt = faker.date().past(30, TimeUnit.DAYS).toInstant().toString();

                writer.append(id)
                        .append(",")
                        .append(firstName)
                        .append(",")
                        .append(lastName)
                        .append(",")
                        .append(birthdate)
                        .append(",")
                        .append(gender)
                        .append(",")
                        .append(crewId)
                        .append(",")
                        .append(createdAt)
                        .append(",")
                        .append(updatedAt)
                        .append("\n");
            }
            writer.flush();
            writer.close();
            System.out.println("Employee data generated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomNumberWithProbabilities(int min, int max, double[] probabilities) {
        double randomValue = Math.random();
        double cumulativeProbability = 0.0;
        for (int i = min; i <= max; i++) {
            cumulativeProbability += probabilities[i - min];
            if (randomValue < cumulativeProbability) {
                return i;
            }
        }
        return max;
    }

}
