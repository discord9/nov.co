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
    private float AverageInterval=0.0f;
    public DrunkMissileAIPlugin(MissileAPI missile){
        this.missile = missile;
        //Global.getLogger(this.getClass()).info("Missile speed"+missile.getMaxSpeed());
    };
    
    public CombatEntityAPI getTarget(){
        return this.target;
    };
    public void setTarget(CombatEntityAPI target){
        this.target = target;
    };
    
    public void advance(float amount){
        if(this.AverageInterval==0.0f)
            this.AverageInterval = amount;
        //give order to missile by giveCommand()
        //now always turn left to see if it actually work
        float elapsed = this.missile.getElapsed();
        
        int throttle = 5;
        int adjustThrottle = (int)(throttle * this.AverageInterval/amount);
        //adjust throttle using time passed by.
        //the goal is to have a PWM-ish missile engine.
        if(this.rngpool[this.rngcnt]<=adjustThrottle)
            this.missile.giveCommand(ShipCommand.ACCELERATE);
        this.missile.giveCommand(ShipCommand.TURN_LEFT);
        if(++this.rngcnt>=this.rngpool.length)this.rngcnt=0;
        
        //float Kp = 1.0f;
        
        this.AverageInterval = 0.9f * this.AverageInterval + 0.1f * amount;
        //average over time
    }
}
