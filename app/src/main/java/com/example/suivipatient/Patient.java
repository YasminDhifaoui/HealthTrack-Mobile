package com.example.suivipatient;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private String date;
    private int tension;
    private int rythme;
    public Patient(int id, String nom, String prenom, String date, int tension,
                   int rythme) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
        this.tension = tension;
        this.rythme = rythme;
    }
    public int getId() {  return id; }
    public String getNom() {  return nom;  }
    public String getPrenom() {  return prenom; }
    public String getDate() {  return date; }
    public int getTension() {   return tension;  }
    public int getRythme() {   return rythme; }
    @Override
    public String toString() {
        return nom + "-" + prenom + "-" + date;
    }

}
