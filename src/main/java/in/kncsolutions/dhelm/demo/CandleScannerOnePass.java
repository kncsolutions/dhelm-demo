package in.kncsolutions.dhelm.demo;
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
import java.util.List;
import in.kncsolutions.dhelm.databuilder.CandlePatternResult;
import in.kncsolutions.dhelm.databuilder.StockData;
import in.kncsolutions.dhelm.demo.candlescanner.CandleScanner;
import in.kncsolutions.dhelm.demoaccess.DemoVars;
import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.uicomponents.dactions.SaveInFile;
import in.kncsolutions.dhelm.uicomponents.dcontainers.DPopUp;
import in.kncsolutions.dhelm.uitemplate.CandleScanTemplate;
import in.kncsolutions.dhelm.uitemplate.ExchangeTemplate;
import in.kncsolutions.dhelm.uitemplate.UIMemo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class CandleScannerOnePass extends CandleScanTemplate {
	private  Service<Void> service1;
	private TextArea progress=new TextArea();
	private TextArea finalOPBullish=new TextArea();
	private TextArea finalOPBearish=new TextArea();
	private Button saveBullish,saveBearish,cancelScan;
	private String s="Progress....\n";
	private StringProperty progressText=new SimpleStringProperty();
	private String resultBullish="RESULT BULLISH...\n";
	private StringProperty resultTextBullish=new SimpleStringProperty();
	private String resultBearish="RESULT BEARISH...\n";
	private StringProperty resultTextBearish=new SimpleStringProperty();
	private List<CandlePatternResult> resultListBullish=new ArrayList<CandlePatternResult>() ;
	private List<CandlePatternResult> resultListBearish=new ArrayList<CandlePatternResult>();
	private Stage stage;
	private boolean isScanComplete=false;
	private ComboBox algoSelector;
	private Tab inputTab=new Tab(),outputTab=new Tab();
	private List<StockData> toScanList=new ArrayList<StockData>();
	/**
	 * @param exchangeNames : exchange names in predefined template.
	 * @param u : User interface memo.
	 * @param s : The stage on which the UI is rendered.
	 */
	public CandleScannerOnePass(ExchangeTemplate exchangeNames,UIMemo u,Stage s) {
		super(exchangeNames,u);
		super.isUItoDisableAtRun(true);
		stage=s;		
	}
	/**
	 * @param u
	 */
	@Override
	public void uiToControl(UIMemo u) {
		inputTab=u.getInputTab();
		outputTab=u.getOuputTab();
		algoSelector=u.getAlgoSelector();
		progress=u.getProgressTextArea();
		finalOPBullish=u.getBullishResultTextArea();
		finalOPBearish=u.getBearishResultTextArea();
		cancelScan=u.getCancelScanButton();
		saveBullish=u.getSaveBullishButton();
		saveBearish=u.getSaveBearishButton();			
	}
	/**
	 * 
	 */
	@Override
	public void controlOutputUnit() {
		saveBullish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              if(getCandleScanningStatus()){
                DPopUp.Warn("Scan in progress..");
              }
              else if(resultListBullish.size()==0){
                DPopUp.Warn("Result list empty..");
              }
              else if(!getCandleScanningStatus() && resultListBullish.size()>0){
                saveInfo("Bullish");
              }
            }
         });
		saveBearish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              if(getCandleScanningStatus()){
                DPopUp.Warn("Scan in progress..");
              }
              else if(resultListBearish.size()==0){
                DPopUp.Warn("Result list empty..");
              }
              else if(!getCandleScanningStatus() && resultListBearish.size()>0){
                saveInfo("Bearish");
              }
            }
          });
		cancelScan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              if(!getCandleScanningStatus()){
                DPopUp.Warn("No scan in progress..");
              }
              else if(getCandleScanningStatus()){
                release();
                service1.cancel();
              }
            }
          });

	}
    /**
     * 
     */
	@Override
	public void setScanList(String exchangeID) {
		if(exchangeID.equals("ABC")||exchangeID.equals("DEF") ) {
			toScanList.addAll(getStockSymbolList(exchangeID));
			System.out.println("Scan List size: "+toScanList.size());
		}
	
	}
	/**
	 * 
	 */
	@Override
	public void disableUIcomponents() {
		algoSelector.setDisable(true);
		inputTab.setClosable(false);
		inputTab.setDisable(true);
	    inputTab.getContent().setDisable(true);
	    outputTab.setClosable(false);		
	}
	/**
	 * 
	 */
	private List<StockData> getStockSymbolList(String exchangeID){
		List<StockData> allSymbols=new ArrayList<StockData>();
		if(exchangeID.equals("ABC")) {
			for(int i=0;i<DhelmDemoController.ABCAll.size();i++) {
				allSymbols.add(DhelmDemoController.ABCAll.get(i));
			}
		}
		else if(exchangeID.equals("DEF")) {
			for(int i=0;i<DhelmDemoController.DEFAll.size();i++) {
				allSymbols.add(DhelmDemoController.DEFAll.get(i));
			}	
		}
		return allSymbols;
	}
	/**
	 * 
	 */
	@Override
	public void doScan() {
		  resultListBullish.clear();
		  resultListBearish.clear();
		  service1 = new Service<Void>() {
		    CandleScanner c=new CandleScanner(SelectedExchange);
		             @Override
		            protected Task<Void> createTask() {
		               return new Task<Void>() {
		                    @Override
		                   protected Void call() throws Exception {
		                       for(int i=0;i<toScanList.size();i++){
		                    	 System.out.println(".....................");
		                    	 System.out.println("Scanning ::"+toScanList.get(i).getName());
		                         c.setData(toScanList.get(i));
		                         if (isCancelled()) {
		                             updateProgressText();
		                             updateResultText();
		                            break;
		                            }
		                         for(int j=1;j<=3;j++){
		                           if (isCancelled()) {
		                            break;
		                           } 
		                           if(!DemoVars.getLoginStatus()){
		                             release();
		                             cancel();
		                           }
		                          c.doScanSingle(j);                        
		                          updateProgressText(i,j);
		                          updateResultText(c.getScanResult(),i); 
		                          try{
		                           Thread.sleep(1000);
		                          }catch(Exception e){}
		                          }                     
		                       }   
		                        release();                   
		                        return null; 
		                   }

		               };                          
		            }            
		        };                   
		        service1.start();
		        progress.textProperty().bind(progressText);
		        finalOPBullish.textProperty().bind(resultTextBullish);
		        finalOPBearish.textProperty().bind(resultTextBearish);
		
	}
	/**
	*
	*/
	private void release(){
	  super.IsCandleScanning=false;
	  algoSelector.setDisable(false); 
	  inputTab.setClosable(true);
	  inputTab.getContent().setDisable(false);
	  outputTab.setClosable(false);

	}
	/**
	*
	*/
	private void updateProgressText(int i,int j){
	 if(i==0 && j==1){
	   progress.clear();
	   finalOPBullish.clear();
	   finalOPBearish.clear();
	   reInitText();
	   resultTextBullish.set(resultBullish);
	   resultTextBearish.set(resultBearish);
	   s=s+"Total securities to scan :: "+Integer.toString(toScanList.size())+"\n";
	 }
	 isScanComplete=false;
	 s=s+"Scanning "+Integer.toString(i)+"."+Integer.toString(j)+" :: "+toScanList.get(i).getName()+"\n";
	 if(i==toScanList.size()-1 && j==3){
	   s=s+"Done...\n";
	   isScanComplete=true;
	   }
	 progressText.set(s);
	 progress.end();
	}
	/**
	*
	*/
	private void updateResultText(String []s,int i){
	  if(!s[0].equals("na") && !s[1].equals("na")){
	    if(s[1].equals("Bullish")){
	      resultListBullish.add(new CandlePatternResult(toScanList.get(i).getName(),toScanList.get(i).getTradingSymbol(),s[0],s[1],s[2],s[3]));
	      resultBullish=resultBullish+toScanList.get(i).getName()+"----"+toScanList.get(i).getTradingSymbol()+"----"+s[0]+"----"+s[1]+"\n";
	      if(isScanComplete)resultBullish=resultBullish+"Done...\n";
	      resultTextBullish.set(resultBullish);
	      finalOPBullish.end();
	    }
	   else if(s[1].equals("Bearish")){
	      resultListBearish.add(new CandlePatternResult(toScanList.get(i).getName(),toScanList.get(i).getTradingSymbol(),s[0],s[1],s[2],s[3]));
	      resultBearish=resultBearish+toScanList.get(i).getName()+"----"+toScanList.get(i).getTradingSymbol()+"----"+s[0]+"----"+s[1]+"\n";
	      if(isScanComplete)resultBearish=resultBearish+"Done...\n";
	      resultTextBearish.set(resultBearish);
	      finalOPBearish.end();
	    }
	   }
	   else if(isScanComplete){
	      resultBullish=resultBullish+"Done...\n";
	      resultTextBullish.set(resultBullish);
	      finalOPBullish.end();
	      resultBearish=resultBearish+"Done...\n";
	      resultTextBearish.set(resultBearish);
	      finalOPBearish.end();
	    }

	}
	/**
	*
	*/
	private void updateProgressText(){
	 
	 s=s+"Cancelled"+"\n";
	 progressText.set(s);
	 progress.end();
	}
	/**
	*
	*/
	private void updateResultText(){
	      resultBullish=resultBullish+"Cancelled"+"\n";
	      resultTextBullish.set(resultBullish);
	      finalOPBullish.end();
	  
	      resultBearish=resultBearish+"Cancelled\n";
	      resultTextBearish.set(resultBearish);
	      finalOPBearish.end();
	}
	/**
	*
	*/
	private void reInitText(){
	 s="Progress....\n";
	 resultBullish="";
	 resultBearish="";
	}
	/**
	*
	*/
	private void saveInfo(String type){
	 String text="---------Result Set-----------\n";
	  if(type.equals("Bullish")){
	    for(int i=0;i<resultListBullish.size();i++){
	      String[] data=resultListBullish.get(i).getCandlePatternResult();
	      text=text+Integer.toString(i+1)+"::\n"+
	        "Stock Name : "+data[0] +"\n"+
	        "Stock Symbol : "+data[1] +"\n"+
	        "Pattern : "+data[2] +"\n"+ 
	        "Pattern Type : "+data[3] +"\n"+
	        "Time Stamp : "+data[4] +"\n"+
	        "Chart Type : "+data[5] +"\n";
	      }
	  }
	 else if(type.equals("Bearish")){
	    for(int i=0;i<resultListBearish.size();i++){
	      String[] data=resultListBearish.get(i).getCandlePatternResult();
	      text=text+Integer.toString(i+1)+"::\n"+
	        "Stock Name : "+data[0] +"\n"+
	        "Stock Symbol : "+data[1] +"\n"+
	        "Pattern : "+data[2] +"\n"+ 
	        "Pattern Type : "+data[3] +"\n"+
	        "Time Stamp : "+data[4] +"\n"+
	        "Chart Type : "+data[5] +"\n";
	      }
	  }
	  new SaveInFile(stage,"TXT files (*.txt)", "*.txt",text);
	}

}
