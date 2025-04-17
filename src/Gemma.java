public enum Gemma {

    GEMMA_BLU (".\\Immagini\\ImmaginiGemme\\blu.png"),
    GEMMA_ROSSA (".\\Immagini\\ImmaginiGemme\\rossa.png"),
    GEMMA_VERDE (".\\Immagini\\ImmaginiGemme\\verde.png"),
    GEMMA_GIALLA (".\\Immagini\\ImmaginiGemme\\gialla.png"),
    GEMMA_VIOLA (".\\Immagini\\ImmaginiGemme\\viola.png"),
    GEMMA_ARANCIONE (".\\Immagini\\ImmaginiGemme\\arancione.png");

    String path;

    Gemma (String path){
        this.path = path;
    }
}
