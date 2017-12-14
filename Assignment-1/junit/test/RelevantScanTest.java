package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.boehn.kmlframework.coordinates.EarthCoordinate;
import org.junit.Test;

import algorithm2.RelevantScan;
import read.SampleScan;
import read.Wifi;

public class RelevantScanTest {

	@Test
	public void testGetRevelantNumber() {
		GregorianCalendar date = new GregorianCalendar(2017, 8, 12, 22, 00, 10);
		EarthCoordinate earth = new EarthCoordinate(100.0, 34.0, 890.0);
		Wifi wifi = new Wifi("easyName", "Mac1", 5000, -50);
		Wifi wifi2 = new Wifi("easyName", "Mac2", 5000, -60);
		Wifi wifi3 = new Wifi("easyName", "Mac3", 5000, -80);
		ArrayList<Wifi> array = new ArrayList<Wifi>();
		array.add(wifi);
		array.add(wifi2);
		array.add(wifi3);
		SampleScan input = new SampleScan (
				date, 
				"id", 
				earth, 
				array
				);
		Wifi wifi4 = new Wifi("easyName", "Mac1", 5000, -40);
		Wifi wifi5 = new Wifi("easyName", "Mac3", 5000, -60);
		ArrayList<Wifi> array1 = new ArrayList<Wifi>();
		array1.add(wifi4);
		array1.add(wifi5);
		SampleScan data = new SampleScan (
				date, 
				"id", 
				earth, 
				array1
				);
		double number = RelevantScan.getRelevantNumber(data, input);
		assertEquals(0.8*0.1*0.75, number, 0.1);
	}

}