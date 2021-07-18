package data.scripts.combat;

import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.GuidedMissileAI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.ShipCommand;

public class DrunkMissileAIPlugin implements MissileAIPlugin, GuidedMissileAI{
    protected MissileAPI missile;
    protected CombatEntityAPI target;
    private int rngcnt=0;
    private int[] rngpool = {5,6,4,8,1,9,7,2,10,3};
    public DrunkMissileAIPlugin(MissileAPI missile){
        this.missile = missile;
    };
    
    public CombatEntityAPI getTarget(){
        return this.target;
    };
    public void setTarget(CombatEntityAPI target){
        this.target = target;
    };
    
    public void advance(float amount){
        //give order to missile by giveCommand()
        //now always turn left to see if it actually work
        float elapsed = this.missile.getElapsed();
        if(elapsed>0.0){
            float throttle = 1;
            if(this.rngpool[this.rngcnt++]<=throttle)
            this.missile.giveCommand(ShipCommand.ACCELERATE);
            if(this.rngcnt>=10)this.rngcnt=0;
        }
        //float Kp = 1.0f;
    };
}
