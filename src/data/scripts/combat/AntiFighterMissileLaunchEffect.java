package data.scripts.combat;

import com.fs.starfarer.api.AnimationAPI;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;

import data.scripts.ai.MagicMissileAI;
import data.scripts.util.MagicTargeting;

public class AntiFighterMissileLaunchEffect implements EveryFrameWeaponEffectPlugin{
    private int missileNum = 64;
    private boolean leftLoad=true,rightLoad=true;
    private boolean isReloading = false;
    private float startReloadTime = 0.0f;
    private static float reloadingTime = 3.0f;
    private int maxRange = 3325;//Ref: WG:RD
    private String missileId = "uf_9M311_launcher";

    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon){
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
            int launchLoc = -1;
            Global.getLogger(this.getClass()).info("2K22 launch 9M311!");
            if(leftLoad){
                leftLoad = false;
                launchLoc = 0;
            }
            else if(rightLoad){
                rightLoad = false;
                launchLoc=1;
            }
            //TODO:gen the missile
            MissileAPI missile = (MissileAPI)engine.spawnProjectile(ship, 
            null, 
            missileId, 
            weapon.getLocation(),
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
        if(animation.getFrame()!=offset){
            int numsFrame = animation.getNumFrames();
            Global.getLogger(this.getClass()).info("switch animate to frame "+offset+"/"+numsFrame+"!");
            animation.setFrame(offset);
        }
    }
}
