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

import java.util.HashMap;
import java.util.Map;

import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.uicomponents.dcontainers.DCenterPanel;
import in.kncsolutions.dhelm.uitemplate.DhelmTerminalTemplate;
import in.kncsolutions.dhelm.uitemplate.ExchangeTemplate;
import in.kncsolutions.dhelm.uitemplate.TemplateModel;
import in.kncsolutions.dhelm.uitemplate.UIMemo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;

public class DhelmTerminal extends DhelmTerminalTemplate {
   private ExchangeTemplate exTmp;
   /**
   * @param t
   */
	public DhelmTerminal(TemplateModel t) {
		super(t);		
	}
    /**
     * 
     */
	@Override
	protected void mainToolbarButtonsActions(){
		try {
			super.getMainToolbarButtonByID(1).setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			    	prepareExchangeTemplate();
			    	setCenter(1);
			    }
			  });
		} catch (DataException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 */
	@Override
	public void runAlgorithmOnChange(String algorithm,DCenterPanel dcp,UIMemo uiMem) {
		if(algorithm.equals("CandleScan")) {
			StackPane test=new StackPane();			
			CandleScannerOnePass csop=new CandleScannerOnePass(exTmp,uiMem, super.getMainStage());
			csop.uiToControl(uiMem);
			csop.controlOutputUnit();
			test.getChildren().add(csop.getView());
			dcp.setParamPane(test);
		}
		
	}
	/**
	 * 
	 */
	private void prepareExchangeTemplate() {
		Map<String,String> e=new HashMap<String,String>();
		e.put("ABC Exchange", "ABC");
		e.put("DEF Exchange", "DEF");
		exTmp=new ExchangeTemplate();
		try {
			exTmp.setExchangeList(e);
		} catch (DataException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	protected void setMainStage() {
		super.setMainStage();
	}

	

	

}
