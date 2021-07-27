package data.scripts.combat.MissileAIPlugin;


import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.GuidedMissileAI;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;

import org.json.JSONException;
import org.json.JSONObject;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import data.scripts.util.MagicTargeting;


public class StageDetachMissileAIPlugin extends PSE_withPriorityMissileAI{
    private JSONObject behaviorSpec;
    private String upperStageWrapperId,behavior,lowerStageWrapperId;
    private float detachRange;
    //改成继承PSE
    public StageDetachMissileAIPlugin(MissileAPI missile, ShipAPI launchingShip){
        super(missile,launchingShip,0,1,2,3,4);
        behaviorSpec = missile.getBehaviorSpecParams();
        try{
            Global.getLogger(this.getClass()).info("parse behaviorSpec of "+missile.getProjectileSpecId());

            behavior = behaviorSpec.getString("behavior");
            upperStageWrapperId = behaviorSpec.getString("upperStageWeapon");
            detachRange = (float)behaviorSpec.getDouble("detachRange");
            lowerStageWrapperId = behaviorSpec.getString("lowerStageWeapon");

            Global.getLogger(this.getClass()).info(String.format( "behavior:%s,upperStage:%s,detachRange:%f", behavior,upperStageWrapperId,detachRange));
        }
        catch(JSONException e){
            Global.getLogger(this.getClass()).info("error when parse behaviorSpec of "+missile.getProjectileSpecId());
            //强制退出？
        }
    }
    public void advance(float amount){
        super.advance(amount);
        //Advance using PSE AI first.
        //Then detach only when:
        //1.within detachRange
        //2.the filght time is almost over
        if(target!=null&&MathUtils.getDistance(missile, target)<=detachRange){
            CombatEngineAPI engine = Global.getCombatEngine();
            engine.spawnProjectile(
            launchingShip, 
            null, 
            upperStageWrapperId, 
            missile.getLocation(), 
            missile.getFacing(), 
            missile.getVelocity()
            );
            //create discard lower stage here
            MissileAPI discard = (MissileAPI)engine.spawnProjectile(
            launchingShip,
            null, 
            lowerStageWrapperId, 
            missile.getLocation(), 
            missile.getFacing(), 
            missile.getVelocity()
            );
            discard.flameOut();
            //TODO:create smoke here
            engine.removeEntity(missile);
        }
    }
}