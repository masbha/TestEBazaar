package daotests;

import java.util.List;
import java.util.logging.Logger;

import business.customersubsystem.CreditCardImpl;
import business.customersubsystem.CustomerSubsystemFacade;
import business.exceptions.BackendException;
import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.DbClassAddressForTest;
import dbsetup.DbQueries;
import junit.framework.TestCase;
import alltests.AllTests;

public class DbClassCreditCard extends TestCase {
	static String name = "DbClassCustomerProfile Test";
	static Logger log = Logger.getLogger(DbClassCreditCard.class.getName());
	
	static {
		AllTests.initializeProperties();
	}
	
	
	public void testReadCreditcard() {
		CreditCardImpl expected = DbQueries.readCreditcard("1");
		
		//test real dbclass address
		CustomerSubsystem css = new CustomerSubsystemFacade();	
		try {
			css.initializeCustomer(1, 0);		
			CreditCard actual = css.getDefaultPaymentInfo();
			assertTrue(expected.getCardNum().equals(actual.getCardNum()));
			assertTrue(expected.getCardType().equals(actual.getCardType()));
			assertTrue(expected.getExpirationDate().equals(actual.getExpirationDate()));
			assertTrue(expected.getNameOnCard().equals(actual.getNameOnCard()));
			
		} catch(Exception e) {
			fail("credit card doesn't match");
		}
		
	}
}
