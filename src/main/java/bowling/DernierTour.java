 package bowling;

// DernierTour : handles 3 throws if spare/strike, else 2.
public class DernierTour extends Tour {

    private int tir1 = -1;
    private int tir2 = -1;
    private int tir3 = -1;
    private int nbLancers = 0;

    public DernierTour() {
        super();
    }

    @Override
    public void lancer(int nbQuilles) {
        if (nbQuilles < 0) throw new IllegalArgumentException("Impossible d'avoir des quilles négatives.");
        if (nbLancers == 0) tir1 = nbQuilles;
        else if (nbLancers == 1) tir2 = nbQuilles;
        else if (nbLancers == 2) tir3 = nbQuilles;
        else throw new IllegalStateException("Trop de lancers pour le dernier tour !");
        nbLancers++;
    }

    public int getPremier() { return tir1; }
    public int getSecond() { return tir2; }
    public int getTroisieme() { return tir3; }

    @Override
    public boolean estStrike() {
        return tir1 == 10;
    }

    @Override
    public boolean estSpare() {
        return (tir1 >= 0 && tir2 >= 0 && (tir1 + tir2 == 10) && !estStrike());
    }

    @Override
    public boolean termine() {
        if (estStrike() || estSpare()) return nbLancers == 3;
        return nbLancers == 2;
    }

    @Override
    public int bonusStrike() {
        // Bonus = deux premiers tirs du 10e tour
        return Math.max(0, tir1) + Math.max(0, tir2);
    }

    @Override
    public int scoreAvecSuivant() {
        int somme = (tir1 == -1 ? 0 : tir1) + (tir2 == -1 ? 0 : tir2) + (tir3 == -1 ? 0 : tir3);
        return somme;
    }
}
