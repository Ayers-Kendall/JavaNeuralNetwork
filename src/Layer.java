public class Layer {
	public Neuron[] neurons;
	
	// Constructor for the hidden and output layer
	public Layer(int inNeurons,int numberNeurons) {
		this.neurons = new Neuron[numberNeurons];
		
		for(int i = 0; i < numberNeurons; i++) {
			float[] weights = new float[inNeurons];
			for(int j = 0; j < inNeurons; j++) {
				weights[j] = StatUtil.randomFloat(Neuron.minWeightValue, Neuron.maxWeightValue);
			}
			neurons[i] = new Neuron(weights,StatUtil.randomFloat(0, 1));
		}
	}
	
	public String toString() {
		String output = "";
		for (int i = 0; i < neurons.length; i++) {
			output += neurons[i].value + " ";
		}
		return output;
	}
	
	// Constructor for the input layer
	public Layer(float input[]) {
		this.neurons = new Neuron[input.length];
		for(int i = 0; i < input.length; i++) {
			this.neurons[i] = new Neuron(input[i]);
		}
	}
}