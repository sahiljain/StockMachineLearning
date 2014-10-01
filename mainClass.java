import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;


public class mainClass {

	public static float testStrategy(ArrayList<Stock> stocks, int period, float multiplier, float bodyLength){

		int totalTrials = 0;
		int totalSuccesses = 0;
		for (Iterator<Stock> it = stocks.iterator(); it.hasNext(); ) {
		   // Stock apple = it.next();
			Stock apple = new Stock(it.next());
			apple.calculateVolumeOutliers(period, multiplier);
			apple.calculateMarubozus(bodyLength);
			int trials = 0, successes = 0;
			
			
			for(int x = 3; x < apple.bars.size()-3400; x++){

				Bar b = apple.bars.get(x);
				if(b.volumeOutlier && b.marubozu){
					trials++;
					if(b.positive){
						//if(((apple.bars.get(x-1).close+apple.bars.get(x-1).open)) < 2.0f*b.close){
						if(((apple.bars.get(x-1).close+apple.bars.get(x-2).close)+apple.bars.get(x-3).close) < 3.0f*b.close){
							System.out.println(apple.symbol + " " + b.date);
							successes++;
						}
					}else{
						//if(((apple.bars.get(x-1).close+apple.bars.get(x-1).open)) > 2.0f*b.close){
						if(((apple.bars.get(x-1).close+apple.bars.get(x-2).close)+apple.bars.get(x-3).close) > 3.0f*b.close){
							System.out.println(apple.symbol + " " + b.date);
							successes++;
						}
					}

				}
			}
			totalTrials+=trials;
			totalSuccesses+=successes;
			
			//System.out.println(apple.symbol + ": success %: " + (100f*((float)successes/(float)trials)));
			//System.out.println(apple.symbol + " " + successes + "," + trials);



		} 

		System.out.println("period: " + period + ", multiplier: " + multiplier + ",  average success %: " + (100f*((float)totalSuccesses/(float)totalTrials)));
		//System.out.println("Took " + ((System.currentTimeMillis()-starttime)/1000f) + " seconds.");
		System.out.println("total trials: " + totalTrials);
		return  (100f*((float)totalSuccesses/(float)totalTrials));
	}
	
	
	public static void main(String[] args) {
		
		long starttime = System.currentTimeMillis();
		System.out.println("hi");
		ArrayList<Stock>stocks = new ArrayList<Stock>();
		File folder = new File("unadjusteddata");
		File[] listOfFiles = folder.listFiles();
		for (int j = 0; j < listOfFiles.length; j++) {
			File file = listOfFiles[j];
			if (file.isFile()) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line;
					Stock apple = new Stock();
					while((line = br.readLine())!= null){
						apple.addBar(line);
					}
					stocks.add(apple);
					br.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		
		int bestperiod = 0;
		float bestmultiplier = 0f ,highestsuccess = 0f, bestBodyLength = 0f;
		//for(int period = 75; period < 80; period+=1){
			//for(float multiplier = 3.7f; multiplier < 3.91f; multiplier+=0.1f){
			  //for(float bodyLength = 0.8f; bodyLength < 1.00f; bodyLength+=0.025){
				float success = testStrategy(stocks, 75, 3.8f, 0.95f);
				/*if(success > highestsuccess){
					highestsuccess = success;
				//	bestBodyLength = bodyLength;
					bestmultiplier = multiplier;
					bestperiod = period;
				}*/
				//System.out.println(bodyLength + "," + highestsuccess);
				//System.out.println("period, multiplier, success: " + bestperiod + "," + bestmultiplier + "," + highestsuccess);
			//}
		//}
		
		System.out.println("Took " + ((System.currentTimeMillis()-starttime)/1000f) + " seconds.");
	}
	

}
