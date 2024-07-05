package learn.foraging.domain;

import learn.foraging.data.ForagerRepositoryDouble;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForagerServiceTest {

    ForagerService service = new ForagerService(
            new ForagerRepositoryDouble()
    );


    @Test
    void findByState() {
    }

    @Test
    void findByLastName() {
    }
}