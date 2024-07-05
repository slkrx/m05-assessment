package learn.foraging.domain;

import learn.foraging.data.ForagerRepository;
import learn.foraging.models.Forage;
import learn.foraging.models.Forager;

import java.util.ArrayList;
import java.util.List;

public class ForagerService {

    private final ForagerRepository repository;

    private final String[] stateAbbreviations = {
                "AL", // Alabama
                "AK", // Alaska
                "AZ", // Arizona
                "AR", // Arkansas
                "CA", // California
                "CO", // Colorado
                "CT", // Connecticut
                "DE", // Delaware
                "FL", // Florida
                "GA", // Georgia
                "HI", // Hawaii
                "ID", // Idaho
                "IL", // Illinois
                "IN", // Indiana
                "IA", // Iowa
                "KS", // Kansas
                "KY", // Kentucky
                "LA", // Louisiana
                "ME", // Maine
                "MD", // Maryland
                "MA", // Massachusetts
                "MI", // Michigan
                "MN", // Minnesota
                "MS", // Mississippi
                "MO", // Missouri
                "MT", // Montana
                "NE", // Nebraska
                "NV", // Nevada
                "NH", // New Hampshire
                "NJ", // New Jersey
                "NM", // New Mexico
                "NY", // New York
                "NC", // North Carolina
                "ND", // North Dakota
                "OH", // Ohio
                "OK", // Oklahoma
                "OR", // Oregon
                "PA", // Pennsylvania
                "RI", // Rhode Island
                "SC", // South Carolina
                "SD", // South Dakota
                "TN", // Tennessee
                "TX", // Texas
                "UT", // Utah
                "VT", // Vermont
                "VA", // Virginia
                "WA", // Washington
                "WV", // West Virginia
                "WI", // Wisconsin
                "WY"  // Wyoming
    };

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
//        Result<Forage> result = validate(forager);
//        if (!result.isSuccess()) {
//            return result;
//        }
//
//        result.setPayload(forageRepository.add(forage));
//
//        return result;
        return null;
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

    public void validateStateAbb(Forager forager, Result<Forager> result) {
        if(!List.of(stateAbbreviations).contains(forager.getState()))
            result.addErrorMessage("State abbreviation must be a valid state.");
    }

    public void validateNotDuplicate(Forager forager, Result<Forager> result) {
       Forager foragerFound = repository.findByKey(forager.getFirstName(), forager.getLastName(), forager.getState());
       if (foragerFound != null) {
           result.addErrorMessage("Forager first name, last name, and state combination must be unique.");
       }
    }

    public Result<Forager> validateNulls(Forager forager) {
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
