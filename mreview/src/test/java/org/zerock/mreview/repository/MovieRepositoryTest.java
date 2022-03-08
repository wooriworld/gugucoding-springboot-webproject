package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Test
    void insertMovies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("title"+i)
                    .build();

            movieRepository.save(movie);

            int count = (int)(Math.random() * 5) + 1;

            for(int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .imgName("test"+j+".jpg")
                        .movie(movie)
                        .build();

                movieImageRepository.save(movieImage);
            }
        });
    }

    @Test
    void testListPage() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "mno");

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for(Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    void testGetMovieWithAll() {
        List<Object[]> list = movieRepository.getMovieWithAll(94L);
        for(Object[] arr : list) {
            System.out.println(Arrays.toString(arr));
        }
    }
}