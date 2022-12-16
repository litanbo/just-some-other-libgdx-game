package pama1234.gdx.game.state.state0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.ButtonGenerator;
import pama1234.gdx.game.ui.util.Button;

public class Settings extends StateEntity0001{
  public Button[] buttons;
  public Settings(Screen0011 p) {
    super(p);
    buttons=ButtonGenerator.genButtons_0004(p);
  }
  @Override
  public void init() {
    p.backgroundColor(0);
    for(Button e:buttons) p.centerScreen.add.add(e);
  }
  @Override
  public void dispose() {
    for(Button e:buttons) p.centerScreen.remove.add(e);
  }
  @Override
  public void displayCam() {
    // p.text("FirstRun",0,0);
  }
}