package read;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

import org.boehn.kmlframework.coordinates.EarthCoordinate;

import algorithm2.RelevantScan;

/**
 * This class define an object scan.
 * @author Orel and Samuel.
 */
public class SampleScan implements Comparable<SampleScan> {
	
	private GregorianCalendar time;
	private String id;
	private EarthCoordinate pointLocation;
	private ArrayList<Wifi> arrayWifi;
	private double relevantNumber;
	
	/**
	 * Constructor.
	 * @param time.
	 * @param id.
	 * @param pointLocation.
	 * @param wifi.
	 */
	public SampleScan(GregorianCalendar time, String id, EarthCoordinate pointLocation, ArrayList<Wifi> arrayWifi) {
		this.time = time;
		this.id = id;
		this.pointLocation = pointLocation;
		this.arrayWifi = arrayWifi;
		sort();
	}

	/**
	 * @return time.
	 */
	public GregorianCalendar getTime() {
		return time;
	}

	/**
	 * @return id.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return pointLocation.
	 */
	public EarthCoordinate getPointLocation() {
		return pointLocation;
	}
	
	/**
	 * @return arrayWifi.
	 */
	public ArrayList<Wifi> getArrayWifi() {
		return arrayWifi;
	}
	
	public void sort() {
		Collections.sort(arrayWifi);
	}
	
	/**
	 * @return arrayStrongerWifi.
	 */
	public ArrayList<Wifi> getArrayStrongerWifi() {
		if (arrayWifi.size() <= 10) return arrayWifi;
		else {
			ArrayList<Wifi> arrayStrongerWifi = new ArrayList<Wifi>();
			for (int i = 0; i < 10; i++)  arrayStrongerWifi.add(arrayWifi.get(i));
			return arrayStrongerWifi;
		}
	}
	
	/**
	 * @return wifiNetworks.
	 */
	public int getWifiNetworks() {
		return getArrayStrongerWifi().size();
	}
	
	/**
	 * @param id
	 * @return this.
	 */
	public SampleScan sameId(String id) {
		if (this.getId().equals(id)) return this;
		return null;
	}
	
	/**
	 * @param input
	 */
	protected void setRelevantNumber(SampleScan input) {
		this.relevantNumber = RelevantScan.getRelevantNumber(this, input);
	}
	
	/**
	 * @return relevanrNumber.
	 */
	protected double getRelevantNumber () {
		return relevantNumber;
	}
	/**
	 * @param mac
	 * @return wifi if contains the same mac.
	 * @return null if not contains the same mac.
	 */
	protected Wifi containsSameMac(String mac) {
		for (Wifi wifi : arrayWifi) 
			if (wifi.getMac().equals(mac))
				return wifi;
		return null;
	}

	protected ArrayList<Wifi> getArrayStrongerWifiConstantNumber(int numberConstant) {
		if (numberConstant > this.getWifiNetworks()) return this.getArrayStrongerWifi();
		ArrayList<Wifi> array = new ArrayList<Wifi>();
		for (int i = 0; i < numberConstant; i++) 
			array.add(this.getArrayStrongerWifi().get(i));
		return array;
	}

	/**
	 * Compare the relevant number.
	 */
	public int compareTo(SampleScan scan) {
		return Double.compare(scan.getRelevantNumber(), this.relevantNumber);
	}
}