package data.scripts.combat;

import com.fs.starfarer.api.AnimationAPI;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;

public class NavLightBlink implements EveryFrameWeaponEffectPlugin{
    private AnimationAPI animation=null;
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon){
        if(weapon==null||weapon.getAnimation()==null)return;
        //weapon do not animate or ship get destroy
        // set hatch open level by charge level
        if(animation==null){
            animation = weapon.getAnimation();
            animation.pause();
            animation.setFrame(1);
        }
        int offset = animation.getFrame();
        animation.setFrame((offset+1)%2);
    }
}
