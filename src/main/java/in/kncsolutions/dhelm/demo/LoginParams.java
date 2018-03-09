/**
*Copyright 2018@Knc Solutions Private Limited

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
/**
*
*/
public class LoginParams{
  public static String passToken;
  public static String authToken;
  
  /**
  *@param s : The pass token.
  */
  public static void setPassToken(String s){
    passToken=s;
	System.out.println("Pass Token : "+passToken);
  }
  /**
  *@return The passtoken token.
  */
  public static String getPassToken(){
    return passToken;
  }
  /**
   *@param s : The auth token.
   */
   public static void setAuthToken(String s){
     authToken=s;
 	System.out.println("Pass Token : "+passToken);
   }
   /**
   *@return The authToken token.
   */
   public static String getAuthToken(){
     return authToken;
   }
  
}