package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString(exclude = {"movie", "member"})
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    private int grade;
    private String text;
}
