package in.kncsolutions.dhelm.demo.candlescanner;
/**
*Copyright 2018 KNC Solutions Private Limited

*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at

* http://www.apache.org/licenses/LICENSE-2.0

*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.kncsolutions.dhelm.candlescanner.CANDLETYPE;
import in.kncsolutions.dhelm.candlescanner.CandlePatternDoubleLine;
import in.kncsolutions.dhelm.candlescanner.CandlePatternMultiLine;
import in.kncsolutions.dhelm.candlescanner.CandlePatternSingleLine;
import in.kncsolutions.dhelm.candlescanner.CandleSignalType;
import in.kncsolutions.dhelm.candlescanner.ScanParams;
import in.kncsolutions.dhelm.databuilder.StockData;
import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.mathcal.CandleFacts;
import in.kncsolutions.dhelm.mathcal.DateCalculator;
import in.kncsolutions.dhelm.mathcal.WeeklyData;


public class CandleScanner extends CANDLETYPE{
	private String Exchange;
	private List<StockData>toScanList=new ArrayList<StockData>();
	private StockData stData;
	private String dg;
	private int brf;
	private int lrf;
	private double lsrf;
	private int ptrf;
	private double losrf;
	private double usrf;
	private List<Double> open;
	private List<Double> high;
	private List<Double> low;
	private List<Double> close;
	private List<Long> volume;
	private List<String> timeStamp;
	private CandlePatternSingleLine cpsl;
	private CandlePatternDoubleLine cpdl;
	private CandlePatternMultiLine cpml;
	private CandleSignalType cst;
	private Map<String,Object> scanParams=new HashMap<String, Object>();
	private  String result="na";
	private  String resultType="na";
	/**
	*@param exchange : The selected exchange at GUI
	*/
	public CandleScanner(String exchange){
	   super();
	   Exchange=exchange;
	   setScanParams();
	   open=new ArrayList<Double>();
	   high=new ArrayList<Double>();
	   low=new ArrayList<Double>();
	   close=new ArrayList<Double>();
	   volume=new ArrayList<Long>();
	   timeStamp=new ArrayList<String>();
	}
	/**
	*
	*/
	private void setScanParams(){
	  dg=ScanParams.DataGap;
	  brf=ScanParams.BodyRefNumber;
	  lrf=ScanParams.LengthRefNumber;
	  lsrf=ScanParams.LongShortRefPercentage;
	  ptrf=ScanParams.PrevTrendRefNumber;
	  losrf=ScanParams.LowerShadowRefPercentage;
	  usrf=ScanParams.UpperShadowRefPercentage;
	  scanParams.put("body_reference_number",brf);
	  scanParams.put("length_reference_number",lrf);
	  scanParams.put("long_short_reference_percentage",lsrf);
	  scanParams.put("previous_trend_reference_number",ptrf);
	  scanParams.put("lower_shadow_reference_percentage",losrf);
	  scanParams.put("upper_shadow_reference_percentage",usrf);
	}
	/**
	*
	*/
	public void setData(StockData s){
	 DataInfo d=null;
	 if(!dg.equals("week"))
	   d=new DataInfo(s);
	 else if(dg.equals("week"))
	   d=new DataInfo(s);
	 open.clear();
	 high.clear();
	 low.clear();
	 close.clear();
	 volume.clear();
	 timeStamp.clear();
	 if(!dg.equals("week")){
	   open.addAll(CandleFacts.reverseDouble(d.getOpen()));
	   high.addAll(CandleFacts.reverseDouble(d.getHigh()));
	   low.addAll(CandleFacts.reverseDouble(d.getLow()));
	   close.addAll(CandleFacts.reverseDouble(d.getClose()));
	   volume.addAll(CandleFacts.reverseLong(d.getVolume()));
	   timeStamp.addAll(CandleFacts.reverseString(d.getTimeStamp()));
	 }
	 else if(dg.equals("week")){
	  WeeklyData wd=new WeeklyData(CandleFacts.reverseDouble(d.getOpen()),
	                               CandleFacts.reverseDouble(d.getHigh()),
	                               CandleFacts.reverseDouble(d.getHigh()),
	                               CandleFacts.reverseDouble(d.getHigh()),
	                               CandleFacts.reverseLong(d.getVolume()),
	                               CandleFacts.reverseString((d.getTimeStamp())));
	 open.addAll(wd.getOpen());
	 high.addAll(wd.getHigh());
	 low.addAll(wd.getLow());
	 close.addAll(wd.getClose());
	 volume.addAll(wd.getVolume());
	 timeStamp.addAll(wd.getDate());
	 if(open.size()>0)
	 for(int i=0;i<open.size();i++){
	   System.out.println(timeStamp.get(i)+":"+open.get(i)+","+high.get(i)+","+low.get(i)+","+close.get(i)+","+volume.get(i));
	 }
	 }
	}
	/**
	*
	*/
	public void doScanSingle(int c){
	 result="na";
	 resultType="na";
	 if(volume.size()>0){
	 if(c==1  && volume.get(volume.size()-1)>1000)
	   calculateSingleLinePattern();
	 if(c==2 && volume.get(volume.size()-1)>1000)
	   calculateDoubleLinePattern();
	 if(c==3  && volume.get(volume.size()-1)>1000)
	 calculateMultiLinePattern();
	 }
	 }
	/**
	*
	*/
	private void calculateSingleLinePattern(){
	  result="na";
	  resultType="na";
	  try{
	 cpsl=super.getSingleLinePattern(open,high,low,close,scanParams);
	 cst=super.getCandleSignalType();
	 if(cpsl!=CandlePatternSingleLine.NotApplicable){
	   switch(cpsl){
	     case BullishBeltHold:
	      result="BullishBeltHold";
	      resultType="Bullish";
	      break;
	     case BearishBeltHold:
	      result="BearishBeltHold";
	      resultType="Bearish";
	      break;
	     case BullishStrongLine:
	      result="BullishStrongLine";
	      resultType="Bullish";
	      break;
	     case BearishStrongLine:
	      result="BearishStrongLine";
	      resultType="Bearish";
	      break; 
	     case GappingUpDoji:
	      result="GappingUoDoji";
	      resultType="Bullish";
	      break;
	     case GappingDownDoji:
	      result="GappingDownDoji";
	      resultType="Bearish";
	      break;
	     case Hammer:
	      result="Hammer";
	      resultType="Bullish";
	      break;
	     case HangingMan:
	      result="HangingMan";
	      resultType="Bearish";
	      break; 
	     case NorthernDoji:
	      result="NorthernDoji";
	      resultType="Bearish";
	      break;
	     case SouthernDoji:
	      result="SouthernDoji";
	      resultType="Bullish";
	      break;
	     case OneCandleShootingStar:
	      result="OneCandleShootingStar";
	      resultType="Bearish";
	      break;
	     case TakuriLine:
	      result="TakuriLine";
	      resultType="Bullish";
	      break; 
	   }
	 }
	 }catch(DataException e){
	   e.printStackTrace();
	 }

	}
	/**
	*
	*/
	private void calculateDoubleLinePattern(){
	  result="na";
	  resultType="na";
	  try{
	 cpdl=super.getDoubleLinePattern(open,high,low,close,scanParams);
	 cst=super.getCandleSignalType();
	 if(cpdl!=CandlePatternDoubleLine.NotApplicable){
	   switch(cpdl){
	     case BullishHarami:
	      result="BullishHarami";
	      resultType="Bullish";
	      break;
	     case BearishHarami:
	      result="BearishHarami";
	      resultType="Bearish";
	      break;
	     case BullishEngulfing:
	      result="BullishEngulfing";
	      resultType="Bullish";
	      break;
	     case BearishEngulfing:
	      result="BearishEngulfing";
	      resultType="Bearish";
	      break; 
	     case BullishKicking:
	      result="BullishKicking";
	      if(cst==CandleSignalType.BULLISH)
	       resultType="Bullish";
	      else if(cst==CandleSignalType.BEARISH)
	        resultType="Bearish";
	      break;
	     case BearishKicking:
	      result="BearishKicking";
	      if(cst==CandleSignalType.BULLISH)
	       resultType="Bullish";
	      else if(cst==CandleSignalType.BEARISH)
	        resultType="Bearish";
	      break;
	     case MatchingLow:
	      result="MatchingLow";
	      resultType="Bullish";
	      break;
	     case MatchingHigh:
	      result="MatchingHigh";
	      resultType="Bearish";
	      break;
	     case BearishHaramiCross:
	      result="BearishHaramiCross";
	      resultType="Bearish";
	      break; 
	     case BullishHaramiCross:
	      result="BullishHaramiCross";
	      resultType="Bullish";
	      break;
	     case BullishDojiStar:
	      result="BullishDojiStar";
	      resultType="Bullish";
	      break;
	     case BearishDojiStar:
	      result="BearishDojiStar";
	      resultType="Bearish";
	      break;
	     case DarkCloudCover:
	      result="DarkCloudCover";
	      resultType="Bearish";
	      break; 
	     case PiercingLine:
	      result="PiercingLine";
	      resultType="Bullish";
	      break;
	     case DescendingHawk:
	      result="DescendingHawk";
	      resultType="Bearish";
	      break;
	     case HomingPigeon:
	      result="HomingPigeon";
	      resultType="Bearish";
	      break; 
	   }
	 }
	 }catch(DataException e){
	   e.printStackTrace();
	 }

	}
	/**
	*
	*/
	private void calculateMultiLinePattern(){
	  result="na";
	  resultType="na";
	  try{
	 cpml=super.getMultiLinePattern(open,high,low,close,scanParams);
	 cst=super.getCandleSignalType();
	 if(cpml!=CandlePatternMultiLine.NotApplicable){
	   switch(cpml){
	     case BullishAbandonedBaby:
	      result="BullishAbandonedBaby";
	      resultType="Bullish";
	      break;
	     case BearishAbandonedBaby:
	      result="BearishAbandonedBaby";
	      resultType="Bearish";
	      break;
	     case BullishSidebySideWhiteLines:
	      result="BullishSidebySideWhiteLines";
	      resultType="Bullish";
	      break;
	     case BearishSidebySideWhiteLines:
	      result="BearishSidebySideWhiteLines";
	      resultType="Bearish";
	      break; 
	     case MorningDojiStar:
	      result="MorningDojiStar";
	      resultType="Bullish";
	      break;
	     case EveningDojiStar:
	      result="EveningDojiStar";
	      resultType="Bearish";
	      break;
	     case MorningStar:
	      result="MorningStar";
	      resultType="Bullish";
	      break;
	     case EveningStar:
	      result="EveningStar";
	      resultType="Bearish";
	      break;
	     case UpsideTasukiGap:
	      result="UpsideTasukiGap";
	      resultType="Bullish";
	      break;
	     case DownsideTasukiGap:
	      result="DownsideTasukiGap";
	      resultType="Bearish";
	      break;
	     case ThreeWhiteSoldiers:
	      result="ThreeWhiteSoldiers";
	      resultType="Bullish";
	      break;
	     case ThreeBlackCrows:
	      result="ThreeBlackCrows";
	      resultType="Bearish";
	      break;
	     case ThreeInsideUp:
	      result="ThreeInsideUp";
	      resultType="Bullish";
	      break;
	     case ThreeInsideDown:
	      result="ThreeInsideDown";
	      resultType="Bearish";
	      break;
	     case ThreeOutsideUp:
	      result="ThreeOutsideUp";
	      resultType="Bullish";
	      break;
	     case ThreeOutsideDown:
	      result="ThreeOutsideDown";
	      resultType="Bearish";
	      break;
	     case RisingThreeMethod:
	      result="RisingThreeMethod";
	      resultType="Bullish";
	      break; 
	   }
	 }
	 }catch(DataException e){
	   e.printStackTrace();
	 }

	}
	/**
	*@return Returns the result for candlescanner
	*/
	public  String[] getScanResult(){
	  if(timeStamp.size()>0)
	    return new String[]{result,resultType,timeStamp.get(0),dg};
	  return new String[]{result,resultType,"na",dg};
	}
}
