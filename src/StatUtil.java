
public class StatUtil {
	
	// Get a random numbers between min and max
    public static float randomFloat(float min, float max) {
        float a = (float) Math.random();
        float num = min + (float) Math.random() * (max - min);
        if(a < 0.5)
            return num;
        else
            return -num;
    }
    
    public static float sigmoid(float x) {
        return (float) (1/(1+Math.pow(Math.E, -x)));
    }
    
    public static float sigmoidDerivative(float x) {
        return sigmoid(x)*(1-sigmoid(x));
    }
    
    public static float tanH(float x) {
    	return (float) Math.tanh(x);
    }
    
    public static float tanHDerivative(float x) {
    	return (float) (1 - Math.pow(tanH(x), 2));
    }
    
    public static float reLu(float x) {
    	return Math.max(0, x);
    }
    
    public static float reLuDerivative(float x) {
    	return (float) (x < 0 ? 0.0 : 1.0);
    }
    
    public static float lReLuDerivative(float x) {
    	return (float) (x < 0 ? 0.01 : 1.0);
    }
    
    public static float lReLu(float x) {
    	return (float) Math.max(0.01 * x, x);
    }
    
    // Used for the backpropagation
    public static float squaredError(float output,float target) {
    	return (float) (0.5*Math.pow(2,(target-output)));
    }
    
    // Used to calculate the overall error rate (not used yet)
    public static float sumSquaredError(float[] outputs,float[] targets) {
    	float sum = 0;
    	for(int i=0;i<outputs.length;i++) {
    		sum += squaredError(outputs[i],targets[i]);
    	}
    	return sum;
    }
}