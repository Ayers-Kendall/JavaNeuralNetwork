
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;

public class TrainingDataEncoder {

	int rows;
	int cols;
	float[][] finalData;
	public TrainingDataEncoder(String file, int headerLines) {
		String row;
		int encodedCols = 0;
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		
		// First pass. Counts row/cols, determines which cols need encoding, 
		// and initializes the data array to hold the encoded data
		try {
        	FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader csvReader = new BufferedReader(new InputStreamReader(fileInputStream));
            int skipCount = 0;
            while (skipCount < headerLines) {
            	csvReader.readLine();
            	skipCount++;
            }
            while ((row = csvReader.readLine()) != null) {  
            	this.rows++;
                String[] data = row.split(",");
                for (int i = 0; i < data.length; i++) {
                	if (rows == 1) {
                		arr.add(new ArrayList<String>());
                	}	
                	// if this is a discrete value (NaN) and hasn't been seen anywhere in the current column so far
                	if (!(arr.get(i).contains(data[i])) && !(isNumber(data[i]) || isRange(data[i]))){
                		arr.get(i).add(data[i]);
                	}
                	if (isRange(data[i]) && !(arr.get(i).contains("min"+Integer.toString(i)))){
                		arr.get(i).add("min"+Integer.toString(i));
                		arr.get(i).add("max"+Integer.toString(i));
                	}
            	}
            }
            // Determine needed num of encoded columns from arr sizes
            //
            //
            System.out.println(arr.toString());
            System.out.println(rows);
            System.out.println(cols);
            
            this.cols = arr.size() + encodedCols;
            csvReader.close();
		/*
            
		// Second pass. Encodes the data
        	FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader csvReader = new BufferedReader(new InputStreamReader(fileInputStream));
            int skipCount = 0;
            while (skipCount < headerLines) {
            	csvReader.readLine();
            	skipCount++;
            }
            while ((row = csvReader.readLine()) != null) {  
                String[] data = row.split(",");
                float[] inputs = Arrays.copyOfRange(data, 0, data.length - 1);
                float[] output = Arrays.copyOfRange(data, data.length - 1, data.length);
                tDataSet = new TrainingData[batchSize];
                for (int i = 0; i < batchSize; i++) {
                	tDataSet[i] = new TrainingData(inputs, output)
                }
            }
            csvReader.close(); 
          
        
        */
            
		} 
            catch (IOException e) {
    			e.printStackTrace();
            }
	}
	
	private boolean isNumber(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!(Character.isDigit(str.charAt(i)) || str.charAt(i) == '.')){
				return false;
			}
		}
		return true;
	}
	
	private boolean isRange(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '>' || str.charAt(i) == '<'){
				return true;
			}
		}
		return false;
	}
	
	
}
