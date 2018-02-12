package com.revature.test.admin.cukes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.revature.test.admin.pom.BatchListTab;
import com.revature.test.utils.WaitToLoad;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BatchListCukes {
	@Given("^The Batch List Tab is clicked$")
	public static void the_Batch_List_Tab_is_clicked(WebDriver wd) throws Throwable {
		BatchListTab.clickBatchListTab(wd).click();
	}

	@Then("^All Batches text is visible$")
	public static boolean all_Batches_text_is_visible(WebDriver wd) throws Throwable {

		try {
			// Find the Header to verify that you are in the Batch List Tab
			BatchListTab.findAllBatchesHeader(wd);
			return true;
		} catch (Throwable e) {
			System.out.println("Failed to find All Batches header");
			return false;
		}
	}
	
	@Given("^the first batch is clicked$")
	public static String the_first_batch_is_clicked(WebDriver wd) throws Throwable {
		// Click the first batch in the list
		String batchName = "";
		try {
			batchName = BatchListTab.getFirstBatchName(wd).getText();
		} catch (Throwable e) {
			System.out.println("Failed to find first batch name");
		}
		try {
			BatchListTab.getFirstBatchName(wd).click();
		} catch (Throwable e) {
			System.out.println("Failed to click first batch name");
		}
		return batchName;
	}

	@When("^the list of associates is grabbed$")
	public static boolean the_list_of_associates_is_grabbed(WebDriver wd, String batchName) throws Throwable {
		// Grab the associates IDs to be compared and store in a HashMap
		try {
			List<WebElement> ID = BatchListTab.getAssociatesIDs(wd);
			HashMap<String, String> compareID = new HashMap<String, String>();
			for (WebElement e : ID) {
				compareID.put(e.getText(), batchName);
			}
			return associates_should_match_the_associate_list(wd, batchName, compareID);
		
		} catch (Throwable e) {
			System.out.println("Could not get Associate IDs");
			return false;
		}
	}
		
		// Get the batch name to verify everyone in that batch is in correct batch
		// Sends to helper function
		

	@Then("^associates should match the associate list$")
	public static boolean associates_should_match_the_associate_list(WebDriver wd, String batchName, HashMap<String,String> IDs) throws Throwable {
		try {
			// Switch to Associate List Tab
			WaitToLoad.findDynamicElement(wd,By.xpath("/html/body/app/div/app-batch-details/app-navbar/nav/div/ul[1]/li[4]"), 10).click();
		} catch (Throwable e) {
			System.out.println("Can't switch to Associate List tab");
			return false;
		}

		try {
//			// Verify that the IDs grabbed from first batch are all in the correct batch by looking at the associate list
//			List<WebElement> associateIDs = BatchListTab.grabAssociatesIDs(wd);
//			List<WebElement> associateRows = BatchListTab.grabAssociatesBatchInfo(wd);
//			// Search through the List of Associates taken from Associate List Tab
//			for (int i = 0; i < associateIDs.size(); i++) {
//				// If the IDs match
//				if (IDs.containsKey(associateIDs.get(i).getText())) {
//					// and if the batch name is in the same row
//					if (associateRows.get(i).getText().contains(batchName)) {
//						IDs.remove(associateIDs.get(i).getText());
//					}
//				}
//			}
//			// Return true when the map is empty; All of the IDs in the map were found in the Associate List with same batch name
//			return IDs.isEmpty();
			
	        // remove potential dups
	        // increase lookup speed to O(1)
			List<WebElement> associateRows = BatchListTab.grabAssociatesBatchInfo(wd);
	        // remove potential dups
	        // increase lookup speed to O(1)
	        Set<String> listHash = new HashSet<String>();
	        for (WebElement val : associateRows)
	        {
	            listHash.add(val.getText());
	        }

	        for (HashMap.Entry<String, String> entry : IDs.entrySet())
	        {
	            if ( !((listHash.contains(entry.getValue())) && (listHash.contains(entry.getKey()))) )
	                return false;
	        }
	        return true;
		} catch (Throwable e) {
			System.out.println("Can't grab associate list ID's");
			return false;
		}
		
	}
}
