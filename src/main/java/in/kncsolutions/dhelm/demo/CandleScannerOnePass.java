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
import java.util.Map;

import in.kncsolutions.dhelm.databuilder.CandlePatternResult;
import in.kncsolutions.dhelm.uitemplate.CandleScanTemplate;
import in.kncsolutions.dhelm.uitemplate.ExchangeTemplate;
import in.kncsolutions.dhelm.uitemplate.UIMemo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
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
	private List<CandlePatternResult> resultListBullish=new <CandlePatternResult>ArrayList() ;
	private List<CandlePatternResult> resultListBearish=new <CandlePatternResult>ArrayList();
	private Stage stage;
	private boolean isScanComplete=false;
	private ComboBox algoSelector;
	private Tab inputTab,outputTab;
	public CandleScannerOnePass(ExchangeTemplate exchangeNames,UIMemo u) {
		super(exchangeNames,u);
		// TODO Auto-generated constructor stub
	}
	
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
	@Override
	public void controlOutputUnit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> setScanList(String exchangeID) {
		if(exchangeID.equals("ABC")||exchangeID.equals("DEF") ) {
			return getStockSymbolList(exchangeID);
		}
		return null;
	}
	@Override
	public void disableUIcomponents() {
		inputTab.setClosable(false);
	    inputTab.getContent().setDisable(true);
	    outputTab.setClosable(false);		
	}
	/**
	 * 
	 */
	private List<String> getStockSymbolList(String exchangeID){
		List<String> allSymbols=new ArrayList<String>();
		if(exchangeID.equals("ABC")) {
			for(int i=0;i<DhelmDemoController.ABCAll.size();i++) {
				allSymbols.add(DhelmDemoController.ABCAll.get(i).getTradingSymbol());
			}
		}
		else if(exchangeID.equals("DEF")) {
			for(int i=0;i<DhelmDemoController.DEFAll.size();i++) {
				allSymbols.add(DhelmDemoController.DEFAll.get(i).getTradingSymbol());
			}	
		}
		return allSymbols;
	}
	@Override
	public void doScan() {
		// TODO Auto-generated method stub
		
	}

	

	

}
