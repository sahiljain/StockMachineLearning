
public class Bar {
	
	public String symbol;
	public String date;
	public float open, high, low, close;
	public long volume;
	public boolean positive, marubozu = false, volumeOutlier = false;
	
	public Bar(String barInfo){
		
		String[] tokens = barInfo.split(",");
		symbol = tokens[0];
		date = tokens[1];
		open = Float.valueOf(tokens[2]);
		high = Float.valueOf(tokens[3]);
		low = Float.valueOf(tokens[4]);
		close = Float.valueOf(tokens[5]);
		volume = Long.valueOf(tokens[6]);
		positive = (close > open);
		
		//marubozu = (Math.abs(close - open)>0.95f*Math.abs(high - low));
		
	}
	
	public Bar(Bar other){
		symbol = other.symbol;
		date = other.date;
		open = other.open;
		high = other.high;
		low = other.low;
		close = other.close;
		volume = other.volume;
		positive = other.positive;
		marubozu = other.marubozu;
	}
	
	public String toString(){
		return (date + " " + symbol + ":  open:" + open + ",  high:" + high + ",  low:" + low + ",  close:" + close );
	}
}
