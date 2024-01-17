package diop.lucien.springsecurityjwt.entities;

public class Pays {
    private String nom;
    private String continent;

    public Pays(String nom, String continent) {
        this.nom = nom;
        this.continent = continent;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    @Override
    public String toString() {
        return "Pays{" +
                "nom='" + nom + '\'' +
                ", continent='" + continent + '\'' +
                '}';
    }
}
