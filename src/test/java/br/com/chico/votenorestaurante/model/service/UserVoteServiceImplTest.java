package br.com.chico.votenorestaurante.model.service;

import br.com.chico.votenorestaurante.model.entity.Restaurant;
import br.com.chico.votenorestaurante.model.entity.User;
import br.com.chico.votenorestaurante.model.entity.UserVote;
import br.com.chico.votenorestaurante.model.repository.UserVoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;

/**
 * @author Francisco Almeida on 12/04/2016.
 */
public class UserVoteServiceImplTest {

    private final static Long USER_VOTE_ID = 1L;
    private final static Long USER_ID = 1L;
    private final static Long RESTAURANT_ID = 1L;
    @Mock
    protected UserVoteRepository mockUserVoteRepository;
    @InjectMocks
    private UserVoteServiceImpl target;
    private User userFixture = new User(USER_ID, "Teste", "teste@teste.com");
    private Restaurant restaurantFixture = new Restaurant(RESTAURANT_ID, "Vento Aragano", "/");
    private UserVote userVoteFixture;

    @Before
    public void adocicaMeuAmorAMinhaVidaOeeeeeH() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_save_success() {

        // GIVEN

        userVoteFixture = new UserVote();
        userVoteFixture.setId(USER_VOTE_ID);
        userVoteFixture.setUser(this.userFixture);
        userVoteFixture.setRestaurant(this.restaurantFixture);

        Mockito.when(mockUserVoteRepository.save(userVoteFixture))
                .thenReturn(userVoteFixture);

        // WHEN
        UserVote result = target.save(this.userVoteFixture);

        // THEN
        Mockito.verify(mockUserVoteRepository).save(userVoteFixture);

        assertNotNull(result);
        assertThat(result.getId(), equalTo(1L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_save_failedSaveVoteWithoutUser() {

        // GIVEN

        userVoteFixture = new UserVote();
        userVoteFixture.setId(USER_VOTE_ID);
        userVoteFixture.setRestaurant(this.restaurantFixture);

        Mockito.when(mockUserVoteRepository.save(userVoteFixture))
                .thenReturn(userVoteFixture);

        // WHEN
        UserVote result = target.save(this.userVoteFixture);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_save_failedSaveVoteWithoutRestaurant() {

        // GIVEN
        userVoteFixture = new UserVote();
        userVoteFixture.setId(USER_VOTE_ID);
        userVoteFixture.setUser(this.userFixture);

        Mockito.when(mockUserVoteRepository.save(userVoteFixture))
                .thenReturn(userVoteFixture);

        // WHEN
        UserVote result = target.save(this.userVoteFixture);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_save_failedDataInsert() {
        target.save(null);
    }

    @Test
    public void test_findOne_success() {
        // GIVEN
        userVoteFixture = new UserVote();
        userVoteFixture.setId(USER_VOTE_ID);

        Mockito.when(mockUserVoteRepository.findOne(USER_VOTE_ID))
                .thenReturn(userVoteFixture);
        // WHEN
        UserVote result = target.findOne(USER_VOTE_ID);

        // THEN
        assertNotNull(result);
        assertThat(result.getId(), equalTo(1L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_findOne_failedDataSearch() {
        target.findOne(null);
    }

    @Test
    public void test_delete_success() {

        // GIVEN
        userVoteFixture = new UserVote();
        userVoteFixture.setId(USER_VOTE_ID);

        Mockito.when(mockUserVoteRepository.findOne(Mockito.anyLong()))
                .thenReturn(this.userVoteFixture);

        // WHEN
        target.remove(this.userVoteFixture);

        // THEN
        Mockito.verify(mockUserVoteRepository, times(1)).delete(this.userVoteFixture);

    }

    @Test(expected = IllegalArgumentException.class)
    public void test_delete_failedWithANullObject() {
        Mockito.when(mockUserVoteRepository.findOne(Mockito.anyLong()))
                .thenReturn(null);
        // WHEN
        target.remove(this.userVoteFixture);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void test_delete_filedWithAInvalidUserVote() {

        // GIVEN
        userVoteFixture = new UserVote();
        userVoteFixture.setId(USER_VOTE_ID);

        Mockito.when(mockUserVoteRepository.findOne(Mockito.anyLong()))
                .thenReturn(null);

        // WHEN
        target.remove(this.userVoteFixture);
    }

    @Test
    public void test_findAll_success() {

        // GIVEN
        this.userVoteFixture = new UserVote(USER_VOTE_ID, userFixture, restaurantFixture, 0);
        List<UserVote> userVotesFixture =
                Collections.singletonList(this.userVoteFixture);

        Mockito.when(mockUserVoteRepository.findAll())
                .thenReturn(userVotesFixture);

        // WHEN
        List<UserVote> result = target.findAll();

        // THEN
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(this.userVoteFixture, equalTo(this.userVoteFixture));
    }

    @Test
    public void test_findByUser_success() {

        // GIVEN

        userVoteFixture = new UserVote();
        userVoteFixture.setId(USER_VOTE_ID);
        userVoteFixture.setRestaurant(this.restaurantFixture);
        List<UserVote> userVoteListFixTure = new ArrayList<>();

        userVoteListFixTure.add(userVoteFixture);

        Mockito.when(mockUserVoteRepository.findByUser(userFixture))
                .thenReturn(userVoteListFixTure);

        // WHEN
        List<UserVote> result = target.findByUser(userFixture);

        // THEN
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result, equalTo(userVoteListFixTure));
    }


    @Test
    public void test_findAllSumarized_success() {

        // GIVEN

        List<UserVote> userVotesFixture = Arrays.asList(
                new UserVote(1L, new User(1L, "sdasd", "dadsda"), new Restaurant(1L, "aaa", "usdaudhas"), 2),
                new UserVote(2L, new User(1L, "sdasd", "dadsda"), new Restaurant(2L, "bbb", "usdaudhas"), 1),
                new UserVote(3L, new User(1L, "sdasd", "dadsda"), new Restaurant(3L, "ccc", "usdaudhas"), 1),
                new UserVote(4L, new User(2L, "bbbb", "dadsda"), new Restaurant(1L, "aaaa", "usdaudhas"), 1),
                new UserVote(5L, new User(2L, "bbbb", "dadsda"), new Restaurant(2L, "bbbb", "usdaudhas"), 1),
                new UserVote(6L, new User(2L, "bbbb", "dadsda"), new Restaurant(3L, "cccc", "usdaudhas"), 2)
        );

        Mockito.when(mockUserVoteRepository.findByTotal())
                .thenReturn(userVotesFixture);




    }
}