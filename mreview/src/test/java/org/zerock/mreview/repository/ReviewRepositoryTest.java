package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void insertMovieReviews() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long mno = (long)(Math.random()*100)+1;
            Long mid = (long)(Math.random()*100)+1;

            Movie movie = Movie.builder().mno(mno).build();
            Member member = Member.builder().mid(mid).build();
            int grade = (int)(Math.random()*5)+1;

            Review review = Review.builder()
                            .movie(movie)
                            .member(member)
                            .grade(grade)
                            .text("text"+i)
                            .build();

            reviewRepository.save(review);
        });
    }

    @Test
    void testGetMovieReviews() {
        Movie movie = Movie.builder().mno(92L).build();
        List<Review> result = reviewRepository.findByMovie(movie);
        result.forEach(movieReview -> {
            System.out.println(movieReview.getReviewnum());
            System.out.println(movieReview.getGrade());
            System.out.println(movieReview.getText());
            System.out.println(movieReview.getMember().getEmail());
        });
    }

    @Transactional
    @Rollback(value = false)
    @Test
    void testDeleteMember() {
        Long mid = 1L;
        Member member = Member.builder().mid(mid).build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }
}