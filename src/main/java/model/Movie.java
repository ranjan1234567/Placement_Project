package model;

/** Stores information about one movie. */
public class Movie {
    private String movieId;
    private String title;
    private String genre;
    private int duration;

    public Movie(String movieId, String title, String genre, int duration) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return movieId + " | " + title + " | " + genre + " | " + duration + " minutes";
    }
}
