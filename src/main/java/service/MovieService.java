package service;

import java.util.ArrayList;
import model.Movie;
import model.Show;

/** Stores movie and show details in ArrayLists. */
public class MovieService {
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private ArrayList<Show> shows = new ArrayList<Show>();

    /** Adds sample data so the project can be tested immediately. */
    public void loadSampleData() {
        movies.add(new Movie("M101", "Ramayana Part1", "Mythology", 192));
        movies.add(new Movie("M102", "Godzilla Minus Zero", "Sci-Fi", 135));
        movies.add(new Movie("M103", "OM", "Action", 172));

        shows.add(new Show("S101", "M101", "PVR Central", "21-Nov-2026 6:30 PM", 220, 20));
        shows.add(new Show("S102", "M102", "INOX City Centre", "21-Nov-2026 8:00 PM", 180, 20));
        shows.add(new Show("S103", "M103", "PVR Central", "22-Nov-2026 3:00 PM", 200, 20));
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Show> getShows() {
        return shows;
    }

    /** Searches for a movie using its ID. */
    public Movie findMovie(String movieId) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getMovieId().equalsIgnoreCase(movieId)) {
                return movies.get(i);
            }
        }
        return null;
    }

    /** Searches for a show using its ID. */
    public Show findShow(String showId) {
        for (int i = 0; i < shows.size(); i++) {
            if (shows.get(i).getShowId().equalsIgnoreCase(showId)) {
                return shows.get(i);
            }
        }
        return null;
    }
}
