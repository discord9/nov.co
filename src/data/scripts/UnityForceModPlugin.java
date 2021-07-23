package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;

import data.scripts.ai.MagicMissileAI;
import data.scripts.combat.MissileAIPlugin.ColdLaunchMissileAIPlugin;
import data.scripts.combat.MissileAIPlugin.StageDetachMissileAIPlugin;
import data.scripts.combat.MissileAIPlugin.PSE_BaseCompetentMissileAI;
import data.scripts.world.UnityForceModGen;

import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin.PickPriority;

public class UnityForceModPlugin extends BaseModPlugin{
    private static void initUnityForceMod(){
        new UnityForceModGen().generate(Global.getSector());
    }
    @Override
    public void onNewGame(){
        Global.getLogger(this.getClass()).info("initing USSR mod!");
        //initUnityForceMod();
        //do not do it now,gen not complete!
    }
    //test
    @Override
    public PluginPick<MissileAIPlugin> pickMissileAI(MissileAPI missile, ShipAPI launchingShip){
        switch(missile.getProjectileSpecId()){
            case "uf_zircon_stage_two":
                //use custom AI
                //switch to PSE maybe?
                PSE_BaseCompetentMissileAI ai = new PSE_BaseCompetentMissileAI(missile, launchingShip);
                return new PluginPick<MissileAIPlugin>(ai, PickPriority.MOD_SET);
            case "uf_zircon":
                Global.getLogger(this.getClass()).info("Pick AI for Zircon Missile!");
                StageDetachMissileAIPlugin detachAI = new StageDetachMissileAIPlugin(missile,launchingShip);
                return new PluginPick<MissileAIPlugin>(detachAI, PickPriority.MOD_SET);
            default:
                return super.pickMissileAI(missile, launchingShip);
        }
        //用自己写的导弹AI
        /*
        MissileAIPlugin missileAI = new ColdLaunchMissileAIPlugin(missile);
        return new PluginPick<MissileAIPlugin>(missileAI, PickPriority.MOD_SET);
        */
    }
}
