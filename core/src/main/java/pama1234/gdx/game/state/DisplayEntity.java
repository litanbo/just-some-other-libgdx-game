package pama1234.gdx.game.state;

import pama1234.gdx.game.util.function.ExecuteF;
import pama1234.gdx.util.listener.EntityListener;

public class DisplayEntity implements EntityListener{
  public ExecuteF f;
  public DisplayEntity(ExecuteF f) {
    this.f=f;
  }
  @Override
  public void display() {
    f.execute();
    // System.out.println("1234");
  }
  public interface DisplayWithCam{
    default public void displayCam() {}
  }
}
