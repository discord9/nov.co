package data.scripts.combat;

import com.fs.starfarer.api.AnimationAPI;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;

public class MissileSiloHatch956 implements EveryFrameWeaponEffectPlugin{
    private AnimationAPI animation=null;
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon){
        // set hatch open level by charge level
        if(weapon==null||weapon.getAnimation()==null)return;
        //weapon do not animate or ship get destroy
        if(animation==null){
            animation = weapon.getAnimation();
            animation.pause();
            animation.setFrame(1);
        }
        float chargeLevel = weapon.getChargeLevel();
        int offset = (int)(3*(chargeLevel)+1);
        offset = Math.min(offset,3);
        animation.setFrame(offset);
    }
}
