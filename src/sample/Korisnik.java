package sample;

public class Korisnik {

    public int idKorisnik;
    public String korisnickoIme;
    public String lozinka;
    public String ime;
    public String prezime;
    public String telefon;
    public String email;

    public Korisnik(int idKorisnik,String korisnickoIme,String lozinka,String ime,String prezime,String telefon,String email){
        this.idKorisnik = idKorisnik;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.telefon = telefon;
        this.email = email;
    }
}
