package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin.PickPriority;
import data.scripts.DrunkMissileAIPlugin;
//import DrunkMissileAIPlugin;

public class UnityForceModPlugin extends BaseModPlugin{
    //test
    @Override
    public PluginPick<MissileAIPlugin> pickMissileAI(MissileAPI missile, ShipAPI launchingShip){
        //return super.pickMissileAI(missile, launchingShip);
        //用自己写的导弹AI
        
        MissileAIPlugin missileAI = new DrunkMissileAIPlugin(missile);
        return new PluginPick<MissileAIPlugin>(missileAI, PickPriority.MOD_SET);
    }
}
