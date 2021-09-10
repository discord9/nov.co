package data.scripts.combat;

import com.fs.starfarer.api.AnimationAPI;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;

public class RotateExtendRadar implements EveryFrameWeaponEffectPlugin{
    private AnimationAPI animation=null;
    private boolean isfirst = true;
    private float rot_spd;
    private float extendLevel = 0.0f;
    private float extendTime;
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon){
        if(engine.isPaused())return;
        // set hatch open level by charge level
        if(weapon==null||weapon.getAnimation()==null)return;
        //weapon do not animate or ship get destroy
        if(isfirst){
            isfirst = false;
            animation = weapon.getAnimation();
            animation.pause();
            rot_spd = Float.parseFloat(weapon.getSpec().getCustomPrimary());
            extendTime = Float.parseFloat(weapon.getSpec().getCustomAncillary());
        }
        int frameNum = animation.getNumFrames();
        int offset = (int)((frameNum-1)*extendLevel);
        offset = Math.min(offset,frameNum-1);
        animation.setFrame(offset);
        extendLevel = engine.getTotalElapsedTime(false)/extendTime;//not included pause time
        extendLevel = Math.min(extendLevel,1.0f);
        //extendLevel = extendLevel - (int)extendLevel;

        //rotate using CustomPrimary spd
        //SpriteAPI sprite = weapon.getSprite();
        float angle = weapon.getCurrAngle();
        if(extendLevel>=1.0f)
            angle += rot_spd * amount;
        //angle += 100.0f * amount;
        weapon.setCurrAngle(angle);
    }
}
