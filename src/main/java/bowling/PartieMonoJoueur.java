 package bowling;

// PartieMonoJoueur : gère la partie complète d'un joueur
public class PartieMonoJoueur {

    private Tour debut;
    private Tour actuel;

    public PartieMonoJoueur() {
        Tour dernier = new DernierTour();
        Tour pile = dernier;
        for (int i = 9; i >= 1; i--) {
            pile = new Tour(i, pile);
        }
        debut = pile;
        actuel = pile;
    }

    public boolean enregistreLancer(int quilles) {
        if (estTerminee())
            throw new IllegalStateException("La partie est finie !");
        actuel.lancer(quilles);
        if (actuel.termine()) {
            actuel = actuel.getSuivant();
            // Si plus de tour, partie terminée
            if (actuel == null) return false;
        }
        // Retourne vrai si on joue encore dans le même tour
        return actuel != null && actuel.getNombreDeTirs() != 0;
    }

    public int score() {
        return debut.scoreAvecSuivant();
    }

    public boolean estTerminee() {
        return actuel == null;
    }

    public int numeroTourCourant() {
        return estTerminee() ? 0 : actuel.getNumero();
    }

    public int numeroProchainLancer() {
        return estTerminee() ? 0 : actuel.getNombreDeTirs() + 1;
    }
}
