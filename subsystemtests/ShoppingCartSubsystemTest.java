package subsystemtests;

import integrationtests.CheckoutTest;

import java.util.logging.Logger;

import dbsetup.DbQueries;
import business.customersubsystem.CustomerSubsystemFacade;
import business.exceptions.BackendException;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.ShoppingCart;
import business.externalinterfaces.ShoppingCartSubsystem;
import business.shoppingcartsubsystem.ShoppingCartSubsystemFacade;
import alltests.AllTests;
import junit.framework.TestCase;

public class ShoppingCartSubsystemTest extends TestCase {
	static String name = "ShoppingCart Subsystem Test";
	static Logger log = Logger.getLogger(CheckoutTest.class.getName());
	
	static{
		AllTests.initializeProperties();
	}
	
	public void testMakeSavedCartLive(){
		ShoppingCart expected = DbQueries.readSavedCart();
		
		ShoppingCartSubsystem scs = ShoppingCartSubsystemFacade.INSTANCE;
		
		CustomerSubsystem css = new CustomerSubsystemFacade();
		CustomerProfile custProfile = css.getGenericCustomerProfile();
		scs.setCustomerProfile(custProfile);
		try {
			scs.retrieveSavedCart();
		} catch (BackendException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scs.makeSavedCartLive();
		ShoppingCart found = scs.getLiveCart();
		
		assertEquals(expected.getCartItems().size(), found.getCartItems().size());
	}

}
