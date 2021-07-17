package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.PluginPick;
//import data.scripts.DrunkMissileAIPlugin;



//import DrunkMissileAIPlugin;

public class UnityForceModPluin extends BaseModPlugin{
    //test
    @Override
    public PluginPick<MissileAIPlugin> pickMissileAI(MissileAPI missile, ShipAPI launchingShip){
        if(true){
            //如果没有某个hull mod 则一般地调
            return super.pickMissileAI(missile, launchingShip);
        }else{
            //用自己写的导弹AI
            /*
            missileAI = DrunkMissileAIPlugin(missile);
            return missileAI;
            */
        }
    }
}
