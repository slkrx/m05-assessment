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

}