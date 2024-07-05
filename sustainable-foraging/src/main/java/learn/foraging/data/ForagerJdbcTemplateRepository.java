package learn.foraging.data;

import learn.foraging.models.Forager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;

public class ForagerJdbcTemplateRepository implements ForagerRepository {

    private final JdbcTemplate jdbcTemplate;

    public ForagerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Forager findById(int id) {
        return null;
    }

    @Override
    public List<Forager> findByLastName(String lastNamePrefix) {
        final String sql = """
                select forager_id, first_name, last_name, state_abbr
                from forager
                where last_name like ?;
                """;
        return jdbcTemplate.query(sql, new ForagerMapper(), lastNamePrefix + "%");
    }

    @Override
    public List<Forager> findByState(String stateAbbr) {
        final String sql = """
                select forager_id, first_name, last_name, state_abbr
                from forager
                where state_abbr = ?;
                """;

        return jdbcTemplate.query(sql, new ForagerMapper(), stateAbbr);
    }

    @Override
    public Forager addForager(Forager forager) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("forager")
                .usingGeneratedKeyColumns("forager_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("first_name", forager.getFirstName());
        args.put("last_name", forager.getLastName());
        args.put("state_abbr", forager.getState());

        int foragerId = insert.executeAndReturnKey(args).intValue();
        forager.setId(foragerId);

        return forager;
    }

    @Override
    public Forager findByKey(String firstName, String lastName, String stateAbb) {
        final String sql = """
                select
                    forager_id,
                    first_name,
                    last_name,
                    state_abbr
                from forager
                where first_name = ? and last_name = ? and state_abbr = ?;
                """;

        return jdbcTemplate.query(sql, new ForagerMapper(), firstName, lastName, stateAbb)
                .stream().findFirst().orElse(null);
    }

}
