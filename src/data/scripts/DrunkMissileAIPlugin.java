package data.scripts;

import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.GuidedMissileAI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.ShipCommand;

public class DrunkMissileAIPlugin implements MissileAIPlugin, GuidedMissileAI{
    protected MissileAPI missile;
    protected CombatEntityAPI target;
    DrunkMissileAIPlugin(MissileAPI missile){
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
        this.missile.giveCommand(ShipCommand.TURN_LEFT);
    };
}
