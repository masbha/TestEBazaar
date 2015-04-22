package subsystemtests;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import business.customersubsystem.CustomerSubsystemFacade;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderSubsystem;
import business.externalinterfaces.ProductSubsystem;
import business.ordersubsystem.OrderSubsystemFacade;
import business.productsubsystem.ProductSubsystemFacade;
import dbsetup.DbQueries;
import junit.framework.TestCase;
import alltests.AllTests;

public class OrderSubsystemTest extends TestCase {
	
	static String name = "Order Subsystem Test";
	static Logger log = Logger.getLogger(OrderSubsystemTest.class.getName());
	
	static {
		AllTests.initializeProperties();
	}
	
	public void testReviewOrder() {
		//setup
		/*
		 * Returns a String[] with values:
		 * 0 - query
		 * 1 - order id
		 * 2 - catalog name
		 */
		String[] insertResult = DbQueries.insertOrderRow();
		String expected = insertResult[1];
		
		CustomerSubsystem css = new CustomerSubsystemFacade();
		CustomerProfile custProfile = css.getGenericCustomerProfile();
		
		OrderSubsystem oss = new OrderSubsystemFacade(custProfile);
		boolean valfound = false;
		try {
			List<Order> found = oss.getOrderHistory();
			for (Order order : found) {
				if (order.getOrderId() == Integer.parseInt(expected)) {
					valfound = true;
					break;
				}
			}
			assertTrue(valfound);
			
			
		} catch(Exception e) {
			fail("Inserted value not found");
		} finally {
			DbQueries.deleteOrderRow(Integer.parseInt(insertResult[1]));
		}
	
	}
	
}
