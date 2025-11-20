 package bowling;

// Tour normal : gère deux tirs, sauf dernier tour
public class Tour {

    private int numero;
    private int premierTir = -1;
    private int secondTir = -1;
    private int nombreDeTirs = 0;

    private Tour prochainTour;

    // Constructeur de base (non utilisé directement)
    public Tour() {}

    // Création d'un tour normal avec numéro et suivant
    public Tour(int num, Tour suivant) {
        if (num < 1 || num > 9) {
            throw new IllegalArgumentException("Le numéro du tour doit être entre 1 et 9.");
        }
        if (suivant == null) throw new IllegalArgumentException("Le tour suivant ne peut pas être null.");
        numero = num;
        prochainTour = suivant;
    }

    public void lancer(int nbQuilles) {
        if (nbQuilles < 0) throw new IllegalArgumentException("Impossible : quilles négatives.");
        if (nombreDeTirs == 0) {
            premierTir = nbQuilles;
        } else if (nombreDeTirs == 1) {
            if (premierTir + nbQuilles > 10)
                throw new IllegalArgumentException("Pas plus de 10 quilles dans un tour normal.");
            secondTir = nbQuilles;
        } else {
            throw new IllegalStateException("Seulement 2 tirs par tour (sauf dernier) !");
        }
        nombreDeTirs++;
    }

    public boolean estStrike() {
        return premierTir == 10;
    }

    public boolean estSpare() {
        return premierTir >= 0 && secondTir >= 0 && (premierTir + secondTir == 10) && !estStrike();
    }

    public boolean termine() {
        return estStrike() || nombreDeTirs == 2;
    }

    public int bonusSpare() {
        return prochainTour.premierTir;
    }

    public int bonusStrike() {
        // Si prochain tour est le dernier
        if (prochainTour instanceof DernierTour) {
            // Bonus = 2 premiers tirs du dernier tour
            return Math.max(0, ((DernierTour) prochainTour).getPremier()) +
                   Math.max(0, ((DernierTour) prochainTour).getSecond());
        }
        if (prochainTour.estStrike()) {
            return prochainTour.premierTir + prochainTour.prochainTour.premierTir;
        }
        return prochainTour.premierTir + prochainTour.secondTir;
    }

    public int scoreAvecSuivant() {
        int valeur;
        if (estStrike()) {
            valeur = 10 + bonusStrike();
        } else if (estSpare()) {
            valeur = 10 + bonusSpare();
        } else {
            valeur = (premierTir == -1 ? 0 : premierTir) + (secondTir == -1 ? 0 : secondTir);
        }
        return valeur + prochainTour.scoreAvecSuivant();
    }

    public int getNumero() { return numero; }
    public int getNombreDeTirs() { return nombreDeTirs; }
    public Tour getSuivant() { return prochainTour; }
}
