package test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import org.boehn.kmlframework.coordinates.EarthCoordinate;
import org.junit.Test;

import algorithm1.Mac;
import algorithm1.MacLocation;
import filter.FilteringKmlId;
import library.InputException;
import read.SampleScan;
import read.Wifi;

public class FilteringIdTest {

	/**
	 * There is no assert equals because everything depends of the user input.
	 * If the user input EasyId so, the programe keep runing, else, we created an exception.
	 */
	@Test (expected = InputException.class)
	public void testFilteringBy() {
		GregorianCalendar date = new GregorianCalendar(2017, 8, 12, 22, 00, 10);
		EarthCoordinate earth = new EarthCoordinate(100.0, 34.0, 890.0);
		Wifi wifi = new Wifi("easyName", "easyMac", 5000, -90);
		ArrayList<Wifi> wifis = new ArrayList<Wifi>();
		wifis.add(wifi);
		SampleScan scan = new SampleScan(date, "easyId", earth, wifis);
		ArrayList<SampleScan> array = new ArrayList<SampleScan>();
		array.add(scan);
		EarthCoordinate tlv = new EarthCoordinate(34.78176760979483,32.08529989645831, 0.0);//TLV
		String macname = "easyMac";
		int signal = -90;
		MacLocation ml1 = new MacLocation(tlv, signal);
		ArrayList<MacLocation> array2 = new ArrayList<MacLocation>();
		array2.add(ml1);
		Mac m = new Mac(macname, array2);
		ArrayList<Mac> macs = new ArrayList<Mac>();
		macs.add(m);
		
		FilteringKmlId filter = new FilteringKmlId();
		try {
			filter.filteringBy(array,macs);
		} 
		catch (InputException ex) {
			ex.printStackTrace();
		}
	}
	
}