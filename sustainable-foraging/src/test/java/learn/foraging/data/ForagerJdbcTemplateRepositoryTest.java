package learn.foraging.data;

import learn.foraging.DataHelper;
import learn.foraging.models.Forager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static learn.foraging.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ForagerJdbcTemplateRepositoryTest {
    static final int NEXT_ID = 3;

    JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();
    ForagerJdbcTemplateRepository repository = new ForagerJdbcTemplateRepository(jdbcTemplate);

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    @Test
    void shouldFindByLastNameLa() {
        List<Forager> expected = List.of(
                FORAGER_ONE,
                FORAGER_TWO
        );
        List<Forager> actual = repository.findByLastName("La");
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByLastNameX() {
        List<Forager> expected = List.of();
        List<Forager> actual = repository.findByLastName("X");
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByState() {
        List<Forager> expected = List.of(FORAGER_ONE);
        List<Forager> actual = repository.findByState("OR");
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByState() {
        List<Forager> expected = List.of();
        List<Forager> actual = repository.findByState("fjkdslf");
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddForager() {
        Forager arg = new Forager(0, "new", "new", "TX");
        Forager expected = new Forager(NEXT_ID, "new", "new", "TX");
        Forager actual = repository.addForager(arg);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindForagerByKey() {
       Forager expected = FORAGER_ONE;
       Forager actual = repository.findByKey("First 1", "Last 1", "OR");
       assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindForagerByMissingKey() {
        Forager expected = FORAGER_ONE;
        Forager actual = repository.findByKey("First 1", "Last 1", "TX");
        assertNotEquals(expected, actual);
    }
}