package daotests;


import java.util.List;
import java.util.logging.Logger;

import junit.framework.TestCase;
import alltests.AllTests;
import business.externalinterfaces.Catalog;
import business.externalinterfaces.ProductSubsystem;
import business.productsubsystem.DbClassCatalogTypes;
import business.productsubsystem.ProductSubsystemFacade;
import dbsetup.DbQueries;

public class DbClassCatalogTypesTest  extends TestCase {
	
	static String name = "Maintain Catalog Test";
	static Logger log = Logger.getLogger(DbClassCatalogTypesTest.class.getName());
	
	static {
		AllTests.initializeProperties();
	}
	
	
	public void testReadAllCatalog() {
		List<Catalog> expected = DbQueries.readAllCatalogs();
		
		ProductSubsystem pss = new ProductSubsystemFacade();
		DbClassCatalogTypes dbclass = pss.getGenericDbClassCatalogTypes();
		
		try {
			List<Catalog> found = dbclass.getCatalogTypes().getCatalogs();
			assertTrue(expected.size() == found.size());
			
		} catch(Exception e) {
			fail("Catalog Lists don't match");
		}
		
	}

}
