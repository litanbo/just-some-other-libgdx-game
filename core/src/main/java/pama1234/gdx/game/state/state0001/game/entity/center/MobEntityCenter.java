package pama1234.gdx.game.state.state0001.game.entity.center;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.center.MultiGameEntityCenter0001.GameEntityCenter;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.MobEntity;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature.SpawnData;
import pama1234.gdx.game.state.state0001.game.net.NetMode;
import pama1234.gdx.game.state.state0001.game.player.Player;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.UtilMath;

public class MobEntityCenter extends GameEntityCenter<Screen0011,MobEntity>{
  public MobEntityCenter(Screen0011 p,MultiGameEntityCenter0001 pc,MobEntity in) {
    super(p,pc,in);
  }
  public MobEntityCenter(Screen0011 p,MultiGameEntityCenter0001 pc,MobEntity[] in) {
    super(p,pc,in);
  }
  public MobEntityCenter(Screen0011 p,MultiGameEntityCenter0001 pc) {
    super(p,pc);
  }
  @Override
  public void refresh() {
    for(MobEntity e:list) if(e.life.pos<=0) {
      e.dispose();
      remove.add(e);
    }
    if(pc.pw.netMode()!=NetMode.client) {
      for(Player player:pc.players.list) testCreatureSpawnWithPlayer(player);
      testCreatureSpawnWithPlayer(pc.pw.yourself);//TODO
    }
    super.refresh();
  }
  public void testCreatureSpawnWithPlayer(Player player) {
    World0001 world=pc.pw;
    for(MetaCreature<?> e:world.metaEntitys.list) {
      if(e.spawnDatas==null) continue;
      if(e.count>=e.naturalMaxCount*(1+pc.players.list.size())) continue;
      float rdeg=world.random(UtilMath.PI2);
      float rdist=world.random(36,world.regions.data.regionLoadDist/2f);
      float tx=player.cx()+UtilMath.sin(rdeg)*rdist*world.settings.blockWidth,
        ty=player.cy()+UtilMath.cos(rdeg)*rdist*world.settings.blockHeight;
      Block block=world.getBlock(tx,ty);
      if(block==null) continue;
      for(SpawnData i:e.spawnDatas) {
        if(world.random(1)>i.rate) continue;
        if(i.block==block.type) {
          LivingEntity out=e.createCreature(tx,ty);
          if(out instanceof MobEntity mob) {
            mob.target=player;
            world.entities.mobEntities.add.add(mob);
          }else world.entities.pointEntities.add.add(out);
          return;
        }
      }
    }
  }
}