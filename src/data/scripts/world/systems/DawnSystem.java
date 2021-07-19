package data.scripts.world.systems;



import java.awt.Color;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.concurrent.locks.Condition;



import com.fs.starfarer.api.Global;

import com.fs.starfarer.api.campaign.*;

import com.fs.starfarer.api.campaign.econ.EconomyAPI;

import com.fs.starfarer.api.campaign.econ.MarketAPI;

import com.fs.starfarer.api.characters.PersonAPI;

import com.fs.starfarer.api.impl.campaign.ids.*;

import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;

import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;

import com.fs.starfarer.api.impl.campaign.procgen.StarAge;

import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;

import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin.AsteroidFieldParams;

import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;

import com.fs.starfarer.api.util.Misc;

import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin.MagneticFieldParams;

import org.lazywizard.lazylib.MathUtils;




public class DawnSystem {

   public void generate(SectorAPI sector) {

      StarSystemAPI system = sector.createStarSystem("Dawn");

      system.getLocation().set(-39000,39000); //top leftish

      system.setBackgroundTextureFilename("graphics/UnityForce/backgrounds/DawnSystemBackground.jpg");

      // create the star and generate the hyperspace anchor for this system
      PlanetAPI DawnStar = system.initStar(
            "Dawn", 
            "star_red_giant", 
            1100f, 
            450);

      system.setLightColor(new Color(239, 155, 128));

      float NovgorodDist = 1850f;
      float NovgorodRadius = 190f;
      float NovgorodOrbitDays = 30f;

      float StorozevojDist = 3500f;
      float StorozevojRadius = 220f;
      float StorozevojOrbitDays = 330f;

      //Novgorod, a ordinary barren planet.
      PlanetAPI Novgorod = system.addPlanet("novgorod", 
      DawnStar,//focus
      Global.getSettings().getString("sector_entity_name", "novgorod"),//name
      "barren",//from starsector-core\data\config\planets.json
      360*(float)Math.random(),//angle
      NovgorodRadius,//radius
      NovgorodDist,//orbitRadius
      NovgorodOrbitDays);

      Novgorod.setCustomDescriptionId("uss_dawn_novgorod");//reference descriptions.csv
      Novgorod.getMarket().addCondition("very_hot");
      Novgorod.getMarket().addCondition("ore_ultrarich");
      Novgorod.getMarket().addCondition("rare_ore_moderate");

      //Storozevoj, the Capital of USSR
      PlanetAPI Storozevoj = system.addPlanet("storozevoj", 
      DawnStar,//focus
      Global.getSettings().getString("sector_entity_name", "storozevoj"),//name
      "terran-eccentric",//from starsector-core\data\config\planets.json
      360*(float)Math.random(),//angle
      StorozevojRadius,//radius
      StorozevojDist,//orbitRadius
      StorozevojOrbitDays);
      //"common_wish"
      Storozevoj.setCustomDescriptionId("uss_dawn_storozevoj");
   }
}
