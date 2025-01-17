package pama1234.gdx.game.state.state0001.game.entity.entity0001;

import java.util.Iterator;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.center.MultiGameEntityCenter0001;
import pama1234.gdx.game.state.state0001.game.entity.center.MultiGameEntityCenter0001.GameEntityCenter;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem.ItemCountType;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.UtilMath;
import pama1234.math.physics.MassPoint;

public class DroppedItem extends LivingEntity{
  public DroppedItemCenter pc;
  public MovementLimitBox limitBox;
  @Tag(4)
  public Item data;
  //---
  public int timeCount;
  public LivingEntity owner;
  @Deprecated
  public DroppedItem() {//kryo only
    super();
  }
  @Override
  public void deserializationInit(Screen0011 p,World0001 pw,MetaCreature<?> type) {
    super.deserializationInit(p,pw,type);
    //---
    data.type=pw.metaItems.array()[data.typeId];
    //---
    pc=pw.entities.items;
    outerBox=limitBox=new MovementLimitBox(this);
    limitBox.usePlatform=true;
  }
  public DroppedItem(Screen0011 p,World0001 pw,float x,float y,float xVel,float yVel,DroppedItemType type,Item data) {
    super(p,pw,new MassPoint(x,y,xVel,yVel),type);
    this.data=data;
    pc=data.type.pc.pw.entities.items;
    outerBox=limitBox=new MovementLimitBox(this);
    limitBox.usePlatform=true;
  }
  @Override
  public void update() {
    limitBox.preCtrlUpdate();
    itemAttract();
    if(limitBox.inAir) point.vel.y+=pw.settings.g;
    super.update();
    //---
    if(owner!=null) {
      if(timeCount==0) owner=null;
      else timeCount--;
    }
  }
  public void itemAttract() {
    Iterator<DroppedItem> di=pc.list.descendingIterator();
    while(di.hasNext()) {
      DroppedItem e=di.next();
      if(e==this) break;
      if(e.data.type.countType==ItemCountType.UNCOUNTABLE) continue;
      if(e.data.type!=data.type) continue;
      float td=UtilMath.dist(e.x(),e.y(),x(),y());
      if(td<pc.itemMergeDist) {
        data.count+=e.data.count;
        e.data.count=0;
        pc.remove.add(e);
      }else if(td<pc.itemMergeMoveDist) {
        float minVel=0.1f,maxVel=0.2f;
        e.point.vel.set((x()-e.x())*p.random(minVel,maxVel),(y()-e.y())*p.random(minVel,maxVel));
      }
    }
  }
  @Override
  public void display() {
    p.image(data.type.tiles[0],x()+type.dx,y()+type.dy,type.w,type.h);
  }
  public void owner(LivingEntity e) {
    owner=e;
    timeCount=60;
  }
  public static void dropItem_2(Screen0011 p,LivingEntity pe,float x,float y,World0001 world,float randomConst,Item e) {
    DroppedItem out=new DroppedItem(p,world,
      x,y,
      world.random(-randomConst,randomConst)*world.settings.blockWidth,
      world.random(-randomConst/2,-randomConst)*world.settings.blockHeight,
      world.metaEntitys.droppedItem,e);
    out.owner(pe);
    world.entities.items.add.add(out);
  }
  public static void dropItem(Screen0011 p,int x,int y,World0001 world,boolean ceilingEmpty,float randomConst,Item e) {
    world.entities.items.add.add(
      new DroppedItem(p,world,
        (x+0.5f)*world.settings.blockWidth,
        (y+1)*world.settings.blockHeight,
        // 0,0,
        world.random(-randomConst,randomConst)*world.settings.blockWidth,
        world.random(randomConst/2,randomConst)*world.settings.blockHeight*(ceilingEmpty?-1:1),
        world.metaEntitys.droppedItem,e));
  }
  public static class DroppedItemType extends MetaCreature<DroppedItem>{
    {
      w=12;
      h=12;
      dx=-6;
      dy=-12;
    }
    public DroppedItemType(MetaCreatureCenter0001 pc,int id) {
      super(pc,"dropped item",id,4,0,0);
      immortal=true;
    }
    @Override
    public void init() {}
  }
  public static class DroppedItemCenter extends GameEntityCenter<Screen0011,DroppedItem>{
    public float itemMergeMoveDist=54,itemMergeDist=18;
    public DroppedItemCenter(Screen0011 p,MultiGameEntityCenter0001 pc) {
      super(p,pc);
    }
  }
}