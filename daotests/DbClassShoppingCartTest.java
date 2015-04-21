package daotests;

import integrationtests.CheckoutTest;

import java.util.List;
import java.util.logging.Logger;

import business.shoppingcartsubsystem.ShoppingCartSubsystemFacade;

import dbsetup.DbQueries;
import business.customersubsystem.CustomerSubsystemFacade;
import business.externalinterfaces.CartItem;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.DbClassAddressForTest;
import business.externalinterfaces.DbClassShoppingCartForTest;
import business.externalinterfaces.ShoppingCart;
import business.externalinterfaces.ShoppingCartSubsystem;
import alltests.AllTests;
import junit.framework.TestCase;

public class DbClassShoppingCartTest extends TestCase {
	static String name = "Shopping Cart Test";
	static Logger log = Logger.getLogger(CheckoutTest.class.getName());
	
	static{
		AllTests.initializeProperties();
	}
	
	public void testReadShoppingCartItems(){
		ShoppingCart expected = DbQueries.readSavedCart();
		
		
		//test real dbclass shoppingcart
		CustomerSubsystem css = new CustomerSubsystemFacade();
		CustomerProfile customerProfile = css.getGenericCustomerProfile();
				
		ShoppingCartSubsystem scs= ShoppingCartSubsystemFacade.INSTANCE;
		
		DbClassShoppingCartForTest dbclass = scs.getGenericDbClassShoppingCart();
		try{
			ShoppingCart found = dbclass.readSavedCart(customerProfile);
			assertTrue(expected.getCartItems().size()==found.getCartItems().size());
		}catch(Exception e){
			fail("CartItems List don't match");
		}
	}
}
