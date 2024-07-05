package learn.foraging.domain;

import learn.foraging.data.ForagerRepository;
import learn.foraging.models.Forage;
import learn.foraging.models.Forager;

import java.util.ArrayList;
import java.util.List;

public class ForagerService {

    private final ForagerRepository repository;

    private final String[] stateAbbreviations = { "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"  };

    public ForagerService(ForagerRepository repository) {
        this.repository = repository;
    }

    public List<Forager> findByState(String stateAbbr) {
        return repository.findByState(stateAbbr);
    }

    public List<Forager> findByLastName(String prefix) {
        return repository.findByLastName(prefix);
    }

    public Result<Forager> addForager(Forager forager) {
        Result<Forager> result = validate(forager);
        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.addForager(forager));

        return result;
    }

    public Result<Forager> validate(Forager forager) {
        Result<Forager> result = validateNulls(forager);
        if (!result.isSuccess()) {
            return result;
        }

        validateStateAbb(forager, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateNotDuplicate(forager, result);
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }

    private void validateStateAbb(Forager forager, Result<Forager> result) {
        if(!List.of(stateAbbreviations).contains(forager.getState()))
            result.addErrorMessage("State abbreviation must be a valid state.");
    }

    private void validateNotDuplicate(Forager forager, Result<Forager> result) {
       Forager foragerFound = repository.findByKey(forager.getFirstName(), forager.getLastName(), forager.getState());
       if (foragerFound != null) {
           result.addErrorMessage("Forager first name, last name, and state combination must be unique.");
       }
    }

    private Result<Forager> validateNulls(Forager forager) {
        Result<Forager> result = new Result<>();

        if (forager == null) {
            result.addErrorMessage("Nothing to save.");
            return result;
        }

        if (forager.getFirstName() == null) {
            result.addErrorMessage("Forager first name is required.");
        }

        if (forager.getLastName() == null) {
            result.addErrorMessage("Forager last name is required.");
        }

        if (forager.getState() == null) {
            result.addErrorMessage("State is required.");
        }

        return result;
    }

}
