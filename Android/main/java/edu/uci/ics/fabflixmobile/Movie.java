package edu.uci.ics.fabflixmobile;

//Base class to hold information about our property
public class Movie {

    //property basics
    private String title;
    private String year;
    private String director;
    private String genre;
    private String star;
    private String rating;

    //constructor
    public Movie(String newTitle, String newYear, String newDirector, String newGenre, String newStar, String newRating){

        this.title = newTitle;
        this.year = newYear;
        this.director = newDirector;
        this.genre = newGenre;
        this.star = newStar;
        this.rating = newRating;
    }

    //getters
    public String getTitle() {return title; }
    public String getYear() {return year; }
    public String getDirector() {return director; }
    public String getGenre() {return genre; }
    public String getStar() { return star; }
    public String getRating() {return rating;}
}