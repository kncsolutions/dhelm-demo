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

public class StockData {
String exchange;
String tradingSymbol;
String name;
/**
*@param ts : The trading symbol.
*@param n : The stock name.
*@param e : The exchange in which it is enlisted.
*/
public StockData(String ts,String n,String e) {
	tradingSymbol = ts;
	name = n;
	exchange = e;	
}
/**
*@return Returns the exchange for this particular scrip.
*/
public String getExchange(){
  return exchange;
}
/**
*@return Returns the trading symbol for this particular scrip.
*/
public String getTradingSymbol(){
  return tradingSymbol;
}
/**
*@return Returns the name for this particular scrip.
*/
public String getName(){
  return name;
}
}
