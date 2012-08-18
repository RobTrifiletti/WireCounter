import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;


public class WireCounter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numberOfWires = 0;
		HashSet<Integer> hs = new HashSet<Integer>();
		try {
			File circuitFile = new File(args[0]);
			BufferedReader fbr = new BufferedReader(new InputStreamReader(
					new FileInputStream(circuitFile), Charset.defaultCharset()));
			String line = "";
			while((line = fbr.readLine()) != null) {
				if (line.isEmpty()){
					continue;
				}

				/*
				 * Ignore meta-data info, we don't need it
				 */
				if(!line.matches("[0-9]* [0-9]* [0-9]* [0-9]* [0-9]* [0-9]* ")){
					if(line.matches("[0-9]* [0-9]*")){
						String[] sizeInfo = line.split(" ");
						numberOfWires = Integer.parseInt(sizeInfo[1]);
					}
					continue;
				}

				/*
				 * Parse each gate line and add the wire info to the hashset
				 */
				Gate g = new GateConvert(line);
				hs.add(g.getLeftWireIndex());
				hs.add(g.getRightWireIndex());
				hs.add(g.getOutputWireIndex());
			}
			int wiresCounted = hs.size();
			System.out.println("#Wires counted: " + wiresCounted);
			System.out.println("#Wires proclaimed: " + numberOfWires);
			System.out.println("Difference: " + (numberOfWires - wiresCounted));
			
			fbr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
