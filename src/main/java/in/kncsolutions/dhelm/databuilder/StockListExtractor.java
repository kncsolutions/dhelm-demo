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
package in.kncsolutions.dhelm.databuilder;

import java.util.ArrayList;
import java.util.List;

import in.kncsolutions.dhelm.demoaccess.DemoStock;
import in.kncsolutions.dhelm.demoaccess.DhelmDataAccess;
import in.kncsolutions.dhelm.exceptions.DataException;

public class StockListExtractor {
private List<StockData> SLAbc;
private List<StockData> SLDef;
private DhelmDataAccess dda;
private List<DemoStock> ds = new ArrayList<DemoStock>();
/**
*@param ck DhelmDataAccess instance
*/
public StockListExtractor(DhelmDataAccess ck) {
	dda=ck;
	SLAbc=new ArrayList<StockData>();
	SLDef=new ArrayList<StockData>();
	setStockList("ABC");
	setStockList("DEF");
}
/**
 * @param exchange The exchange for which all listed stocks have to be found.
 * @return All the listed stocks.
 */
public List<StockData> getStockList(String exchange) {
	if(exchange.equals("ABC") || exchange.equals("abc"))return SLAbc;
	if(exchange.equals("DEF") || exchange.equals("def"))return SLDef;
	return null;
}
/**
* 
*/
private void setStockList(String exchange) {
		ds.clear();
		try {
			ds.addAll(dda.getAllListedStocks(exchange));
			for(int i=0;i<ds.size();i++) {
				if(exchange.equals("ABC")) {
					System.out.println("I am here.."+ds.get(i));
					SLAbc.add(new StockData(ds.get(i).stockSymbol,ds.get(i).stockName,ds.get(i).exchange));
				}
				else if(exchange.equals("DEF")) {
					SLDef.add(new StockData(ds.get(i).stockSymbol,ds.get(i).stockName,ds.get(i).exchange));
				}
			}
		} catch (DataException e) {
			e.printStackTrace();
		}	
}
}
