package in.kncsolutions.dhelm.demo;

import java.util.ArrayList;
import java.util.List;

import in.kncsolutions.dhelm.uitemplate.CandleScanTemplate;
import in.kncsolutions.dhelm.uitemplate.ExchangeTemplate;

public class CandleScannerOnePass extends CandleScanTemplate {

	public CandleScannerOnePass(ExchangeTemplate exchangeNames) {
		super(exchangeNames);
		// TODO Auto-generated constructor stub
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
