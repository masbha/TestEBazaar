package integrationtests;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import junit.framework.TestCase;
import middleware.exceptions.DatabaseException;
import alltests.AllTests;
import business.exceptions.BackendException;
import business.externalinterfaces.Catalog;
import business.externalinterfaces.Product;
import business.externalinterfaces.ProductSubsystem;
import business.productsubsystem.ProductSubsystemFacade;
import dbsetup.DbQueries;

public class MaintainProductTest extends TestCase {
	
	static String name = "Maintain Product Test";
	static Logger log = Logger.getLogger(MaintainProductTest.class.getName());
	
	static {
		AllTests.initializeProperties();
	}
	
	
	public void testProductListStep() {
		// Add row in product table for testing
		String[] vals = DbQueries.insertProductRow();
		String expectedName = vals[2];
		int expectedCatalogId = Integer.parseInt(vals[3]);
		int productId = Integer.parseInt(vals[1]);
		// Perform test
        ProductSubsystem pss = new ProductSubsystemFacade();
      
        List<Product> products = null;
        Catalog catalog = null;
		try {
			//product = pss.getProductFromId(Integer.parseInt(vals[1]));
			List<Catalog> catalogList = pss.getCatalogList();
			for (Catalog c : catalogList) {
				if (c.getId() == expectedCatalogId) {
					catalog = c;
				}
			}
			assertTrue(catalog != null);
			products = pss.getProductList(catalog);
		}
		catch(BackendException ex){
			fail("DatabaseException: " + ex.getMessage());
		}
		finally {
			assertTrue(products != null);
			boolean nameFound = false;
			if(products != null){
				for (Product product : products) {
					if (product.getProductName().equals(expectedName) && product.getProductId() == productId) {
						nameFound=true;
						break;
					}
				}
			}
			assertTrue(nameFound);
			// Clean up table
			DbQueries.deleteProductRow(Integer.parseInt(vals[1]));
			
		}
	}

	

}