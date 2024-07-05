package learn.foraging.data;

import learn.foraging.models.Forager;

import java.util.List;

public interface ForagerRepository {
    Forager findById(int id);

    List<Forager> findByLastName(String lastNamePrefix);

    List<Forager> findByState(String stateAbbr);

    Forager addForager(Forager forager);

    Forager findByKey(String firstName, String lastName, String stateAbb);
}
