package in.techcamp.issueAppJava;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<IssueEntity, Integer> {
}
//@Mapper
//public interface IssueRepository {
//    @Insert("insert into issues (title, content, period, importance) values (#{title}, #{content}, #{period}, #{importance})")
//    void insert(String title,String content,String period,Character importance);
//
//    @Select("select * from issues")
//    List<IssueEntity> findAll();
//}
