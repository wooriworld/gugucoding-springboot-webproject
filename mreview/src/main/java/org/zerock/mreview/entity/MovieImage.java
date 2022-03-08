package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString(exclude = "movie")
@AllArgsConstructor
@NoArgsConstructor
public class MovieImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;
    private String uuid;
    private String imgName;
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
