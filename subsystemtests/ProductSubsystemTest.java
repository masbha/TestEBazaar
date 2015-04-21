package subsystemtests;

import integrationtests.BrowseAndSelectTest;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import business.exceptions.BackendException;
import business.externalinterfaces.Catalog;
import business.externalinterfaces.Product;
import business.externalinterfaces.ProductSubsystem;
import business.productsubsystem.ProductSubsystemFacade;
import dbsetup.DbQueries;
import junit.framework.TestCase;
import alltests.AllTests;

public class ProductSubsystemTest extends TestCase {
	
	static String name = "Product Subsystem Test";
	static Logger log = Logger.getLogger(BrowseAndSelectTest.class.getName());
	
	static {
		AllTests.initializeProperties();
	}
	
	public void testGetCatalogNames() {
		//setup
		/*
		 * Returns a String[] with values:
		 * 0 - query
		 * 1 - catalog id
		 * 2 - catalog name
		 */
		String[] insertResult = DbQueries.insertCatalogRow();
		String expected = insertResult[2];
		
		ProductSubsystem pss = new ProductSubsystemFacade();
		try {
			List<String> found = pss.getCatalogList()
				      .stream()
				      .map(cat -> cat.getName())
				      .collect(Collectors.toList());
			boolean valfound = false;
			for(String catData : found) {
				
					if(catData.equals(expected)) valfound = true;
				
			}
			assertTrue(valfound);
			
		} catch(Exception e) {
			fail("Inserted value not found");
		} finally {
			DbQueries.deleteCatalogRow(Integer.parseInt(insertResult[1]));
		}
	
	}
	
	public void testGetProductNamesByCatalog() {
		//setup
		/*
		 * Returns a String[] with values:
		 * 0 - query
		 * 1 - product id
		 * 2 - product name
		 * 3 - catalog id
		 */
		String[] insertResult = DbQueries.insertProductRow();
		String expected = insertResult[2];
		int expectedCatalogId = Integer.parseInt(insertResult[3]);
		int productId = Integer.parseInt(insertResult[1]);
		List<Product> products = null;
        Catalog catalog = null;
        ProductSubsystem pss = new ProductSubsystemFacade();
		try {
			List<Catalog> catalogList = pss.getCatalogList();
			for (Catalog c : catalogList) {
				if (c.getId() == expectedCatalogId) {
					catalog = c;
				}
			}
			assertTrue(catalog != null);
			products = pss.getProductList(catalog);
		}
		catch(Exception ex){
			fail("Inserted value not found");
		}
		finally {
			assertTrue(products != null);
			boolean nameFound = false;
			if(products != null){
				for (Product product : products) {
					if (product.getProductName().equals(expected) && product.getProductId() == productId) {
						nameFound=true;
						break;
					}
				}
			}
			assertTrue(nameFound);
			// Clean up table
			DbQueries.deleteProductRow(Integer.parseInt(insertResult[1]));
			
		}
	
	}
	
	public void testGetProductFromId() {
		//setup
		/*
		 * Returns a String[] with values:
		 * 0 - query
		 * 1 - product id
		 * 2 - product name
		 * 3 - catalog id
		 */
		String[] insertResult = DbQueries.insertProductRow();
		String expected = insertResult[2];
		int productId = Integer.parseInt(insertResult[1]);
		Product product = null;
        ProductSubsystem pss = new ProductSubsystemFacade();
		try {
			product = pss.getProductFromId(productId);
		}
		catch(Exception ex){
			fail("Product not found from product id");
		}
		finally {
			assertTrue(product != null);
			boolean nameFound = false;
			if (product != null){
				if (product.getProductName().equals(expected) && product.getProductId() == productId) {
					nameFound = true;
				}
			}
			assertTrue(nameFound);
			// Clean up table
			DbQueries.deleteProductRow(Integer.parseInt(insertResult[1]));
			
		}
	
	}
	
	public void testProductDelete() {
		//setup
		/*
		 * Returns a String[] with values:
		 * 0 - query
		 * 1 - product id
		 * 2 - product name
		 * 3 - catalog id
		 */
		String[] insertResult = DbQueries.insertProductRow();
		List<Product> productList =  DbQueries.readAllProducts();
		int productListSize = productList.size();
        ProductSubsystem pss = new ProductSubsystemFacade();
        productList.get(productListSize - 1);
		try {
			pss.deleteProduct(productList.get(productListSize - 1));
		}
		catch (Exception ex) {
			fail("Product deletion fail");
		}
		finally {
			productList =  DbQueries.readAllProducts();
			int productListAfterDelete = productList.size();
			
			assertTrue(productListAfterDelete == productListSize - 1);
			
		}
	
	}
	
}
