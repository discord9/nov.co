package data.scripts.combat;


import com.fs.starfarer.api.AnimationAPI;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;

import org.lwjgl.util.vector.Vector2f;

import data.scripts.ai.MagicMissileAI;
import data.scripts.util.MagicTargeting;

public class AntiFighterMissileLaunchEffect implements EveryFrameWeaponEffectPlugin{
    private int missileNum,maxMissileNum=-1;//CustomPrimary
    private boolean leftLoad=true,rightLoad=true;
    private boolean isReloading = false;
    private float startReloadTime = 0.0f;
    private static float reloadingTime = 3.0f;
    private int maxRange = 1800;//Ref: WG:RD
    private String missileId = "uf_9M311_launcher";

    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon){
        if(maxMissileNum==-1){
            missileNum = maxMissileNum = Integer.parseInt(weapon.getSpec().getCustomPrimary());
        }
        if(weapon==null||weapon.getAnimation()==null)return;
        //weapon do not animate or ship get destroy
        //check if needed to launch missile
        //because it's checking before launch, the search parameter should be different to in-flight AI
        ShipAPI ship = weapon.getShip();
        ShipAPI findTarget = MagicTargeting.pickTarget(
            ship, //seeker
            MagicTargeting.targetSeeking.LOCAL_RANDOM, //seeks
            maxRange, 
            360,
            4, //Target Fighter only
            0, 
            0, 
            0, 
            0, 
            false
        );
        //if have target in reach
        if(findTarget!=null&&(leftLoad||rightLoad)){
            //if loaded with missile
            //change load state
            int launchLoc = 0;
            Global.getLogger(this.getClass()).info("2K22 launch 9M311!");
            if(leftLoad){
                leftLoad = false;
                launchLoc = -1;
            }
            else if(rightLoad){
                rightLoad = false;
                launchLoc=1;
            }
            //gen the missile
            float angle = weapon.getCurrAngle()+90*launchLoc;
            Vector2f missileLaunchLocation = new Vector2f(0,0);
            Vector2f.add(weapon.getLocation(),new Vector2f(7*(float)Math.cos(angle),7f*(float)Math.sin(angle)),missileLaunchLocation);
            MissileAPI missile = (MissileAPI)engine.spawnProjectile(ship, 
            null, 
            missileId, 
            missileLaunchLocation,
            weapon.getCurrAngle(), 
            ship.getVelocity());
            MagicMissileAI missileAI = new MagicMissileAI(missile, ship);
            missile.setMissileAI(missileAI);
        }
        if(!(leftLoad&&rightLoad)){
            //reload with time
            if(!isReloading&&missileNum>0){
                Global.getLogger(this.getClass()).info("2K22 start reloading!");
                isReloading = true;
                startReloadTime = engine.getTotalElapsedTime(false);//pause not included
            }else if(isReloading&&engine.getTotalElapsedTime(false)>=startReloadTime+reloadingTime){
                Global.getLogger(this.getClass()).info("2K22 complete reloading!");
                isReloading = false;
                if(missileNum>=2){
                    missileNum -= 2;
                    leftLoad = rightLoad = true;
                }else if(missileNum==1){
                    missileNum -= 1;
                    leftLoad = true;
                }
            }
        }

        //set the animation.
        //01:right load
        //02:left load
        //03:all load
        AnimationAPI animation = weapon.getAnimation();
        animation.pause();
        int offset = 1+(!leftLoad?1:0) +(!rightLoad?2:0);
        if(missileNum<=0)offset = 5;
        if(animation.getFrame()!=offset){
            int numsFrame = animation.getNumFrames();
            Global.getLogger(this.getClass()).info("switch animate to frame "+offset+"/"+numsFrame+"!");
            animation.setFrame(offset);
        }
    }
}
