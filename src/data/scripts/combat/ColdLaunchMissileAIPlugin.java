package data.scripts.combat;

import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.EngineSlotAPI;
import com.fs.starfarer.api.combat.GuidedMissileAI;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.ShipCommand;
import com.fs.starfarer.api.combat.ShipEngineControllerAPI.ShipEngineAPI;
import com.fs.starfarer.api.Global;


import java.awt.Color;
import java.util.*;

public class ColdLaunchMissileAIPlugin implements MissileAIPlugin, GuidedMissileAI{
    protected MissileAPI missile;
    protected CombatEntityAPI target;
    private int rngcnt=0;
    private int[] rngpool = {5,6,4,8,1,9,7,2,10,3};
    private float averageInterval=0.0f;
    private List<Color> originalEngineColors;
    private List<Color> originalContrailColor;
    public ColdLaunchMissileAIPlugin(MissileAPI missile){
        this.missile = missile;
        /*
        //set Flame Level to zero to sim cold launch
        //FAILED
        List<EngineSlotAPI> slotList = new ArrayList<EngineSlotAPI>();
        List<ShipEngineAPI> engineList = missile.getEngineController().getShipEngines();
        int len = engineList.size();
        for(int i=0;i<len;i++){
            slotList.add(i,engineList.get(i).getEngineSlot());
            missile.getEngineController().setFlameLevel(slotList.get(i), 10.0f);
        }
        
        
        List<ShipEngineAPI> engineList = missile.getEngineController().getShipEngines();
        int len = engineList.size();
        //Color opaque = new Color(0,0,0,0);
        for(int i=0;i<len;i++){
            engineList.get(i).disable();
            
            this.originalEngineColors.add(i,engineList.get(i).getEngineColor());
            this.originalContrailColor.add(i,engineList.get(i).getContrailColor());
            missile.getEngineController().fadeToOtherColor(engineList.get(i), opaque, opaque, 0.0f, 1.0f);
            
        }
        */
        //Global.getLogger(this.getClass()).info("Missile speed"+missile.getMaxSpeed());
    };

    public CombatEntityAPI getTarget(){
        return this.target;
    };
    public void setTarget(CombatEntityAPI target){
        this.target = target;
    };
    
    public void advance(float amount){
        if(this.missile.getElapsed()>1.0){
            missile.giveCommand(ShipCommand.ACCELERATE);
            missile.giveCommand(ShipCommand.TURN_LEFT);
        }else{
            Global.getLogger(this.getClass()).info("Flamingout:"+missile.getEngineController().isFlamingOut());
            Global.getLogger(this.getClass()).info("Flamedout:"+missile.getEngineController().isFlamedOut());
            //missile.getEngineController().forceFlameout(true);
            missile.flameOut();
            /*
            for(ShipEngineAPI engine: missile.getEngineController().getShipEngines()){
                //engine.disable();
                missile.getEngineController().setFlameLevel(engine.getEngineSlot(), -1.0f);
                Global.getLogger(this.getClass()).info("Missile forceFlameout!:"+missile.getEngineController().isFlamedOut());
            }
            */
        }
    }
}
