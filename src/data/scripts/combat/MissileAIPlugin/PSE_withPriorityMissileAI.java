package data.scripts.combat.MissileAIPlugin;

import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;

import data.scripts.util.MagicTargeting;

public class PSE_withPriorityMissileAI extends PSE_BaseCompetentMissileAI{
    int fighters=0,
        frigates=1, 
        destroyers=2,
        cruisers=3,
        capitals=4;
    public PSE_withPriorityMissileAI(
        MissileAPI missile, 
        ShipAPI launchingShip,
        int fighters,
        int frigates, 
        int destroyers,
        int cruisers,
        int capitals){
        super(missile,launchingShip);
        this.fighters = fighters;
        this.frigates = frigates;
        this.destroyers = destroyers;
        this.cruisers = cruisers;
        this.capitals = capitals;
    }

    @Override
    protected CombatEntityAPI findTarget(){
        ShipAPI pickTarget = MagicTargeting.pickTarget(
            missile,
            MagicTargeting.targetSeeking.FULL_RANDOM,
            1800,//max search range
            360,//cone
            fighters,
            frigates, 
            destroyers,
            cruisers,
            capitals, 
            true
        );
        //Global.getLogger(this.getClass()).info(String.format( "New target:%s", pickTarget.getName()));
        return pickTarget;
    }
}
