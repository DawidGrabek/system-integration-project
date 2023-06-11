package com.project.system_integration.soap_endpoints;

import com.project.system_integration.entities.SocialExpense;
import com.project.system_integration.entities.Unit;
import com.project.system_integration.repositories.CountryRepository;
import com.project.system_integration.repositories.InflationRepository;
import com.project.system_integration.repositories.SocialExpensesRepository;
import com.project.system_integration.repositories.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap_service.*;

import java.math.BigInteger;
import java.util.Optional;

@Endpoint
@RequiredArgsConstructor
@CrossOrigin
public class SocialExpenseEndpoint {
    private static final String NAMESPACE_URI = "http://system_integration.pl/soap_service";
    private final SocialExpensesRepository repository;
    private final UnitRepository repositoryUnit;
    private final CountryRepository repositoryCountry;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getExpense")
    @ResponsePayload
    public GetExpenseResponse getExpense(@RequestPayload GetExpense request) {
        GetExpenseResponse response = new GetExpenseResponse();
        System.out.println("/////////////////////////////////// getExpense");
//        get unit object with required title
        Optional<Unit> u = repositoryUnit.findByTitle(request.getUnitTitle());
        if(u.isPresent()) {
            System.out.println("unit exist");
        }else {
            System.out.println("unit doesnt exist");
        }
//        get expense with required year and unit
        SocialExpense expense = repository.findByYearAndUnit(request.getYear().intValue(), u.get()).get();
//        setting values of response
        response.setValue(expense.getValue());
        response.setYear(BigInteger.valueOf(expense.getYear()));
        response.setCountry(mapCountry(expense.getCountry()));
        response.setUnit(mapUnit(expense.getUnit()));
        return response;
    }
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addExpense")
    @ResponsePayload
    public AddExpenseResponse addExpense(@RequestPayload AddExpense request) {
        Optional<com.project.system_integration.entities.Country> OptionalC = repositoryCountry.findByName(request.getCountry().getName());
        AddExpenseResponse response = new AddExpenseResponse();
        if(OptionalC.isEmpty()) {
            response.setStatus("nie ma tego typu danych");
            return response;
        }
        com.project.system_integration.entities.Country c = OptionalC.get();

        Optional<Unit> OptionalU = repositoryUnit.findByTitle(request.getUnit().getTitle());

        if(OptionalU.isEmpty()) {
            response.setStatus("nie ma takiej jednostki");
            return response;
        }
        Unit u = OptionalU.get();
        SocialExpense expense = new SocialExpense();
        expense.setCountry(c);
        expense.setUnit(u);
        expense.setYear(request.getYear().intValue());
        expense.setValue(request.getValue());
        repository.save(expense);


        response.setStatus("Udalo sie");
        return response;
    }


    private soap_service.Unit mapUnit(Unit unit) {
        soap_service.Unit soapUnit = new soap_service.Unit();
        soapUnit.setUnit(unit.getUnit());
        soapUnit.setTitle(unit.getTitle());
        return soapUnit;
    }

    private soap_service.Country mapCountry(com.project.system_integration.entities.Country country) {
        Country soapCountry = new Country();
        soapCountry.setCode(country.getCode());
        soapCountry.setName(country.getName());
        return soapCountry;
    }

//    private Unit mapModelUnit(soap_service.Unit unit) {
//        Unit u = new Unit();
//        u.setTitle(unit.getTitle());
//        u.setUnit(unit.getUnit());
//        return u;
//    }
//
//    private com.project.system_integration.entities.Country mapModelCountry(Country country) {
//        com.project.system_integration.entities.Country c = new com.project.system_integration.entities.Country();
//        c.setCode(country.getCode());
//        c.setName(country);
//    }
}
