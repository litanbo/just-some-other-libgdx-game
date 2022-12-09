package pama1234.gdx.game.ui;

import static pama1234.math.Tools.inBox;

import pama1234.gdx.game.util.ExecuteF;
import pama1234.gdx.game.util.GetBoolean;
import pama1234.gdx.game.util.GetFloat;
import pama1234.gdx.game.util.GetInt;
import pama1234.gdx.util.app.UtilScreen;

public class TextButton extends Button{
  String text;
  boolean textOffset=true;//TODO
  // int x,y,w,h;
  GetInt bu;
  GetFloat x,y,w,h;
  public TextButton(UtilScreen p,boolean textOffset,GetBoolean active,ExecuteF press,ExecuteF clickStart,ExecuteF clickEnd,String text,GetInt bu,GetFloat x,GetFloat y) {
    this(p,textOffset,active,press,clickStart,clickEnd,text,bu,x,y,null,bu::get);
    this.w=()->p.textLength(this.text)+(this.textOffset?p.pu:0);//TODO
  }
  public TextButton(UtilScreen p,boolean textOffset,GetBoolean active,ExecuteF press,ExecuteF clickStart,ExecuteF clickEnd,String text,GetInt bu,GetFloat x,GetFloat y,GetFloat w,GetFloat h) {
    super(p,active,press,clickStart,clickEnd);
    this.text=text;
    this.textOffset=textOffset;
    this.bu=bu;
    this.x=x;
    this.y=y;
    this.w=w;
    this.h=h;
  }
  @Override
  public void display() {
    if(!active.get()) return;
    final float tx=x.get(),ty=y.get(),tw=w.get(),th=h.get();
    if(touch!=null) {
      // p.fill(127,240);
      p.fill(94,203,234,200);
      p.textColor(255,220);
    }else {
      p.fill(127,191);
      // p.fill(94,203,234,191);
      p.textColor(255,200);
    }
    p.beginBlend();//TODO
    p.rect(tx+p.pus,ty+p.pus,tw+(textOffset?-p.pus*3:p.pus),th-p.pus*2);
    // float tl=tw-(textOffset?p.pu*3/2:p.pu);//TODO
    // p.text(text,tx+(tw-tl)/2f,ty+(th-p.pu)/2f);
    p.text(text,tx+(textOffset?p.pu/2:p.pus*2),ty+(th-p.pu)/2f);
    // p.text(text,tx+(textOffset?(tw-p.pus*8)/2f:0),ty+(th-p.pu)/2f);
    p.endBlend();
  }
  public boolean inButton(float xIn,float yIn) {
    return inBox(xIn,yIn,x.get(),y.get(),w.get(),h.get());
  }
}