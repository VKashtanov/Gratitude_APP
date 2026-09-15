package ru.kashtanov.comment_service.repo;


import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import ru.kashtanov.comment_service.enums.TargetType;
import ru.kashtanov.comment_service.model.Comment;


import java.util.List;


/**
 * @author Viktor Кashtanov
 */
@DataJpaTest // makes rollback, in the end of tested methods
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepoTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private CommentRepo commentRepo;
    // Variables
    private Comment comment;
    private final Long userId = 122L;
    private final Long targetId = 221L;
    private final Long cursor = 0L;
    private final Long limit = 3L;

    @BeforeEach
    void setUp() {
        comment = Comment.builder()
                .userId(userId)
                .targetId(targetId)
                .targetType(TargetType.COMMENT).build();
    }


    @Test
    void findByUserId_WhenOk() {
        testEntityManager.persist(comment);
        testEntityManager.flush();
        testEntityManager.clear();
        //WHEN
        List<Comment> result = commentRepo.findByUserId(userId, cursor, limit);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUserId()).isEqualTo(userId);
        assertThat(result.get(0).getTargetId()).isEqualTo(targetId);
        assertThat(result.get(0).getTargetType()).isEqualTo(TargetType.COMMENT);
    }

    @Test
    void findByUserId_WhenCursorIsNull() {
        testEntityManager.persist(comment);
        testEntityManager.flush();
        testEntityManager.clear();
        //WHEN
        List<Comment> result = commentRepo.findByUserId(userId, null, limit);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUserId()).isEqualTo(userId);
        assertThat(result.get(0).getTargetId()).isEqualTo(targetId);
        assertThat(result.get(0).getTargetType()).isEqualTo(TargetType.COMMENT);
    }

    @Test
    void findByUserId_WhenCursorIsGreaterThanId() {
        testEntityManager.persist(comment);
        testEntityManager.flush();
        testEntityManager.clear();
        //WHEN
        List<Comment> result = commentRepo.findByUserId(userId, 9999L, limit);
        //THEN
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void findByUserId_CheckLimits() {
        for (int i = 0; i < 5; i++) {
            testEntityManager.persist(Comment.builder()
                    .userId(userId)
                    .targetId(targetId)
                    .targetType(TargetType.COMMENT)
                    .build());
        }
        testEntityManager.flush();
        testEntityManager.clear();
        // WHEN
        List<Comment> result = commentRepo.findByUserId(userId, cursor, limit);
        // THEN
        int lim = Math.toIntExact(limit);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo((lim));
    }


    @Test
    void findByTargetId_WhenOk() {
        testEntityManager.persist(comment);
        testEntityManager.flush();
        testEntityManager.clear();
        //WHEN
        List<Comment> result = commentRepo.findByTargetId(targetId, cursor, limit);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUserId()).isEqualTo(userId);
        assertThat(result.get(0).getTargetId()).isEqualTo(targetId);
        assertThat(result.get(0).getTargetType()).isEqualTo(TargetType.COMMENT);
    }

    @Test
    void findByTargetId_WhenCursorIsNull() {
        testEntityManager.persist(comment);
        testEntityManager.flush();
        testEntityManager.clear();
        //WHEN
        List<Comment> result = commentRepo.findByTargetId(targetId, null, limit);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUserId()).isEqualTo(userId);
        assertThat(result.get(0).getTargetId()).isEqualTo(targetId);
        assertThat(result.get(0).getTargetType()).isEqualTo(TargetType.COMMENT);
    }

    @Test
    void findByTargetId_WhenCursorIsGreaterThanId() {
        testEntityManager.persist(comment);
        testEntityManager.flush();
        testEntityManager.clear();
        //WHEN
        List<Comment> result = commentRepo.findByTargetId(targetId, 9999L, limit);
        //THEN
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void findByTargetId_CheckLimits() {
        for (int i = 0; i < 5; i++) {
            testEntityManager.persist(Comment.builder()
                    .userId(userId)
                    .targetId(targetId)
                    .targetType(TargetType.COMMENT)
                    .build());
        }
        testEntityManager.flush();
        testEntityManager.clear();
        // WHEN
        List<Comment> result = commentRepo.findByTargetId(targetId, cursor, limit);
        // THEN
        int lim = Math.toIntExact(limit);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo((lim));
    }

    @Test
    void findAllComments_WhenOk() {
        // GIVEN
        for (int i = 0; i < 5; i++) {
            comment = Comment.builder()
                    .userId(userId)
                    .targetId(targetId)
                    .targetType(TargetType.COMMENT).build();
            testEntityManager.persist(comment);
        }
        testEntityManager.flush();
        testEntityManager.clear();
        // WHEN
        List<Comment> result = commentRepo.findAllComments(cursor, limit);
        // THEN
        assertThat(result).isNotNull();
        assertThat(result.size()).isGreaterThanOrEqualTo(3);
    }

    @Test
    void findAllComments_WhenCursorIsNull() {
        // GIVEN
        for (int i = 0; i < 5; i++) {
            comment = Comment.builder()
                    .userId(userId)
                    .targetId(targetId)
                    .targetType(TargetType.COMMENT).build();
            testEntityManager.persist(comment);
        }
        testEntityManager.flush();
        testEntityManager.clear();
        // WHEN
        List<Comment> result = commentRepo.findAllComments(null, limit);
        // THEN
        assertThat(result).isNotNull();
        assertThat(result.size()).isGreaterThanOrEqualTo(3);
    }

}