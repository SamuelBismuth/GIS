package write;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import algorithms.Mac;

public class WriteComboAlgo1 implements WriteFile<Mac> {

	private FileWriter fw;
	private PrintWriter outs;
	private String fileName;

	/**
	 * Constructor.
	 * @param array.
	 * @param fileNameExport.
	 * @exception IOException : Error writing the file.
	 */
	public WriteComboAlgo1(String fileName) {
		try {
			fileName += ".csv";
			this.fileName = fileName;
			this.fw = new FileWriter(fileName);
			this.outs = new PrintWriter(fw);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receiveData(ArrayList<Mac> array) {
		writeHeader();
		for (Mac mac : array) {
			outs.print(mac.getLineAlgo1().getIndex() + ",");
			outs.print(mac.getLineAlgo1().getMacName() + ",");
			outs.print(mac.getLineAlgo1().getSsid() + ",");
			outs.print(mac.getLineAlgo1().getFrequency() + ",");
			outs.print(mac.getLineAlgo1().getSignal() + ",");
			outs.print(mac.getLineAlgo1().getLocalisation().getLatitude() + ",");
			outs.print(mac.getLineAlgo1().getLocalisation().getLongitude() + ",");
			outs.print(mac.getLineAlgo1().getLocalisation().getAltitude() + ",");
			outs.print(mac.getLineAlgo1().getDate().getTime() + ", ");
			outs.println("Approx. w-center algo1");
		}
		writeFile();
	}

	@Override
	public void writeHeader() {
		outs.println("Index," + "Mac Name,"+ "SSID," + "Frequency," + "Signal,"  + "Lat," + "Lon," + "Alt," + "Time,");
	}

	@Override
	public void writeFile() {	
		try {
			outs.close(); 
			fw.close();
		} 
		catch (IOException ex) {
			System.out.println("Error writing file : " + ex);
		}
	}
	
	/**
	 * @return fileName.
	 */
	public String getFileName() {
		return fileName;
	}

}
