package data.scripts.campaign.econ;

import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.Global;

public class CommonWish extends BaseMarketConditionPlugin{
    private String source = Global.getSettings().getString("market_condition","common_wish_title");
    private String desc = Global.getSettings().getString("market_condition","common_wish_desc");
    @Override
    public void apply(String id){
        this.market.getStability().modifyFlat(this.source, 2, this.desc);
    };
    @Override
    public void unapply(String id){
        this.market.getStability().modifyFlat(this.source, -2, this.desc);
    };
}
