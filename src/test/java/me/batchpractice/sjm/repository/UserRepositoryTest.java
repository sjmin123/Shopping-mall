package me.batchpractice.sjm.repository;

import me.batchpractice.sjm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByuserId성공테스트() {
        //given
        User user = userRepository.save(User.builder()
                .userId("asdf")
                .password("asdf")
                .build());

        //when
        Optional<User> result = userRepository.findByUserId(user.getUserId());

        //then
        assertThat(result.get().getUserId()).isEqualTo("asdf");
        assertThat(result.get().getPassword()).isEqualTo("asdf");
        assertThat(result.get().getId()).isEqualTo(1);
    }

    @Test
    public void save성공테스트() {
        //given
        User user = User.builder()
                .userId("asdf")
                .password("asdf")
                .build();
        //when
        User result = userRepository.save(user);

        //then
        assertThat(result.getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
        assertThat(result.getId()).isEqualTo(user.getId());
    }

}