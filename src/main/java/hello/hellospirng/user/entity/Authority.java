package hello.hellospirng.user.entity;

import hello.hellospirng.user.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Authority {
    @Id
    @Enumerated(EnumType.STRING)
    private RoleType authorityType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        return authorityType == authority.authorityType;
    }

    @Override
    public int hashCode() {
        return authorityType != null ? authorityType.hashCode() : 0;
    }
}


