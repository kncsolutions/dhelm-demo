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
package in.kncsolutions.dhelm.demo.candlescanner;
import java.util.ArrayList;
import java.util.List;
import in.kncsolutions.dhelm.databuilder.StockData;
import in.kncsolutions.dhelm.demo.DhelmDemoController;
import in.kncsolutions.dhelm.demoaccess.DhelmDemoOHLC;
import in.kncsolutions.dhelm.exceptions.DataException;


/**
*
*/
public class DataInfo{
private StockData sd;
private String exchange;
private List<Double> open;
private List<Double> high;
private List<Double> low;
private List<Double> close;
private List<Long> volume;
private List<String> timeStamp;
/**
*@param s : Stock symbol
*@param e: exchange
*/
public DataInfo(StockData s ){
  sd=s;   
  open=new ArrayList<Double>();
  high=new ArrayList<Double>();
  low=new ArrayList<Double>();
  close=new ArrayList<Double>();
  volume=new ArrayList<Long>();
  timeStamp=new ArrayList<String>();
  getHistoricalData();
}
/**
*
*/
private void getHistoricalData(){
  List<DhelmDemoOHLC> demoOHLC=new ArrayList<DhelmDemoOHLC>();
  if(DhelmDemoController.dataAccessController!=null){
  try{    
	demoOHLC.addAll(DhelmDemoController.dataAccessController.getStockOHLCVT(sd.getTradingSymbol(), sd.getExchange()));
    }catch(DataException e){}
  }
  else{
   System.out.println("No Connection...");
  }
    
      open.clear();
	  high.clear();
      low.clear();
      close.clear();
      volume.clear();
      timeStamp.clear();
      if(demoOHLC.size()>0){
        for(int j=0;j<demoOHLC.size();j++){	  
	      open.add(demoOHLC.get(j).open);
	      high.add(demoOHLC.get(j).high);
          low.add(demoOHLC.get(j).low);
          close.add(demoOHLC.get(j).close);
          volume.add(demoOHLC.get(j).volume);
          timeStamp.add(demoOHLC.get(j).date);
      }
    }
    System.out.println("Data for : "+sd.getName()+"----"+"Symbol :: "+sd.getTradingSymbol());
    System.out.println("Date--Open--High--Low--Close--Volume");
    for(int i=0;i<open.size();i++){
      System.out.println(timeStamp.get(i)+","+open.get(i)+","+
                           high.get(i)+","+low.get(i)+","+
                           close.get(i)+","+volume.get(i)+"\n");
      
    }
}
/**
*
*/
public void setOpen(List<Double> l){
  open.addAll(l);
}
/**
*
*/
public void setHigh(List<Double> l){
  high.addAll(l);
}

/**
*
*/
public void setLow(List<Double> l){
  low.addAll(l);
}

/**
*
*/
public void setClose(List<Double> l){
  close.addAll(l);
}

/**
*
*/
public void setVolume(List<Long> l){
  volume.addAll(l);
}
/**
*
*/
public void setTimeStamp(List<String> l){
  timeStamp.addAll(l);
}
/**
*
*/
public List<Double> getOpen(){
  return open;
}
/**
*
*/
public List<Double> getHigh(){
  return high;
}

/**
*
*/
public List<Double> getLow(){
  return low;
}

/**
*
*/
public List<Double> getClose(){
  return close;
}

/**
*
*/
public List<Long> getVolume(){
  return volume;
}
/**
*
*/
public List<String> getTimeStamp(){
  return timeStamp;
}


}