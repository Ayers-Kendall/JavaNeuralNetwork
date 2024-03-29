import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class NeuralNetwork {
 	
    // Layers
    static Layer[] layers;
    
    // Training data
    static TrainingData[] dataSet;
    
    // Batches of training data arrays
    static TrainingDataBatch[] batches; 
   
    // Main Method
    public static void main(String[] args) {
    	TrainingDataEncoder encodedData = new TrainingDataEncoder("src/res/dataset_31_credit-g.csv", 1);
    	
    	//readTrainingData(encodedData, 10);
    	
    	/*
        // Set the Min and Max weight value for all Neurons
    	Neuron.setRangeWeight(-1,1);
    	
    	// Create the layers
    	layers = new Layer[3];
    	layers[0] = null; // Input Layer 0 , # inputs
    	layers[1] = new Layer(numIn,numOut1); // Hidden Layer # inputs , # out1
    	layers[2] = new Layer(numOut1,numOutFinal); // Output Layer #out1, #outFinal
        
        // CHANGE LATER TO OUTPUT TO FILE
        
        System.out.println("============");
        System.out.println("Output before training");
        System.out.println("============");
        for(int i = 0; i < tDataSet.length; i++) {
            forward(tDataSet[i].data);
            System.out.println(layers[layers.length - 1].toString());
        }
       
        train(1000000, 0.05f);

        System.out.println("============");
        System.out.println("Output after training");
        System.out.println("============");
        for(int i = 0; i < tDataSet.length; i++) {
            forward(tDataSet[i].data);
            System.out.println(layers[layers.length - 1].toString());
        }
        */
    }
    
    public static void readTrainingData(TrainingDataEncoder dataEncoder, int batchSize){
    	int rows = dataEncoder.rows;
    	int cols = dataEncoder.cols;
    	float[][] data = dataEncoder.finalData;
        int batchCount = 0;
        TrainingData[] dataSet = new TrainingData[batchSize];;
    	for (int i = 0; i < rows; i++) {
            if ((i != 0) && (i % batchSize == 0)) {
            	batchCount++;
            	batches[batchCount] = new TrainingDataBatch(dataSet);
            	dataSet = new TrainingData[batchSize];
            }
            float[] inputs = Arrays.copyOfRange(data[i], 0, data[i].length - 1);
            float[] output = Arrays.copyOfRange(data[i], data[i].length - 1, data[i].length);
            dataSet[i % batchSize] = new TrainingData(inputs, output);       
        }
    }
    
    public static void forward(float[] inputs) {
    	// First bring the inputs into the input layer layers[0]
    	layers[0] = new Layer(inputs);
    	
        for(int i = 1; i < layers.length; i++) {
        	for(int j = 0; j < layers[i].neurons.length; j++) {
        		float sum = 0;
        		for(int k = 0; k < layers[i-1].neurons.length; k++) {
        			sum += layers[i-1].neurons[k].value*layers[i].neurons[j].weights[k];
        		}
        		//sum += layers[i].neurons[j].bias; // TODO add in the bias 
        		layers[i].neurons[j].value = StatUtil.sigmoid(sum);
        	}
        } 	
    }
    
    public static void backward(float learning_rate, TrainingData tData) {
    	
    	int number_layers = layers.length;
    	int out_index = number_layers-1;
    	
    	// Update the output layers 
    	// For each output
    	for(int i = 0; i < layers[out_index].neurons.length; i++) {
    		// and for each of their weights
    		float output = layers[out_index].neurons[i].value;
    		float target = tData.expectedOutput[i];
    		float derivative = output-target;
    		float delta = derivative*(output*(1-output));
    		layers[out_index].neurons[i].gradient = delta;
    		for(int j = 0; j < layers[out_index].neurons[i].weights.length;j++) { 
    			float previous_output = layers[out_index-1].neurons[j].value;
    			float error = delta*previous_output;
    			layers[out_index].neurons[i].cache_weights[j] = layers[out_index].neurons[i].weights[j] - learning_rate*error;
    		}
    	}
    	
    	// Update all the subsequent hidden layers
    	for(int i = out_index-1; i > 0; i--) {
    		// For all neurons in that layers
    		for(int j = 0; j < layers[i].neurons.length; j++) {
    			float output = layers[i].neurons[j].value;
    			float gradient_sum = sumGradient(j,i+1);
    			float delta = (gradient_sum)*(output*(1-output));
    			layers[i].neurons[j].gradient = delta;
    			// And for all their weights
    			for(int k = 0; k < layers[i].neurons[j].weights.length; k++) {
    				float previous_output = layers[i-1].neurons[k].value;
    				float error = delta*previous_output;
    				layers[i].neurons[j].cache_weights[k] = layers[i].neurons[j].weights[k] - learning_rate*error;
    			}
    		}
    	}
    	
    	// Here we do another pass where we update all the weights
    	for(int i = 0; i< layers.length;i++) {
    		for(int j = 0; j < layers[i].neurons.length;j++) {
    			layers[i].neurons[j].update_weight();
    		}
    	}
    	
    }
    
    // This function sums up all the gradient connecting a given neuron in a given layer
    public static float sumGradient(int n_index,int l_index) {
    	float gradient_sum = 0;
    	Layer current_layer = layers[l_index];
    	for(int i = 0; i < current_layer.neurons.length; i++) {
    		Neuron current_neuron = current_layer.neurons[i];
    		gradient_sum += current_neuron.weights[n_index]*current_neuron.gradient;
    	}
    	return gradient_sum;
    }
 
    
    // This function is used to train forward and backward.
    public static void train(int training_iterations, float learning_rate) {
    	for(int i = 0; i < training_iterations; i++) {
    		for(int j = 0; j < dataSet.length; j++) {
    			forward(dataSet[j].data);
    			backward(learning_rate,dataSet[j]);
    		}
    	}
    }
}