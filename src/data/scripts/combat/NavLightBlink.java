package data.scripts.combat;

import com.fs.starfarer.api.AnimationAPI;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;

public class NavLightBlink implements EveryFrameWeaponEffectPlugin{
    private AnimationAPI animation=null;
    private String name=null;
    private boolean[] codeSeq={true,false,true,true};
    private int codeIdx = 0;
    private int codeLen = 4;
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon){
        if(name==null){
            name = weapon.getShip().getName();
            codeLen = codeSeq.length;
        }
        if(weapon==null||weapon.getAnimation()==null)return;
        //weapon do not animate or ship get destroy
        // set hatch open level by charge level
        if(animation==null){
            animation = weapon.getAnimation();
            animation.pause();
            animation.setFrame(1);
        }
        if(codeSeq[codeIdx])animation.setFrame(0);//light up
        else animation.setFrame(1);
        codeIdx = (codeIdx+1)%codeLen;
        //int offset = animation.getFrame();
        //animation.setFrame((offset+1)%2);
    }
}
