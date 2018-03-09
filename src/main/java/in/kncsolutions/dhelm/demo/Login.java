/**
*Copyright 2018 @Knc Solutions Private Limited

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
import in.kncsolutions.dhelm.uicomponents.HavePassword;
import in.kncsolutions.dhelm.uicomponents.dcontainers.DstackPane;
import in.kncsolutions.dhelm.uicomponents.dweb.DwebView;
import javafx.scene.web.WebEngine;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.*;
/**
*
*/
public class Login implements HavePassword{
  private StackPane bg;
  private Scene scene;
  private Stage loginStage;
  private Rectangle2D visualBounds;
  private DwebView wb;
  /**
 *
 */
 public void Login(){  
   loginStage=new Stage();
   loginStage.setResizable(true); 
   visualBounds = Screen.getPrimary().getVisualBounds();
   bg=(new DstackPane()).getStackPane();
   bg.setPadding(new Insets(2, 2, 2, 2));
   setPage();
   
 }
 /**
 *
 */
 public void setPage(){  
   wb=new DwebView("https://dhelm.kncsolutions.in/demo");
   bg.setAlignment(Pos.CENTER);  
   bg.getChildren().add(wb.getDwebView());   
   scene=new Scene(bg,visualBounds.getWidth()-40, visualBounds.getHeight()-40);   
   loginStage.setX((visualBounds.getWidth() - loginStage.getWidth()) / 2);
   loginStage.setY((visualBounds.getHeight() - loginStage.getHeight()) / 2);
   loginStage.setScene(scene);
   
 }
 /**
 *
 */
 public Scene getLoginPage(){
   return scene;
 }
 /**
 *
 */
 public Stage getLoginStage(){
   return loginStage;
 }
 /**
 *
 */
 public void showLoginStage(){
   loginStage.show();
 }
 /**
 *
 */
 public String getLoginState(){
   return wb.getRedirectedUrl();
 }
 /**
 *
 */
 public void closeLoginStage(){
   System.out.println("trying to close...");
   try {
     loginStage.close();
     Thread.sleep(5000);
   }
   catch(Exception e) {
	   e.printStackTrace();
   }
 }
 /**
 *
 */
 public WebEngine getWebEngine(){
   return wb.getWebEngine();
 }
}