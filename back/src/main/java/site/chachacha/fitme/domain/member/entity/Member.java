package site.chachacha.fitme.domain.member.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.chachacha.fitme.domain.auth.entity.Token;
import site.chachacha.fitme.common.entity.BaseEntity;
import site.chachacha.fitme.domain.dressroom.entity.DressRoom;
import site.chachacha.fitme.domain.member.dto.MemberUpdate;
import site.chachacha.fitme.util.RandomNickname;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)
@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    // 카카오톡이 제공하는 사용자 구분을 위한 ID
    @NotNull
    @Column(unique = true)
    private Long providerId;

    @NotBlank
    @Column(length = 30)
    private String nickname;

    // false 남자, true 여자
    private Boolean gender;

    @Column(length = 300)
    private String profileUrl;

    @Column(length = 20)
    private String phoneNumber;

    private Integer birthYear;

    @Column(length = 200)
    private String address;

    @NotNull
    private Boolean isDeleted = false;

    @Setter
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "token_id")
    private Token token;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DressRoom> dressRooms = new ArrayList<>();

    @Builder
    protected Member(Long providerId) {
        this.providerId = providerId;
        this.nickname = RandomNickname.getRandomNickname();
    }

    // == 비즈니스 로직 == //
    public void updateProfile(MemberUpdate memberUpdate) {
        this.nickname = memberUpdate.getNickname();
        this.gender = memberUpdate.getGender();
        this.phoneNumber = memberUpdate.getPhoneNumber();
        this.birthYear = memberUpdate.getBirthYear();
        this.address = memberUpdate.getAddress();
    }

    // == 연관관계 메서드 =
    public void addDressRoom(DressRoom dressRoom) {
        dressRooms.add(dressRoom);
    }
}
