/**
*Copyright 2018 Knc Solutions Private Limited

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
package in.kncsolutions.dhelm.demo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.kncsolutions.dhelm.databuilder.StockData;
import in.kncsolutions.dhelm.databuilder.StockListExtractor;
import in.kncsolutions.dhelm.demoaccess.DhelmDataAccess;
import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.uitemplate.ExchangeTemplate;
import in.kncsolutions.dhelm.uitemplate.TemplateModel;
/**
*
*/
public class DhelmDemoController{
public static  List<StockData> ABCAll=new ArrayList<StockData>();
public static  List<StockData>DEFAll=new ArrayList<StockData>();
public static int ScanDomain=1;
public static final String [] AlgoList={"Select","CandleStick Pattern Scan-once","CandleStick Pattern Scan-continuous"};
private String passToken;
private DhelmDataAccess ck;
private DhelmTerminal t;
private TemplateModel tm;
/**
*@param The extracted pass-token
*/
public DhelmDemoController(String p){
	passToken=p;
	tm=new TemplateModel();
	prepareTemplateModel();
    t=new DhelmTerminal(tm);  
}
/**
*
*/
public void Execute(){
  connectKnct();
  startGUI();
  run();
}
/**
*
*/
private void connectKnct(){
  try {
	ck=new DhelmDataAccess(passToken);
  }
  catch(DataException e) {
	  e.printStackTrace();  	  
  }  			 
}
/**
*
*/
private void startGUI(){
   StockListExtractor sd=new StockListExtractor(ck);
   System.out.println("Can reach here...");
   ABCAll.addAll(sd.getStockList("ABC"));
   DEFAll.addAll(sd.getStockList("DEF"));
   System.out.println("Can reach here...");
   if(ABCAll.size()>0 && DEFAll.size()>0) {
     System.out.println("Here.."+ABCAll.get(0).getExchange()+","+ABCAll.get(0).getName()+","+ABCAll.get(0).getTradingSymbol());
      System.out.println(DEFAll.get(0).getExchange()+","+DEFAll.get(0).getName()+","+DEFAll.get(0).getTradingSymbol()); 
   }
   System.out.println("Can reach here...");
   t.setMainStage();  
}
/**
*
*/
//public Button getLogoutButton(){
  //return t.getLogoutButton();
//}
/**
*
*/
//protected DhelmTerminal getTerminal(){
  //return t;
//}
/**
*
*/
protected  void exitSession(){
  //ck.signOut();
}
/**
*
*/
private void run(){
}
/**
*
*/
private void prepareTemplateModel() {
	Map<Integer,String> b=new HashMap<Integer,String>();
	b.put(1,"Scan entire Exchange");
	tm.setContentMainToolbarButtons(b);
	List<String> algoList=new ArrayList<String>();
	algoList.add("CandleScan");
	tm.setContentAlgoList(algoList);
}
}
