package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@Table(name = "m_member")
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;
    private String email;
    private String pw;
    private String nickname;
}
