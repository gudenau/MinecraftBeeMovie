import net.gudenau.mineloader.api.annotation.Mod;

@Mod(name = "BeeMovie", version = "1.0.0.0", mainClass = "net.gudenau.beemovie.BeeMovie")
module net.gudenau.BeeMovie{
    requires MineLoader;

    exports net.gudenau.beemovie to MineLoader;
}