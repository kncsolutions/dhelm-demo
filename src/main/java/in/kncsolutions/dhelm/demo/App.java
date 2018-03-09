/**
*Copyright 2018Knc Solutions Private Limited

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

import in.kncsolutions.dhelm.uicomponents.dcontainers.DPopUp;
import in.kncsolutions.dhelm.uicomponents.params.Params;
import in.kncsolutions.dhelm.demoaccess.LoginDemo;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
/**
 *This class is going to be your entry point towards gui enabled application development. 
 *
 */
public class App extends Application{
  private String URL;  
  public static void main( String[] args ){
    App a=new App();
    a.launch(args);
  }
 /**
 *
 */
  @Override
  public void start(Stage primaryStage) throws Exception {
	  if(!Params.getIsDemo() &&  !Params.getIsHavePwd())
	      topLevel(primaryStage);
	  else  if(Params.getIsDemo() && !Params.getIsHavePwd())
	      handler_demo(primaryStage);
	  else  if(!Params.getIsDemo() && Params.getIsHavePwd())
	      handler_have_password(primaryStage); 
   }
  /*
  *
  */
  private void topLevel(Stage primaryStage) throws Exception{   
    StartPage s=new StartPage(true,primaryStage);
	s.exit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {               
        primaryStage.close();
        try {
			App.this.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
            
     });
	s.demo.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Params.setIsDemo(true);
        Params.setIsHavePwd(false);
        primaryStage.close();            
        try{
          Thread.sleep(1000);
          start(primaryStage);
         }
        catch(Exception e){
           e.printStackTrace();
        }
      }
     });
	s.wthPass.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        DPopUp.Warn("Not implemented yet...");
       }    
    });
	primaryStage.setTitle("Dhelm");
	primaryStage.resizableProperty().setValue(Boolean.FALSE);
	primaryStage.setWidth(400);
    primaryStage.setHeight(300);
	primaryStage.setScene(s.getScene());
	primaryStage.show();
  }
  /**
   * 
   */
  private void handler_demo(Stage primaryStage) {
    Login l=new Login();
	l.Login();
	l.showLoginStage();
	l.getWebEngine().getLoadWorker().stateProperty()
	  .addListener((ChangeListener<State>) (ov, oldState, newState) -> {
	    if (newState == Worker.State.SUCCEEDED) {
		  URL=l.getWebEngine().getLocation();			  
		  System.out.println(URL);
		  if(URL.contains("passtoken=")){
		    LoginParams.setPassToken(URL.substring(URL.lastIndexOf("passtoken=")+new String("passtoken=").length(),URL.length()));
		    l.closeLoginStage();
			try{
			  DhelmDemoController demo_controller=new DhelmDemoController(LoginParams.getPassToken());
			  demo_controller.Execute();
			}
			catch(Exception e){}
		  }
	        }

	   });
	  }
	  
  
/**
 * 
 */
private void handler_have_password(Stage primaryStage) {
	//To be implemented if has real data subscription.
  }
}
