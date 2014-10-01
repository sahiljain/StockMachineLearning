import java.util.ArrayList;
import java.util.Arrays;


public class Stock {
	
	ArrayList<Bar> bars;
	String symbol = "";
	

	public Stock(){
		bars = new ArrayList<Bar>();
	}
	
	public Stock(Stock another){
		bars = new ArrayList<Bar>();
		for(Bar b : another.bars){
			bars.add(new Bar(b));
		}
		symbol = another.symbol + "";
	}
	
	public void addBar(String s){
		Bar b = new Bar(s);
		addBar(b);
		if(symbol.length() == 0)symbol=b.symbol;
		
	}
	
	public void addBar(Bar b){
		bars.add(b);
	}
	
	public int getSize(){
		return bars.size();
	}
	
	public Bar getBar(int i){
		return bars.get(i);
	}
	
	public void calculateMarubozus(float bodyLength){
		for(int i = 0;i < bars.size(); i++){
			if(Math.abs(bars.get(i).close - bars.get(i).open)>bodyLength*Math.abs(bars.get(i).high - bars.get(i).low)){
				if(Math.abs(bars.get(i).close - bars.get(i).open) > 0.01f*Math.min(bars.get(i).close, bars.get(i).open)){
					bars.get(i).marubozu = true;
				}
			}
			//bars.get(i).marubozu = false;
		}
	}
	
	public void calculateVolumeOutliers(int period, float multiplier){
		
		
		for(int i = 0; i < bars.size()-period; i++){
			long[] volumes = new long[period];
			
			//shove all the volumes in
			for(int x = 0; x < period; x++){
				volumes[x] = bars.get(x+i).volume;
			}
			
			//sort them in order to find the threshold
			Arrays.sort(volumes);
			long IQR = volumes[(int) Math.ceil(volumes.length*3/4)]-volumes[(int) Math.floor(volumes.length*1/4-1)];
			assert(IQR > 0);
			long threshold = (long) (multiplier*IQR+volumes[(int) Math.floor(volumes.length*3/4-1)]);
			
			if(bars.get(i).volume > threshold){
				bars.get(i).volumeOutlier = true;
			}
			
			
		}
		
		/*105 day based
			//so now if a volume is above threshold, then it's an outlier
		 */
		 
		
	}
}
