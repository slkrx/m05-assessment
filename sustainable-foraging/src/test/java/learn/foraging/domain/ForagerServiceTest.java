package learn.foraging.domain;

import learn.foraging.data.ForagerRepositoryDouble;
import learn.foraging.models.Forager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForagerServiceTest {

    ForagerService service = new ForagerService(
            new ForagerRepositoryDouble()
    );

    @Test
    void shouldFindByState() {
        List<Forager> expected = List.of(ForagerRepositoryDouble.FORAGER);
        List<Forager> actual = service.findByState("GA");
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByMissingState() {
        List<Forager> actual = service.findByState("TX");
        assertEquals(0, actual.size());
    }

    @Test
    void shouldAddForager() {
        Forager toAdd = new Forager();
        toAdd.setFirstName("Dan");
        toAdd.setLastName("McMullen");
        toAdd.setState("FL");

        Result<Forager> result = service.addForager(toAdd);
        assertEquals(result.getErrorMessages().size(), 0);

    }

    @Test
    void shouldNotAddForager() {
        Forager toAdd = new Forager();
        toAdd.setFirstName("dan");
        toAdd.setLastName("McMullen");
        toAdd.setState("FL123");

        Result<Forager> result = service.addForager(toAdd);
        assertNotEquals(result.getErrorMessages().size(), 0);

    }

    @Test
    void shouldNotAddDuplicateForager() {
        Forager forager = new Forager();
        forager.setFirstName("Jilly");
        forager.setLastName("Sisse");
        forager.setState("GA");

        Result<Forager> result = service.addForager(forager);
        assertNotEquals(result.getErrorMessages().size(), 0);

    }


}