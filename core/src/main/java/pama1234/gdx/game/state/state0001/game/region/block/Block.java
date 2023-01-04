package pama1234.gdx.game.state.state0001.game.region.block;

import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock.ItemDropAttr;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class Block{
  public MetaBlock type;
  public boolean changed;
  public boolean updateLighting=true;
  public int[] displayType;
  public int lighting=16;
  public Block(MetaBlock type) {
    this.type=type;
    init(type);
  }
  public void init(MetaBlock type) {
    if(type.displayTypeSize>0) {
      displayType=new int[type.displayTypeSize];
      displayType[0]=type.getDisplayType();
    }else displayType=null;
  }
  public void type(MetaBlock in) {
    MetaBlock t=type;
    if(in==t) return;
    changed=true;
    // updateLighting=true;
    type=in;
    init(in);
    t.to(this,in);
    in.from(this,t);
  }
  public static boolean isEmpty(Block in) {
    return in==null||in.type.empty;
  }
  public static boolean isType(Block in,MetaBlock type) {
    return in!=null&&in.type==type;
  }
  public void doItemDrop(int x,int y) {
    // System.out.println(x+" "+y);
    World0001 world=type.pc.pw;
    for(ItemDropAttr e:type.itemDrop) {
      world.entities.items.add.add(new DroppedItem(world.pg,x*world.blockWidth,y*world.blockHeight,e.item.createItem()));
    }
  }
}