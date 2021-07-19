package data.scripts.world;

import com.fs.starfarer.api.campaign.SectorAPI;
import data.scripts.world.systems.DawnSystem;

public class UnityForceModGen {
    public void generate(SectorAPI sector) {

        (new DawnSystem()).generate(sector);//Dawn黎明星
    }
}
