package daotests;


import java.util.List;
import java.util.logging.Logger;

import junit.framework.TestCase;
import alltests.AllTests;
import business.customersubsystem.CustomerSubsystemFacade;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.DbClassAddressForTest;
import business.externalinterfaces.DbClassOrderForTest;
import business.externalinterfaces.Order;
import business.externalinterfaces.Catalog;
import business.externalinterfaces.OrderSubsystem;
import business.externalinterfaces.ProductSubsystem;
import business.ordersubsystem.OrderSubsystemFacade;
import business.productsubsystem.DbClassCatalogTypes;
import business.productsubsystem.ProductSubsystemFacade;
import dbsetup.DbQueries;

public class DbClassOrderTest  extends TestCase {
	
	static String name = "Customer Order Test";
	static Logger log = Logger.getLogger(DbClassOrderTest.class.getName());
	
	static {
		AllTests.initializeProperties();
	}
	
	
	public void testReadAllOrder() {
		List<Integer> expected = DbQueries.readAllOrderIds();
		
//		ProductSubsystem pss = new ProductSubsystemFacade();
//		DbClassCatalogTypes dbclass = pss.getGenericDbClassCatalogTypes();
		
		CustomerSubsystem css = new CustomerSubsystemFacade();
		CustomerProfile custProfile = css.getGenericCustomerProfile();
		
		OrderSubsystem oss =new OrderSubsystemFacade(custProfile);
		
		DbClassOrderForTest dbClassOrder = oss.getGenericDbClassOrder();
		
		try {
			List<Integer> found = dbClassOrder.readAllOrders(custProfile);
			assertTrue(expected.size() == found.size());
//			assertTrue(expected.size() == 1);
			
		} catch(Exception e) {
			fail("Order Lists don't match");
		}
		
	}

}
