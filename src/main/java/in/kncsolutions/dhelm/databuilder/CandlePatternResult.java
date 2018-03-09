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
package in.kncsolutions.dhelm.databuilder;

/**
*
*/
public class CandlePatternResult{
private String stockName;
private String stockSymbol;
private String candlePattern;
private String patternType;
private String timeStamp;
private String chartType;
/**
*
*/
public CandlePatternResult(String name,String sym,String pattern,String cType,String time,String chType){
  stockName=name;
  stockSymbol=sym;
  candlePattern=pattern;
  patternType=cType;
  timeStamp=time;
  chartType=chType;
}
/**
*
*/
public String[] getCandlePatternResult(){
  return new String[]{stockName,stockSymbol,candlePattern,patternType,timeStamp,chartType};
}
}
