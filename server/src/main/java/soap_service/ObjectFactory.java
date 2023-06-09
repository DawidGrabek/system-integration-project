
package soap_service;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the soap_service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: soap_service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetExpense }
     * 
     */
    public GetExpense createGetExpense() {
        return new GetExpense();
    }

    /**
     * Create an instance of {@link GetExpenseResponse }
     * 
     */
    public GetExpenseResponse createGetExpenseResponse() {
        return new GetExpenseResponse();
    }

    /**
     * Create an instance of {@link Unit }
     * 
     */
    public Unit createUnit() {
        return new Unit();
    }

    /**
     * Create an instance of {@link Country }
     * 
     */
    public Country createCountry() {
        return new Country();
    }

    /**
     * Create an instance of {@link AddExpense }
     * 
     */
    public AddExpense createAddExpense() {
        return new AddExpense();
    }

    /**
     * Create an instance of {@link AddExpenseResponse }
     * 
     */
    public AddExpenseResponse createAddExpenseResponse() {
        return new AddExpenseResponse();
    }

}
